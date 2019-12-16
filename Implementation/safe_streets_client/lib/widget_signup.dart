import 'package:flutter/material.dart';

import 'handler_localization.dart' as l;
import 'handler_presets.dart' as theme_presets;

///Allows to insert the data to do a login.
class SignUp extends StatefulWidget {
  SignUp({Key key}) : super(key: key);

  @override
  _SignUpState createState() => _SignUpState();
}

///The state for the sign up widget.
class _SignUpState extends State<SignUp> {
  ///The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  String _username;
  String _email;
  String _firstName;
  String _lastName;
  String _placeOfBirth;
  String _dateOfBirth;
  String _placeOfResidence;
  String _fiscalCode;
  String _idCard;

  List<_TextFormPlaceholder> textFields = [];

  @override
  void initState() {
    super.initState();
    textFields = [
      _TextFormPlaceholder(
        label: l.AvailableStrings.LOGIN_USERNAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _username = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_EMAIL,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _email = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_FIRST_NAME,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _firstName = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_PLACE_BIRTH,
        error: l.AvailableStrings.SIGN_REQUIRED,
        onSaved: (s) => _placeOfBirth = s,
      ),
      _TextFormPlaceholder(
        label: l.AvailableStrings.SIGN_DATE_BIRTH, //TODO
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
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(l.local(l.AvailableStrings.LOGIN_SIGN_UP)),
      ),
      body: ListView(
        children: [
          Form(
            key: _formKey,
            child: Column(
              children: _buildItems(),
            ),
          )
        ],
      ),
    );
  }

  List<Widget> _buildItems() {
    var list = textFields.map((e) => e.buildTextForm()).toList();
    list.addAll([
      Padding(
        padding: EdgeInsets.all(8.0),
      ),
      Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: <Widget>[
          Padding(
            padding: EdgeInsets.all(4.0),
          ),
          Text(l.local(l.AvailableStrings.SIGN_DOCUMENT)),
        ],
      ),
      ButtonBar(

        alignment: MainAxisAlignment.spaceBetween,
        layoutBehavior: ButtonBarLayoutBehavior.constrained,
        children: [
          RaisedButton(
            color: theme_presets.accent,
            textColor: theme_presets
                .getDefaultTheme()
                .accentTextTheme
                .body1
                .color, //FIXME theme
            child:
                Text(l.local(l.AvailableStrings.INFO_IMAGES_CAM).toUpperCase()),
            onPressed: () => null, //Todo add images;
          ),
          RaisedButton(
            color: theme_presets.accent,
            textColor: theme_presets
                .getDefaultTheme()
                .accentTextTheme
                .body1
                .color, //FIXME theme
            child:
                Text(l.local(l.AvailableStrings.INFO_IMAGES_DEV).toUpperCase(), ),
            onPressed: () => null, //Todo add images;
          ),
        ],
      ),
      Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width: double.maxFinite,
          child: RaisedButton(
            color: theme_presets.accent,
            textColor: theme_presets
                .getDefaultTheme()
                .accentTextTheme
                .body1
                .color, //FIXME theme
            child:
            Text(l.local(l.AvailableStrings.LOGIN_SIGN_UP).toUpperCase()),
            onPressed: () => null, //Todo send;
          ),
        ),
      ),
    ]);
    return list;
  }
}

class _TextFormPlaceholder {
  l.AvailableStrings label;
  l.AvailableStrings error;
  FormFieldSetter onSaved;
  FormFieldValidator validator;

  _TextFormPlaceholder({
    @required this.label,
    @required this.error,
    @required this.onSaved,
    this.validator,
  });

  Widget buildTextForm() {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: TextFormField(
        validator: validator ??
            (value) {
              if (value.isEmpty) return l.local(error);
              return null;
            },
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
