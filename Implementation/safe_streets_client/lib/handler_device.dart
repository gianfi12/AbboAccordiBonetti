import 'dart:developer';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:image_picker/image_picker.dart';
import 'package:permission/permission.dart';

import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;

/// Returns a future indicating whether the app has location permissions.
Future<bool> hasPositionPermissions() => checkPerm(
    Permission.requestPermissions([PermissionName.Location]),
    PermissionName.Location);

/// Returns a future indicating whether the app has camera permissions.
Future<bool> hasCameraPermissions() => checkPerm(
    Permission.requestPermissions([PermissionName.Camera]),
    PermissionName.Camera);

/// Returns a future indicating whether the app has the specified permission.
///
/// [result] should be the output of a single permission request.
/// If an exception is thrown, this logs it and returns false.
@visibleForTesting
Future<bool> checkPerm(Future<List<Permissions>> result, PermissionName name) {
  assert(result != null);
  assert(name != null);
  return result.then((permissions) {
    for (var p in permissions) {
      if (p.permissionName == name) {
        var permissionStatus = p.permissionStatus;
        return (permissionStatus == PermissionStatus.allow ||
            permissionStatus == PermissionStatus.always ||
            permissionStatus == PermissionStatus.whenInUse);
      }
    }
    return false;
  }).catchError((e) {
    log(e.toString());
    return false;
  });
}

/// Returns a future with the device position.
/// If an exception is thrown, this logs it and rethrows it.
Future<model.DevicePosition> getDevicePosition() {
  Geolocator geo = Geolocator();
  geo.forceAndroidLocationManager = true;
  return getPos(geo.getCurrentPosition(desiredAccuracy: LocationAccuracy.best));
}

/// Returns a future with the device position.
@visibleForTesting
Future<model.DevicePosition> getPos(Future<Position> result) {
  assert(result != null);
  return result.then((position) {
    if (position != null) {
      return model.DevicePosition(
        latitude: position.latitude,
        longitude: position.longitude,
      );
    }
    throw Exception('Position not available');
  }).catchError((e) {
    log(e.toString());
    throw (e);
  });
}

/// Prompts the user to choose a picture from the camera or the device.
///
/// If the user does not choose a picture, this returns null.
Future<File> chooseImage(BuildContext context) async {
  assert(context != null);
  final imageSource = await showDialog<ImageSource>(
    context: context,
    builder: (context) => AlertDialog(
      title: Text(l.local(l.AvailableStrings.PHOTO_TEXT)),
      actions: <Widget>[
        MaterialButton(
          child: Text(l.local(l.AvailableStrings.PHOTO_CAMERA)),
          onPressed: () => Navigator.pop(context, ImageSource.camera),
        ),
        MaterialButton(
          child: Text(l.local(l.AvailableStrings.PHOTO_DEVICE)),
          onPressed: () => Navigator.pop(context, ImageSource.gallery),
        ),
      ],
    ),
  );

  return ImagePicker.pickImage(source: imageSource);
}
