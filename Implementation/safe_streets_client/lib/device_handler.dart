import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:permission/permission.dart';

///Returns a future indicating whether the app has location permissions.
Future<bool> hasPositionPermissions() => _checkPerm(PermissionName.Location);

///Returns a future indicating whether the app has camera permissions.
Future<bool> hasCameraPermissions() => _checkPerm(PermissionName.Camera);

///Returns a future indicating whether the app has the specified permission.
///If an error occurs, this logs it and returns false.
Future<bool> _checkPerm(PermissionName name) {
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
  }).catchError((e) {
    log(e);
    return false;
  });
}

///Returns a future with the device position.
///If an error occurs, this logs it and returns (0,0).
Future<DevicePosition> getDevicePosition() {
  Geolocator geo = Geolocator();
  geo.forceAndroidLocationManager = true;
  return geo
      .getCurrentPosition(desiredAccuracy: LocationAccuracy.best)
      .then((position) => DevicePosition(
            latitude: position?.latitude,
            longitude: position?.longitude,
          ))
      .catchError((e) {
    log(e);
    return DevicePosition(latitude: 0, longitude: 0);
  });
}

///A class that contains a position, expressed in latitude and longitude.
class DevicePosition {
  double latitude, longitude;

  DevicePosition({@required this.latitude, @required this.longitude});
}
