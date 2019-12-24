import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'handler_model.dart' as model;

/// This file contains some assets and presets used in the app, including the [ThemeData].

/// The main color of the app.
const MaterialColor accent = Colors.amber;

/// The format of a date in a string.
const String dateFormat = 'dd/MM/yyyy';

/// The format of a time in a string.
const String timeFormat = 'HH:mm';

/// The default theme is based on the dark theme with the main color of the app.
ThemeData getDefaultTheme() => ThemeData.dark().copyWith(
      buttonTheme: ThemeData.dark().buttonTheme.copyWith(buttonColor: accent),
      accentColor: accent,
    );

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

/// Returns a string representing a web page with a heat map.
///
/// The page is in HTML with JavaScript, loaded from the [assetBundle].
/// The [spots] are the points that will be shown on the map, the map will be
/// centered on [position].
Future<Uri> getHeatMapURI(
  AssetBundle assetBundle,
  bool kIsWeb,
  Future<List<model.DevicePosition>> spots,
  model.DevicePosition position,
) async {
  assert(assetBundle != null);
  assert(kIsWeb != null);
  assert(spots != null);
  assert(position != null);
  var data = (await spots)
      .map((spot) =>
          'new google.maps.LatLng(${spot.latitude}, ${spot.longitude}),')
      .join();
  var center =
      'center: {lat: ${position.latitude}, lng: ${position.longitude}},';
  return assetBundle
      .loadString(kIsWeb ? 'code.html' : 'assets/code.html')
      .then((html) {
    html = html
        .replaceAll('/*DATA_PLACEHOLDER*/', data)
        .replaceAll('/*POSITION_PLACEHOLDER*/', center);
    return Uri.dataFromString(html,
        mimeType: 'text/html', encoding: Encoding.getByName('utf-8'));
  });
}
