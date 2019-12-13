import 'package:flutter/material.dart';

///The main color of the app.
const MaterialColor accent = Colors.amber;

///The format of a date in a string.
const String dateFormat = "dd/MM/yyyy";
const String timeFormat = "hh:mm";

///The default theme is based on the dark theme with the main color of the app.
ThemeData getDefaultTheme() => ThemeData.dark().copyWith(accentColor: accent);

///Returns the logo of the app on a transparent background.
AssetImage getTransparentLogo() {
  return AssetImage('assets/images/logo_transparent.png');
}

///Returns the title of the app, formatted.
RichText getFormattedAppName() => RichText(
      text: TextSpan(
        style: TextStyle(
          fontFamily: "Roboto",
          color: accent,
          fontSize: 60,
        ),
        children: <TextSpan>[
          TextSpan(
            text: 'Safe',
            style: TextStyle(fontWeight: FontWeight.w900),
          ),
          TextSpan(
            text: 'Streets',
            style: TextStyle(fontWeight: FontWeight.w100),
          ),
        ],
      ),
    );
