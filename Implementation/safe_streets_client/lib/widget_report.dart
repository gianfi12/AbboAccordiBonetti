import 'dart:developer';
import 'dart:io';
import 'dart:ui';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';

import 'handler_backend.dart' as backend;
import 'handler_device.dart' as device;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;
import 'handler_presets.dart' as presets;

/// The first step of the report process: this asks permissions and opens the camera.
///
/// The flow for a new report is as follows:
/// * NewReport containing CameraMode
/// * PicturePreview
/// * ChooseCategory (and position if necessary)
/// * AdditionalInput
/// * Main route
class NewReport extends StatefulWidget {
  NewReport({Key key}) : super(key: key);

  @override
  _NewReportState createState() => _NewReportState();
}

/// The state for the new report widget.
/// Shows the camera or a button to request camera permissions.
class _NewReportState extends State<NewReport> {
  /// Whether the app has access to the device camera.
  bool _hasPermissions = false;

  /// The device camera.
  CameraDescription camera;

  /// On initialization the app checks the permissions.
  @override
  void initState() {
    super.initState();
    _onRefreshPermissions();
  }

  @override
  Widget build(BuildContext context) {
    if (_hasPermissions && camera != null) {
      return _CameraMode(camera: camera);
    }

    //No permissions: show button to get permissions.
    return Center(
      child: FlatButton(
        child: Text(l.local(l.AvailableStrings.REQUEST_PERMISSIONS)),
        onPressed: _onRefreshPermissions,
      ),
    );
  }

  /// Asks the permissions for the camera.
  ///
  /// If granted, sets the camera to the first available.
  void _onRefreshPermissions() {
    device
        .hasCameraPermissions()
        .then((has) => setState(() => _hasPermissions = has));
    availableCameras().then((available) => camera = available?.first);
  }
}

/// A widget to take the first picture, go back or select from device.
class _CameraMode extends StatefulWidget {
  /// The provided camera.
  final CameraDescription camera;

  const _CameraMode({Key key, @required this.camera}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _CameraModeState();
}

/// The state for the camera mode.
class _CameraModeState extends State<_CameraMode> {
  /// The controller for the camera of this widget.
  CameraController _controller;

  /// The status of the camera initialization.
  Future<void> _initializeControllerFuture;

  /// Initializes the controller.
  @override
  void initState() {
    super.initState();
    _controller = CameraController(widget.camera, ResolutionPreset.medium);
    _initializeControllerFuture = _controller.initialize();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<void>(
        future: _initializeControllerFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return Center(
              child: SizedBox(
                width: 720,
                height: 550,
                child: CameraPreview(_controller),
              ),
            );
          }

          //The camera is not ready: show progress indicator.
          return const Center(child: const CircularProgressIndicator());
        },
      ),
      extendBody: false,
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
            IconButton(
              icon: const Icon(Icons.photo),
              onPressed: () => _onSelectFromDevice(),
            ),
          ],
        ),
        shape: const CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: presets.accent,
        child: const Icon(Icons.camera_alt),
        onPressed: () => _onTakePicture(context),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  /// Allows to select the picture from the device and go to preview.
  void _onSelectFromDevice() async {
    device.chooseImage(this.context, onlyDevice: true).then((image) {
      if (image != null) {
        _onPictureTaken(image.path);
      }
    });
  }

  /// Allows to take the picture from the camera and go to preview.
  void _onTakePicture(BuildContext context) async {
    try {
      await _initializeControllerFuture;
      final path = join(
        (await getTemporaryDirectory()).path,
        '${DateTime.now()}.png',
      );
      await _controller.takePicture(path);
      _onPictureTaken(path);
    } catch (e) {
      log(e.toString());
    }
  }

  /// Provides the route to follow when the picture is taken or chosen from device.
  void _onPictureTaken(String imagePath) {
    Navigator.push(
      this.context,
      MaterialPageRoute(
        builder: (context) => _DisplayPictureScreen(
          imagePath: imagePath,
          onConfirm: () => Navigator.push(
            this.context,
            MaterialPageRoute(
              builder: (context) => _ChooseCategory(
                imagePath: imagePath,
              ),
            ),
          ),
        ),
      ),
    );
  }
}

/// Shows a preview of the image, the user can go back or confirm it.
/// If he confirms, the user is directed to ChooseCategory.
class _DisplayPictureScreen extends StatelessWidget {
  /// The path of the image to preview.
  final String imagePath;

  /// The action to perform if the user confirms.
  final VoidCallback onConfirm;

  const _DisplayPictureScreen({
    Key key,
    @required this.imagePath,
    @required this.onConfirm,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: const IconButton(
          icon: const Icon(Icons.close),
          onPressed: _abortReport,
        ),
        title: Text(l.local(l.AvailableStrings.REPORT_PICTURE_PREVIEW)),
      ),
      body: Image.file(
        File(imagePath),
        fit: BoxFit.fitWidth,
        height: double.infinity,
      ),
      extendBody: true,
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
          ],
        ),
        shape: const CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.check),
        onPressed: onConfirm,
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
    );
  }
}

/// The widget that allows to choose the category.
///
/// Once a category is selected, the user can choose to add information or send
/// the report. The user can go back to the previous screen or abort the process.
class _ChooseCategory extends StatefulWidget {
  /// The path of the image to preview.
  final String imagePath;

  _ChooseCategory({Key key, @required this.imagePath}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ChooseCategoryState();
}

/// The state of the choose category widget.
class _ChooseCategoryState extends State<_ChooseCategory> {
  /// The value currently selected.
  String _selected;

  /// The list of choices.
  List<String> items = [];

  /// Populates the list of available choices.
  @override
  void initState() {
    super.initState();
    backend
        .getAvailableReportCategories()
        .then((list) => setState(() => items = list));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: const IconButton(
          icon: const Icon(Icons.close),
          onPressed: _abortReport,
        ),
        title: Text(l.local(l.AvailableStrings.CATEGORY_CHOOSE)),
      ),
      body: ListView(
        children: items.map(_buildTile).toList(),
      ),
      extendBody: true,
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
            FlatButton(
              child: Row(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  const Icon(Icons.playlist_add),
                  Text(l.local(l.AvailableStrings.ADD_INFO)),
                ],
              ),
              onPressed: _selected == null ? null : _onAddInfo,
            ),
          ],
        ),
        shape: CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.check),
        onPressed: _selected == null ? null : _onSave,
        mini: _selected == null,
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
    );
  }

  Widget _buildTile(String key) {
    return RadioListTile<String>(
      title: Text(l.localKey(key)),
      value: key,
      groupValue: _selected,
      activeColor: presets.accent,
      onChanged: (value) async => setState(() => _selected = value),
    );
  }

  /// Requests the position and shows the page to add information.
  void _onAddInfo() async {
    final pos = await device.getDevicePosition(this.context);
    Navigator.push(
      this.context,
      MaterialPageRoute(
        builder: (context) => AdditionalInputs(
          imagePath: widget.imagePath,
          category: _selected,
          currentPosition: pos,
        ),
      ),
    );
  }

  /// Requests the position and sends the report.
  void _onSave() async {
    final pos = await device.getDevicePosition(this.context);
    _sendReport(model.Report(
      devicePosition: pos,
      mainImage: widget.imagePath,
      violationType: _selected,
      deviceDateTime: DateTime.now(),
    ));
  }
}

/// This allows to insert additional information.
class AdditionalInputs extends StatefulWidget {
  /// The path of the image to preview.
  final String imagePath;

  /// The chosen category.
  final String category;

  /// The position of the device.
  final model.DevicePosition currentPosition;

  AdditionalInputs({
    Key key,
    @required this.imagePath,
    @required this.category,
    this.currentPosition,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _AdditionalInputsState();
}

//TODO(hig): parse position.
//TODO(low): add links between fields.
//TODO(low): validate non critical fields.
//TODO(low): set keyboard types.
/// The state for the additional inputs widget.
class _AdditionalInputsState extends State<AdditionalInputs> {
  /// The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  /// A controller for the violation date text field.
  TextEditingController dateController;

  /// A controller for the violation time text field.
  TextEditingController timeController;

  /// A date format for the date.
  DateFormat dateFormat;

  /// A date format for the time.
  DateFormat timeFormat;

  /// The time of the violation, this defaults to the present time.
  DateTime violationDateTime;

  /// The time of the report.
  DateTime reportDateTime;

  /// A list of other image paths.
  List<String> otherImages = [];

  /// The position of the violation, if null the current one is the valid one.
  String overridePosition;

  /// The plate number in the main picture.
  String plateNumber;

  @override
  void initState() {
    super.initState();
    reportDateTime = DateTime.now();
    violationDateTime = DateTime.fromMillisecondsSinceEpoch(
        reportDateTime.millisecondsSinceEpoch);
    dateFormat = DateFormat(presets.dateFormat);
    timeFormat = DateFormat(presets.timeFormat);
    dateController = TextEditingController(
      text: dateFormat.format(violationDateTime),
    );
    timeController = TextEditingController(
      text: timeFormat.format(violationDateTime),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: const IconButton(
          icon: const Icon(Icons.close),
          onPressed: _abortReport,
        ),
        title: Text(l.local(l.AvailableStrings.ADD_INFO)),
      ),
      body: Form(
        key: _formKey,
        child: ListView(
          children: <Widget>[
            //Plate number
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: const OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_PLATE),
                ),
                textInputAction: TextInputAction.next,
                onSaved: (value) => plateNumber = value,
              ),
            ),

            //Position
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: const OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_POSITION),
                ),
                textInputAction: TextInputAction.next,
                onSaved: (value) => overridePosition = value,
              ),
            ),

            //Violation date
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: const OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_DATE),
                  suffixIcon: IconButton(
                    icon: const Icon(Icons.calendar_today),
                    onPressed: _onCalendarDate,
                  ),
                ),
                validator: (text) {
                  try {
                    //TODO(low): check if the date (and time) inserted are after now.
                    dateFormat.parseLoose(text);
                    return null;
                  } on FormatException {
                    return l.local(l.AvailableStrings.INFO_WRONG_DATE);
                  }
                },
                controller: dateController,
                autovalidate: true,
                textInputAction: TextInputAction.next,
                onSaved: (text) => _onSaveDate(dateFormat.parseLoose(text)),
              ),
            ),

            //Violation time
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: const OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_TIME),
                  suffixIcon: IconButton(
                    icon: const Icon(Icons.access_time),
                    onPressed: _onCalendarTime,
                  ),
                ),
                validator: (text) {
                  try {
                    timeFormat.parseLoose(text);
                    return null;
                  } on FormatException {
                    return l.local(l.AvailableStrings.INFO_WRONG_TIME);
                  }
                },
                controller: timeController,
                autovalidate: true,
                textInputAction: TextInputAction.done,
                onSaved: (text) => _onSaveTime(
                    TimeOfDay.fromDateTime(timeFormat.parseLoose(text))),
              ),
            ),

            //Add pictures
            ButtonBar(
              alignment: MainAxisAlignment.end,
              children: <Widget>[
                RaisedButton(
                  child: Text(
                    l.local(l.AvailableStrings.INFO_IMAGES).toUpperCase(),
                  ),
                  onPressed: () async =>
                      otherImages.add((await device.chooseImage(context)).path),
                ),
              ],
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
          ],
        ),
        shape: const CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.check),
        onPressed: _onSave,
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
    );
  }

  /// Allows to pick a date between 2019 and now.
  void _onCalendarDate() async {
    showDatePicker(
      context: this.context,
      initialDate: violationDateTime,
      firstDate: DateTime(2019),
      lastDate: DateTime.now(),
    ).then((dateTime) {
      if (dateTime != null) _onSaveDate(dateTime);
    });
  }

  /// Saves the date to [violationDateTime].
  void _onSaveDate(DateTime newDate) async {
    setState(() => violationDateTime = DateTime(
          newDate.year,
          newDate.month,
          newDate.day,
          violationDateTime.hour,
          violationDateTime.minute,
        ));
    dateController.text = dateFormat.format(violationDateTime);
  }

  /// Allows to pick a time.
  void _onCalendarTime() async {
    showTimePicker(
      context: this.context,
      initialTime: TimeOfDay.fromDateTime(violationDateTime),
    ).then((time) {
      if (time != null) _onSaveTime(time);
    });
  }

  /// Saves the time to [violationDateTime].
  void _onSaveTime(TimeOfDay newTime) async {
    setState(() => violationDateTime = DateTime(
          violationDateTime.year,
          violationDateTime.month,
          violationDateTime.day,
          newTime.hour,
          newTime.minute,
        ));
    timeController.text = timeFormat.format(violationDateTime);
  }

  /// Sends the report if the fields are filled correctly.
  void _onSave() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      _sendReport(model.Report(
        violationType: widget.category,
        mainImage: widget.imagePath,
        devicePosition: widget.currentPosition,
        deviceDateTime: reportDateTime,
        plateNumber: plateNumber,
        violationDateTime: violationDateTime,
        otherImages: otherImages,
      ));
    }
  }
}

void _abortReport() {
  print('abort');
  //TODO(hig): ask confirmation and pop until main.
}

void _sendReport(model.Report report) {
  print('send $report');
  //TODO(hig): send report.
}
