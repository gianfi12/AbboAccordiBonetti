import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

import 'localized_strings.dart' as l;
import 'logic.dart' as logic;
import 'theme_presets.dart' as theme_presets;
import 'widget_bottom.dart' as bottom;
import 'widget_signup.dart' as sign_up;

///Allows to insert the data for the login or to start the sign up process.
class Login extends StatefulWidget {
  Login({Key key}) : super(key: key);

  @override
  _LoginState createState() => _LoginState();
}

///The state for the login widget.
class _LoginState extends State<Login> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: <Widget>[
          Column(children: [
            Image(image: theme_presets.getTransparentLogo()),
            theme_presets.getFormattedAppName(),
            SizedBox(
              width: 400,
              child: _LoginForm(),
            ),
          ]),
        ],
      ),
    );
  }
}

///A form for the login data and buttons.
class _LoginForm extends StatefulWidget {
  @override
  _LoginFormState createState() {
    return _LoginFormState();
  }
}

///The state for the login form.
class _LoginFormState extends State<_LoginForm> {
  //TODO: add link between elements

  ///The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  ///Whether the password should be visible.
  bool _isPasswordVisible = false;

  ///The username inserted.
  String _username;

  ///The password inserted.
  String _password;

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextFormField(
              validator: (value) {
                if (value.isEmpty) {
                  return l.local(l.AvailableStrings.LOGIN_USERNAME_REQ);
                }
                return null;
              },
              decoration: InputDecoration(
                filled: true,
                border: OutlineInputBorder(),
                labelText: l.local(l.AvailableStrings.LOGIN_USERNAME),
              ),
              onSaved: (value) => _username = value,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextFormField(
              validator: (value) {
                if (value.isEmpty) {
                  return l.local(l.AvailableStrings.LOGIN_PASSWORD_REQ);
                }
                return null;
              },
              obscureText: !_isPasswordVisible,
              decoration: InputDecoration(
                filled: true,
                border: OutlineInputBorder(),
                labelText: l.local(l.AvailableStrings.LOGIN_PASSWORD),
                suffixIcon: IconButton(
                    onPressed: () => setState(() {
                          _isPasswordVisible = !_isPasswordVisible;
                        }),
                    icon: Icon(_isPasswordVisible
                        ? Icons.visibility_off
                        : Icons.visibility)),
              ),
              onSaved: (value) => _password = value,
            ),
          ),
          ButtonBar(
            alignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              RichText(
                  text: TextSpan(
                      text: l.local(l.AvailableStrings.LOGIN_NOT_REGISTERED_),
                      style: Theme.of(context).textTheme.body1,
                      children: [
                    TextSpan(
                        text: l.local(l.AvailableStrings.LOGIN_SIGN_UP),
                        style: TextStyle(color: Theme.of(context).accentColor),
                        recognizer: TapGestureRecognizer()
                          ..onTap = _requestSignUp)
                  ])),
              FlatButton(
                color: Theme.of(context).accentColor,
                textColor: Theme.of(context).primaryTextTheme.body1.color,
                child: Text(l.local(l.AvailableStrings.LOGIN_LOGIN)),
                onPressed: _requestLogin,
              )
            ],
          ),
        ],
      ),
    );
  }

  ///Called when sign up is pressed. Allows to sign up.
  void _requestSignUp() async {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => sign_up.SignUp()),
    ); //TODO sign up
  }

  ///Called when login is pressed. Sends a login request.
  ///If the request is accepted, shows the main route.
  void _requestLogin() async {
    if (_formKey.currentState.validate()) {
      logic.login(username: _username, password: _password).then((returned) {
        if (returned) {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => bottom.BottomNavigation()),
          );
        }
      });
    }
  }
}
