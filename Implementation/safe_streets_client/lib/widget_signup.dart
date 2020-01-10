import 'package:flutter/material.dart';

import 'handler_backend.dart' as backend;
import 'handler_device.dart' as device;
import 'handler_localization.dart' as l;
import 'widget_bottom.dart' as bottom;

/// A regular expression to identify valid email addresses.
String emailRegex =
    r'^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$';

/// Allows the user to insert the data to sign up.
class SignUp extends StatefulWidget {
  SignUp({Key key}) : super(key: key);

  @override
  _SignUpState createState() => _SignUpState();
}

/// The state for the sign up widget.
class _SignUpState extends State<SignUp> with AutomaticKeepAliveClientMixin{
  /// The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  /// The key to later show a snack bar.
  final _scaffoldKey = GlobalKey<ScaffoldState>();

  /// A list of the text fields to build.
  List<_TextFormPlaceholder> textFields = [];

  //Start of subscription contents.
  String _username;
  String _email;
  String _firstName;
  String _lastName;
  String _placeOfBirth;
  String _dateOfBirth;
  String _placeOfResidence;
  String _fiscalCode;
  String _picture;
  String _idCard;
  String _password;

  //TODO(hig): validate inputs based on json, especially dates and images.
  //TODO(low): link elements.
  //TODO(low): change keyboards.
  @override
  void initState() {
    super.initState();
    textFields = <_TextFormPlaceholder>[
      _TextFormPlaceholder(
        label: l.AvailableStrings.LOGIN_USERNAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _username = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_EMAIL,
        onSaved: (s) => _email = s,
        validator: (value) {
          if (value.isEmpty) return l.local(l.AvailableStrings.SIGN_REQUIRED);
          if (!RegExp(emailRegex).hasMatch(value))
            return l.local(l.AvailableStrings.SIGN_EMAIL_WRONG);
          return null;
        },
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_FIRST_NAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _firstName = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_LAST_NAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _lastName = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_PLACE_BIRTH,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _placeOfBirth = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_DATE_BIRTH,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _dateOfBirth = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_PLACE_RESIDENCE,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _placeOfResidence = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_FISCAL,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _fiscalCode = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.LOGIN_PASSWORD,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _password = s,
      ),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: Text(l.local(l.AvailableStrings.LOGIN_SIGN_UP)),
      ),
      body: Center(
        child: Form(
          key: _formKey,
          child: SizedBox(
            width: 400,
            child: ListView(
              children: _buildItems(),
              addAutomaticKeepAlives: true,
            ),
          ),
        ),
      ),
    );
  }

  /// Builds the text field and some buttons.
  List<Widget> _buildItems() {
    var list = textFields.map((e) => e.buildTextForm()).toList();
    list.addAll(<Widget>[
      Padding(
        padding: EdgeInsets.all(8.0),
      ),
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width: double.maxFinite,
          child: RaisedButton(
            child: Text(
              l.local(l.AvailableStrings.SIGN_DOCUMENT).toUpperCase(),
            ),
            onPressed: _onIDButton,
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width: double.maxFinite,
          child: RaisedButton(
            child: Text(
              l.local(l.AvailableStrings.SIGN_PICTURE).toUpperCase(),
            ),
            onPressed: _onPictureButton,
          ),
        ),
      ),
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width: double.maxFinite,
          child: RaisedButton(
            child: Text(
              l.local(l.AvailableStrings.LOGIN_SIGN_UP).toUpperCase(),
            ),
            onPressed: _onSendButton,
          ),
        ),
      ),
    ]);
    return list;
  }

  /// Called when the button for the document photo is pressed.
  ///
  /// Asks the user to choose a photo and saves it.
  void _onIDButton() async {
    device.chooseImage(context).then((file) {
      if (file != null) _idCard = file.path;
    });
  }

  /// Called when the button for the user photo is pressed.
  ///
  /// Asks the user to choose a photo and saves it.
  void _onPictureButton() async {
    device.chooseImage(context).then((file) {
      if (file != null) _picture = file.path;
    });
  }

  /// Called when the send button is pressed.
  ///
  /// If the form is complete and valid, sends the registration request.
  /// If the registration is accepted, shows the main screen and removes the
  /// navigation stack behind it.
  void _onSendButton() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      var server = backend.DispatcherInterface.getNew(_username, _password);
      server
          .userRegistration(
        username: _username,
        email: _email,
        firstName: _firstName,
        lastName: _lastName,
        placeOfBirth: _placeOfBirth,
        placeOfResidence: _placeOfResidence,
        picture: _picture,
        idCard: _idCard,
        fiscalCode: _fiscalCode,
        dateOfBirth: DateTime.parse(_dateOfBirth),
        password: _password,
      )
          .then((outcome) {
        if (outcome) {
          Navigator.pushNamedAndRemoveUntil(
            context,
            bottom.BottomNavigation.name,
            (r) => false,
            arguments: server,
          );
        } else {
          _scaffoldKey.currentState.showSnackBar(SnackBar(
            content: Text(l.local(l.AvailableStrings.SIGN_ERROR)),
          ));
        }
      });
    }
  }

  @override
  bool get wantKeepAlive => true;
}

/// An item that represents a [TextFormField].
class _TextFormPlaceholder {
  /// The label of the field.
  l.AvailableStrings label;

  /// The error message displayed by the default validator.
  l.AvailableStrings error;

  /// The action taken when saved.
  FormFieldSetter onSaved;

  /// The validator for the field.
  ///
  /// If not specified, a default is used, that prints [error] if the field is empty.
  FormFieldValidator<String> validator;

  _TextFormPlaceholder({
    @required this.label,
    this.error,
    @required this.onSaved,
    this.validator,
  }) {
    validator ??= (value) => value.isEmpty ? l.local(error) : null;
  }

  /// Returns a [TextFormField] with the properties of this class.
  Widget buildTextForm() {
    return Padding(
      key: ValueKey(label),
      padding: const EdgeInsets.all(8.0),
      child: TextFormField(
        validator: validator,
        decoration: InputDecoration(
          filled: true,
          border: OutlineInputBorder(),
          labelText: l.local(label),
        ),
        onSaved: onSaved,
      ),
    );
  }
}

/// Allows the municipality to insert the data to sign up.
class MunSignUp extends StatefulWidget {
  MunSignUp({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => MunSignUpState();
}

/// The state of the municipality sign up widget.
class MunSignUpState extends State<MunSignUp> {
  /// The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  /// The key to later show a snack bar.
  final _scaffoldKey = GlobalKey<ScaffoldState>();

  /// A list of the text fields to build.
  List<_TextFormPlaceholder> textFields = [];

  //Start of subscription contents.
  String _code;
  String _username;
  String _password;
  String _dataIntegrationIp;
  String _dataIntegrationPort;
  String _dataIntegrationPassword;

  //TODO(low): link elements.
  //TODO(low): change keyboards.
  @override
  void initState() {
    super.initState();
    textFields = <_TextFormPlaceholder>[
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_CODE,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _code = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.LOGIN_USERNAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _username = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.LOGIN_PASSWORD,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _password = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_DI_IP,
        onSaved: (s) => _dataIntegrationIp = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_DI_PORT,
        onSaved: (s) => _dataIntegrationPort = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_DI_PASSWORD,
        onSaved: (s) => _dataIntegrationPassword = s,
      ),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: Text(l.local(l.AvailableStrings.LOGIN_SIGN_UP)),
      ),
      body: Center(
        child: Form(
          key: _formKey,
          child: SizedBox(
            width: 400,
            child: ListView(
              children: _buildItems(),
            ),
          ),
        ),
      ),
    );
  }

  /// Builds the text field and some buttons.
  List<Widget> _buildItems() {
    var list = textFields.map((e) => e.buildTextForm()).toList();
    list.addAll(<Widget>[
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width: double.maxFinite,
          child: RaisedButton(
            child: Text(
              l.local(l.AvailableStrings.LOGIN_SIGN_UP).toUpperCase(),
            ),
            onPressed: _onSendButton,
          ),
        ),
      ),
    ]);
    return list;
  }

  /// Called when the send button is pressed.
  ///
  /// If the form is complete and valid, sends the registration request.
  /// If the registration is accepted, shows the main screen and removes the
  /// navigation stack behind it.
  void _onSendButton() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      var server = backend.DispatcherInterface.getNew(_username, _password);
      server
          .municipalityRegistration(
        code: _code,
        username: _username,
        password: _password,
        dataIntegrationIp: _dataIntegrationIp,
        dataIntegrationPort: _dataIntegrationPort,
        dataIntegrationPassword: _dataIntegrationPassword,
      )
          .then((outcome) {
        if (outcome) {
          Navigator.pushNamedAndRemoveUntil(
            context,
            bottom.BottomNavigation.name,
            (r) => false,
            arguments: server,
          );
        } else {
          _scaffoldKey.currentState.showSnackBar(SnackBar(
            content: Text(l.local(l.AvailableStrings.SIGN_ERROR)),
          ));
        }
      });
    }
  }
}
