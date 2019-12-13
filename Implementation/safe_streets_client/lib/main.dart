import 'package:flutter/material.dart';

import 'theme_presets.dart' as theme_presets;
import 'widget_login.dart' as login;

//TODO: Testing
void main() => runApp(MyApp());

///When launched, the app shows the login page.
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,//TODO: remove
      title: 'SafeStreets',
      theme: theme_presets.getDefaultTheme(),
      home: login.Login(),
    );
  }
}
