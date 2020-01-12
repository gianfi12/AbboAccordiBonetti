import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';

/// A class that contains a position, expressed in latitude and longitude.
class DevicePosition {
  final double latitude, longitude;

  const DevicePosition({@required this.latitude, @required this.longitude})
      : assert(latitude != null),
        assert(longitude != null);

  ///This method is used to get the json serialization of the object
  Map<String, dynamic> toJson() => {
        'latitude': latitude,
        'longitude': longitude,
      };
}

/// An entry of a statistic, with main and secondary text.
class StatisticsItem {
  /// The main text, mandatory.
  final String head;

  /// The shorter secondary text, not mandatory: if absent is set to an empty string.
  final String tail;

  const StatisticsItem({@required this.head, String tail})
      : assert(head != null),
        tail = tail ?? '';
}

/// A report from the user.
class Report {
  /// The time and date of the report.
  DateTime deviceDateTime;

  /// The time and date of when the violation happened if different from the report's.
  DateTime violationDateTime;

  /// The local path of the image containing the license plate.
  String mainImage;

  /// Other local paths of optional images.
  List<String> otherImages;

  /// The position of the device.
  DevicePosition devicePosition;

  /// The position as a string to be parsed.
  String position;

  /// The type of violation.
  String violationType;

  /// The plate number if inserted by the user.
  String plateNumber;

  /// The author of the report.
  String author;

  Report({
    @required this.deviceDateTime,
    this.violationDateTime,
    @required this.mainImage,
    this.otherImages,
    this.devicePosition,
    this.position,
    @required this.violationType,
    this.plateNumber,
    this.author,
  })  : assert(deviceDateTime != null),
        assert(violationType != null) {
    if (otherImages == null) otherImages = [];
  }

  @override
  String toString() {
    return 'Report{\n'
        'deviceDateTime: $deviceDateTime,\n'
        'violationDateTime: $violationDateTime,\n'
        'mainImage: $mainImage,\n'
        'otherImages: $otherImages,\n'
        'devicePosition: $devicePosition,\n'
        'violationType: $violationType,\n'
        'plateNumber: $plateNumber,\n'
        'author: $author\n}\n';
  }

  /// This method is used by the json parser in order to know how to serialize the object in the JSON standard
  Map<String, dynamic> toJson() => {
        'reportOffsetDateTime': deviceDateTime.toString(),
        'odtOfWatchedViolation': violationDateTime?.toString() ?? '',
        'place': position ?? '',
        'devicePositionLat': devicePosition?.latitude ?? '',
        'devicePositionLng': devicePosition?.longitude ?? '',
        'violationType': violationType ?? '',
        'description': '',
        'vehicle': plateNumber ?? '',
        'author': '',
        'mainPicture': imageToString(mainImage) ?? '',
        'otherPictures': imageListToString(otherImages) ?? '',
      };

  /// This method returns a string encoded in base64 with the image specified by the [path]
  String imageToString(String path) {
    List<int> imageBytes = File(path).readAsBytesSync();
    return base64Encode(imageBytes);
  }

  /// This method returns a json list of string that represents the given list of [images] path, in a base64 encoding
  List imageListToString(List<String> images) {
    List jsonList = new List();
    List<int> imageBytes;
    images.forEach((element) => {
          imageBytes = File(element).readAsBytesSync(),
          jsonList.add(base64Encode(imageBytes)),
        });
    return jsonList;
  }

  /// This method is used by the json parser in order to deserialize the object
  factory Report.fromJson(Map<String, dynamic> parsedJson) {
    var placeDecode = jsonDecode(parsedJson["place"]);
    String position =
        "City: " + placeDecode["city"] + " Address: " + placeDecode["address"];
    var devicePositionDecode = placeDecode["coordinate"];
    DateTime offset = DateTime.now();
    if (parsedJson["odtOfWatchedViolation"] != "") {
      offset = DateTime.parse(parsedJson["odtOfWatchedViolation"]);
    }

    return Report(
        deviceDateTime: DateTime.parse(parsedJson["reportOffsetDateTime"]),
        violationDateTime: offset ?? DateTime.parse(parsedJson["reportOffsetDateTime"]),
        mainImage: null,
        otherImages: new List(),
        devicePosition: new DevicePosition(
            latitude: devicePositionDecode["latitude"],
            longitude: devicePositionDecode["longitude"]),
        position: position,
        violationType: parsedJson["violationType"],
        plateNumber: parsedJson["vehicle"],
        author: parsedJson["author"]);
  }
}

/// The type of access a client can have.
enum AccessType {
  USER,
  MUNICIPALITY,
  NOT_REGISTERED,
}
