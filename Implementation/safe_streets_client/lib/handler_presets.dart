import 'package:flutter/material.dart';

/// This file contains some presets used in the app, including the [ThemeData].

/// The main color of the app.
const MaterialColor accent = Colors.amber;

/// The format of a date in a string.
const String dateFormat = 'dd/MM/yyyy';

/// The format of a time in a string.
const String timeFormat = 'HH:mm';

/// The default theme is based on the dark theme with the main color of the app.
ThemeData getDefaultTheme() => ThemeData.dark().copyWith(accentColor: accent);

/// Returns the logo of the app on a transparent background.
AssetImage getTransparentLogo() =>
    const AssetImage('assets/images/logo_transparent.png');

/// Returns the title of the app, formatted.
RichText getFormattedAppName() => RichText(
      text: const TextSpan(
        style: const TextStyle(
          fontFamily: 'Roboto',
          color: accent,
          fontSize: 60,
        ),
        children: const <TextSpan>[
          const TextSpan(
            text: 'Safe',
            style: const TextStyle(fontWeight: FontWeight.w900),
          ),
          const TextSpan(
            text: 'Streets',
            style: const TextStyle(fontWeight: FontWeight.w100),
          ),
        ],
      ),
    );
