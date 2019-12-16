import 'dart:developer';

import 'package:flutter/foundation.dart';
import 'package:geolocator/geolocator.dart';
import 'package:permission/permission.dart';

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
