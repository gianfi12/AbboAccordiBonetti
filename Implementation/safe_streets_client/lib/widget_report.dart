import 'dart:developer';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:safe_streets_client/handler_backend.dart';

import 'handler_model.dart' as data;
import 'handler_device.dart' as device;
import 'handler_localization.dart' as l;
import 'handler_presets.dart' as theme_presets;

///Starts the new report process: asks permissions and opens the camera.
///The flow is as follows:
///NewReport with CameraMode => PicturePreview => ChooseCategory
///(and position if necessary) => AdditionalInputs
///from there, the user is redirected back to the main route.
class NewReport extends StatefulWidget {
  NewReport({Key key}) : super(key: key);

  @override
  _NewReportState createState() => _NewReportState();
}

///The state for the new report widget.
///Shows the camera or a button to request camera permissions.
class _NewReportState extends State<NewReport> {
  ///Whether the app has access to the device camera.
  bool _hasPermissions = false;

  ///The device camera.
  CameraDescription camera;

  ///On initialization the app checks the permissions.
  @override
  void initState() {
    super.initState();
    _refreshPermissions();
  }

  @override
  Widget build(BuildContext context) {
    if (_hasPermissions && camera != null) {
      return CameraMode(camera: camera);
    }
    return Center(
      child: FlatButton(
        child: Text(l.local(l.AvailableStrings.REQUEST_PERMISSIONS)),
        onPressed: _refreshPermissions,
      ),
    );
  }

  ///Asks the permissions for the camera. If granted, sets the camera to the first available.
  void _refreshPermissions() {
    device
        .hasCameraPermissions()
        .then((has) => setState(() => _hasPermissions = has));
    availableCameras().then((available) => camera = available.first);
  }
}

///A widget to take the first picture, go back or select fro device.
class CameraMode extends StatefulWidget {
  ///The provided camera.
  final CameraDescription camera;

  const CameraMode({Key key, @required this.camera}) : super(key: key);

  @override
  State<StatefulWidget> createState() => CameraModeState();
}

///The state for the camera mode.
class CameraModeState extends State<CameraMode> {
  ///The controller for the camera of this widget.
  CameraController _controller;

  ///The status of the camera initialization.
  Future<void> _initializeControllerFuture;

  ///Initializes the controller.
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
          //If the camera is ready, show the feed.
          if (snapshot.connectionState == ConnectionState.done) {
            return Center(
              child: SizedBox(
                  width: 720,
                  height: 550, //FIXME size box to not distort
                  child: CameraPreview(_controller)),
            );
          }

          //The camera is not ready: show progress indicator.
          return Center(child: CircularProgressIndicator());
        },
      ),
      extendBody: false,
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            IconButton(
              icon: Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
            //TODO: pop until named
            IconButton(
              icon: Icon(Icons.photo),
              onPressed: () => _selectFromDevice(context),
            ),
          ],
        ),
        shape: CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: theme_presets.accent,
        child: Icon(Icons.camera_alt),
        onPressed: () => _takePicture(context),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  ///Select the picture from the device and go to preview.
  void _selectFromDevice(BuildContext context) {
    ImagePicker.pickImage(source: ImageSource.gallery).then((image) {
      if (image != null) {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => DisplayPictureScreen(
              imagePath: image.path,
              onConfirm: () => _whenConfirmed(context),
            ),
          ),
        );
      }
    });
  }

  ///Take the picture from the camera and go to preview
  void _takePicture(BuildContext context) async {
    try {
      await _initializeControllerFuture;
      final path = join(
        (await getTemporaryDirectory()).path,
        '${DateTime.now()}.png',
      );
      await _controller.takePicture(path);
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => DisplayPictureScreen(
            imagePath: path,
            onConfirm: () => _whenConfirmed(context),
          ),
        ),
      );
    } catch (e) {
      log(e);
    }
  }

  ///Provides the route to follow when the picture is confirmed.
  void _whenConfirmed(BuildContext context) => Navigator.push(
      context, MaterialPageRoute(builder: (context) => ChooseCategory()));
}

///Shows a preview of the image, the user can go back or confirm it.
///If he confirms, the user is directed to ChooseCategory.
class DisplayPictureScreen extends StatelessWidget {
  final String imagePath;
  final VoidCallback onConfirm;

  const DisplayPictureScreen({
    Key key,
    @required this.imagePath,
    @required this.onConfirm,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.close),
          onPressed: () => null, //TODO abort
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
              icon: Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
          ],
        ),
        shape: CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: theme_presets.accent,
        child: Icon(Icons.check),
        onPressed: onConfirm,
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
    );
  }
}

///The widget that allows to choose the category.
///Once a category is selected, the user can choose to add information or send the report.
///The user can go back to the previous screen or abort the process.
class ChooseCategory extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _ChooseCategoryState();
}

///The state of the choose category widget.
class _ChooseCategoryState extends State<ChooseCategory> {
  ///The value currently selected.
  String _selected;

  ///The list of choices.
  List<String> items = [];

  ///Populates the list of available choices.
  @override
  void initState() {
    super.initState();
    getAvailableReportCategories().then((i) => setState(() => items = i));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.close),
          onPressed: () => null, //TODO abort
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
              icon: Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
            FlatButton(
              child: Row(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  Icon(Icons.playlist_add),
                  Text(l.local(l.AvailableStrings.ADD_INFO)),
                ],
              ),
              onPressed: _selected == null
                  ? null
                  : () => Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) =>
                              AdditionalInputs())), //TODO Add info
            ),
          ],
        ),
        shape: CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: theme_presets.accent,
        child: Icon(Icons.check),
        onPressed: _selected == null
            ? null
            : () => Navigator.pop(context), //TODO check position and send
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
      activeColor: theme_presets.accent,
      onChanged: (value) {
        setState(() {
          _selected = value;
        });
      },
    );
  }
}

class AdditionalInputs extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => AdditionalInputsState();
}

class AdditionalInputsState extends State<AdditionalInputs> {
  //TODO add links
  ///The key to validate the form.
  final _formKey = GlobalKey<FormState>();
  TextEditingController dateController;
  TextEditingController timeController;

  @override
  void initState() {
    super.initState();
    dateController = TextEditingController(
      text: DateFormat(theme_presets.dateFormat).format(violationDateTime),
    );
    timeController = TextEditingController(
      text: DateFormat(theme_presets.timeFormat).format(violationDateTime),
    );
  }

  DateTime violationDateTime = DateTime.now();
  List<String> otherImages;
  data.DevicePosition overridePosition;
  String plateNumber;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.close),
          onPressed: () => null, //TODO confirm and abort
        ),
        title: Text(l.local(l.AvailableStrings.ADD_INFO)),
      ),
      body: Form(
        key: _formKey,
        child: ListView(
          //mainAxisSize: MainAxisSize.min, FIXME
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_PLATE),
                ),
                textInputAction: TextInputAction.next,
                onSaved: (value) => plateNumber = value,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_POSITION),
                  //TODO: validate? keyboard type? connections with focus
                ),
                textInputAction: TextInputAction.next,
                onSaved: (value) => plateNumber = value,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_DATE),
                  //TODO add validation
                  suffixIcon: IconButton(
                    icon: Icon(Icons.calendar_today),
                    onPressed: _changeDate,
                  ),
                ),
                initialValue: DateFormat(theme_presets.dateFormat)
                    .format(violationDateTime),
                //FIXME it does not update with calendar
                textInputAction: TextInputAction.next,
                onSaved: (value) => plateNumber = value,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextFormField(
                decoration: InputDecoration(
                  filled: true,
                  border: OutlineInputBorder(),
                  labelText: l.local(l.AvailableStrings.INFO_TIME),
                  //TODO add validation
                  suffixIcon: IconButton(
                    icon: Icon(Icons.access_time),
                    onPressed: _changeDate, //TODO change time
                  ),
                ),
                initialValue: DateFormat(theme_presets.timeFormat)
                    .format(violationDateTime),
                //FIXME it does not update with calendar
                textInputAction: TextInputAction.done,
                onSaved: (value) => plateNumber = value,
              ),
            ),
            ButtonBar(
              alignment: MainAxisAlignment.spaceBetween,
              children: [
                RaisedButton(
                  color: theme_presets.accent,
                  textColor: theme_presets
                      .getDefaultTheme()
                      .accentTextTheme
                      .body1
                      .color, //FIXME theme
                  child: Text(l
                      .local(l.AvailableStrings.INFO_IMAGES_CAM)
                      .toUpperCase()),
                  onPressed: () => null, //Todo add images;
                ),
                RaisedButton(
                  color: theme_presets.accent,
                  textColor: theme_presets
                      .getDefaultTheme()
                      .accentTextTheme
                      .body1
                      .color, //FIXME theme
                  child: Text(l
                      .local(l.AvailableStrings.INFO_IMAGES_DEV)
                      .toUpperCase()),
                  onPressed: () => null, //Todo add images;
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
              icon: Icon(Icons.arrow_back),
              onPressed: () => Navigator.pop(context),
            ),
          ],
        ),
        shape: CircularNotchedRectangle(),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: theme_presets.accent,
        child: Icon(Icons.check),
        onPressed: () => Navigator.pop(context), //TODO send
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
    );
  }

  //TODO: change context to this.context where possible
  ///Pick a date between 2019 and now.
  void _changeDate() {
    showDatePicker(
      context: this.context,
      initialDate: violationDateTime,
      firstDate: DateTime(2019),
      lastDate: DateTime.now(),
    ).then((dateTime) {
      if (dateTime != null) setState(() => violationDateTime = dateTime);
    });
  }
}

/*
File imageFile = new File(imageFilePath);
List<int> imageBytes = imageFile.readAsBytesSync();
String base64Image = BASE64.encode(imageBytes);
 */
