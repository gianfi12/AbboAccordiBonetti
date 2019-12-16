import 'package:flutter/material.dart';

import 'handler_presets.dart' as presets;
import 'widget_login.dart' as login;

/// The entry point for the app.
void main() => runApp(MyApp());

/// The main app of the SafeStreets client.
///
/// This app is designed for mobile and for web. The web version will differ
/// from the mobile depending on the capabilities of Flutter's infrastructure.
/// When launched, the app shows the login page.
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      //debugShowCheckedModeBanner: false,//TODO: remove
      title: 'SafeStreets',
      theme: presets.getDefaultTheme(),
      home: login.Login(),
    );
  }
}
