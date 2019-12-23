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

///A report from the user.
class Report {
  ///The time and date of the report.
  DateTime deviceDateTime;

  ///The time and date of when the violation happened if different from the report's.
  DateTime violationDateTime;

  ///The image containing the license plate.
  String mainImage;

  ///Other optional images.
  List<String> otherImages;

  ///The position of the device or the one inserted by the user.
  DevicePosition devicePosition;

  ///The type of violation.
  String violationType;

  ///The plate number if inserted by the user.
  String plateNumber;

  ///The author of the report.
  String author;

  Report({
    @required this.deviceDateTime,
    this.violationDateTime,
    @required this.mainImage,
    this.otherImages,
    @required this.devicePosition,
    @required this.violationType,
    this.plateNumber,
    @required this.author,
  })  : assert(deviceDateTime != null),
        assert(mainImage != null),
        assert(devicePosition != null),
        assert(violationType != null),
        assert(author != null);
}

/// The type of access a client can have.
enum AccessType {
  USER,
  MUNICIPALITY,
  NOT_REGISTERED,
}
