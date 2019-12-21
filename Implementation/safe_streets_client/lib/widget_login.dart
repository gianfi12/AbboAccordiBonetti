import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

import 'handler_backend.dart' as backend;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;
import 'handler_presets.dart' as presets;
import 'widget_bottom.dart' as bottom;
import 'widget_signup.dart' as signUp;

/// Allows to insert the data for the login or to start the sign up process.
///
/// The municipality can sign up only from the web.
class Login extends StatelessWidget {
  Login({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: <Widget>[
          Visibility(
            child: _buildRichText(
              context: context,
              primaryKey: l.AvailableStrings.LOGIN_MUNICIPALITY_MESSAGE,
              linkTextKey: l.AvailableStrings.LOGIN_MUNICIPALITY_LINK,
              onTap: () => Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => signUp.MunSignUp()),
              ),
            ),
            visible: kIsWeb,
          ),
          Column(
            children: <Widget>[
              Image(image: presets.getTransparentLogo()),
              presets.getFormattedAppName(),
              SizedBox(
                width: 400,
                child: _LoginForm(),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

/// A form for the login data and buttons and their logic.
class _LoginForm extends StatefulWidget {
  @override
  _LoginFormState createState() => _LoginFormState();
}

//TODO(low): add links between elements.
/// The state for the login form.
class _LoginFormState extends State<_LoginForm> {
  /// The key to validate the form.
  final _formKey = GlobalKey<FormState>();

  /// Whether the password should be visible.
  bool _isPasswordVisible = false;

  /// The username inserted.
  String _username;

  /// The password inserted.
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
            child: _buildUsernameField(),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: _buildPasswordField(),
          ),
          ButtonBar(
            alignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              _buildRichText(
                context: context,
                primaryKey: l.AvailableStrings.LOGIN_NOT_REGISTERED_QUEST,
                linkTextKey: l.AvailableStrings.LOGIN_SIGN_UP,
                onTap: _onSignUpButton,
              ),
              RaisedButton(
                child: Text(l.local(l.AvailableStrings.LOGIN).toUpperCase()),
                onPressed: _onLoginButton,
              ),
            ],
          ),
        ],
      ),
    );
  }

  _buildUsernameField() {
    return TextFormField(
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
      onSaved: (value) => _username = value.trim(),
    );
  }

  _buildPasswordField() {
    return TextFormField(
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
          onPressed: () {
            setState(() => _isPasswordVisible = !_isPasswordVisible);
          },
          icon: Icon(
              _isPasswordVisible ? Icons.visibility_off : Icons.visibility),
        ),
      ),
      onSaved: (value) => _password = value.trim(),
    );
  }

  /// Called when sign up is pressed, allows to sign up.
  void _onSignUpButton() async => Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => signUp.SignUp()),
      );

  /// Called when login is pressed, sends a login request.
  ///
  /// If the request is accepted, shows the main route and passes it the backend
  /// instance; the main route substitutes this as the navigation stack bottom.
  void _onLoginButton() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      var server = backend.DispatcherInterface.getNew(_username, _password);
      server.login().then((outcome) {
        if (outcome != model.AccessType.NOT_REGISTERED) {
          Navigator.pushAndRemoveUntil(
            context,
            MaterialPageRoute(
                builder: (context) => bottom.BottomNavigation(
                      dispatcher: server,
                    )),
            (r) => false,
          );
        } else {
          Scaffold.of(context).showSnackBar(SnackBar(
            content: Text(l.local(l.AvailableStrings.LOGIN_ERROR)),
          ));
        }
      });
    }
  }
}

/// Builds a [RichText] containing the translation of the [primaryKey] in the
/// default body color, followed by the translation of [linkTextKey] in the
/// accent color; the link text activates on interaction the [onTap] callback.
_buildRichText({
  @required BuildContext context,
  @required l.AvailableStrings primaryKey,
  @required l.AvailableStrings linkTextKey,
  @required GestureTapCallback onTap,
}) {
  return RichText(
    text: TextSpan(
      children: <InlineSpan>[
        TextSpan(
          text: l.local(primaryKey),
          style: TextStyle(color: Theme.of(context).textTheme.body1.color),
        ),
        TextSpan(
          text: l.local(linkTextKey),
          style: TextStyle(color: Theme.of(context).accentColor),
          recognizer: TapGestureRecognizer()..onTap = onTap,
        )
      ],
    ),
  );
}
