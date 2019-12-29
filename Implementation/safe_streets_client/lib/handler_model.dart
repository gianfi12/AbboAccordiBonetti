import 'package:flutter/foundation.dart';

/// A class that contains a position, expressed in latitude and longitude.
class DevicePosition {
  final double latitude, longitude;

  const DevicePosition({@required this.latitude, @required this.longitude})
      : assert(latitude != null),
        assert(longitude != null);
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
        assert(mainImage != null),
        assert(violationType != null);

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
}

/// The type of access a client can have.
enum AccessType {
  USER,
  MUNICIPALITY,
  NOT_REGISTERED,
}


