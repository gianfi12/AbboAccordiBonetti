import 'dart:developer';

import 'package:geolocator/geolocator.dart';
import 'package:permission/permission.dart';

import 'handler_model.dart' as model;

/// Returns a future indicating whether the app has location permissions.
Future<bool> hasPositionPermissions() => _checkPerm(PermissionName.Location);

/// Returns a future indicating whether the app has camera permissions.
Future<bool> hasCameraPermissions() => _checkPerm(PermissionName.Camera);

/// Returns a future indicating whether the app has the specified permission.
/// If an exception is thrown, this logs it and returns false.
Future<bool> _checkPerm(PermissionName name) {
  assert(name != null);
  return Permission.requestPermissions([name]).then((permissions) {
    for (var p in permissions) {
      if (p.permissionName == name) {
        var permissionStatus = p.permissionStatus;
        return (permissionStatus == PermissionStatus.allow ||
            permissionStatus == PermissionStatus.always ||
            permissionStatus == PermissionStatus.whenInUse);
      }
    }
    return false;
  }).catchError((e) {//TODO add on
    log(e);
    return false;
  });
}

/// Returns a future with the device position.
/// If an exception is thrown, this logs it and rethrows it.
Future<model.DevicePosition> getDevicePosition() {
  Geolocator geo = Geolocator();
  geo.forceAndroidLocationManager = true;
  return geo
      .getCurrentPosition(desiredAccuracy: LocationAccuracy.best)
      .then((position) {
    if (position != null)
      return model.DevicePosition(
        latitude: position?.latitude,
        longitude: position?.longitude,
      );
    throw Exception('Position not available');
  }).catchError((e) {
    log(e);
    throw (e);
  });
}
