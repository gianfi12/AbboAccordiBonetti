import 'package:flutter/foundation.dart';

///A class that contains a position, expressed in latitude and longitude.
class DevicePosition {
  final double latitude, longitude;

  const DevicePosition({@required this.latitude, @required this.longitude})
      : assert(latitude != null),
        assert(longitude != null);
}

///An entry of a statistic, with main and secondary text.
class StatisticsItem {
  ///The main text, mandatory.
  final String head;

  ///The shorter secondary text, not mandatory.
  final String tail;

  const StatisticsItem({@required this.head, tail})
      : assert(head != null),
        tail = tail ?? "",
        assert(tail != null);
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
  String author; //TODO add author

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
        assert(violationDateTime != null),
        assert(author != null);
}
