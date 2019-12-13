//TODO: implement backend
//TODO: handle errors
import 'package:flutter/material.dart';
import 'package:safe_streets_client/device_handler.dart';

///Returns a future containing whether the login was successful.
Future<bool> login({@required String username, @required String password}) {
  return Future.delayed(Duration(seconds: 1), () => true);
}

///Returns a future containing the available statistic types.
Future<List<String>> getAvailableStatistics() {
  return Future.delayed(
      Duration(seconds: 1),
      () => [
            "STREETS_STAT",
            "EFFECTIVENESS_STAT",
            "VEHICLES_STAT",
            "VIOLATIONS_STAT",
          ]);
}

Future<List<String>> getAvailableReportCategories() {
  return Future.delayed(
      Duration(seconds: 1),
      () => [
            "PARKING_ON_BIKE_LANES",
            "PARKING_ON_RESERVED_STALL",
            "DOUBLE_PARKING",
            "PARKING_ON_PEDESTRIAN_CROSSING",
            "PARKING_ON_SIDEWALK",
            "PARKING_ON_TRAFFIC_ISLAND",
            "PARKING_NOT_PAYED",
            "PARKING_ON_RED_ZONE",
          ]);
}

///Returns a future containing the statistic items for the requested type.
Future<List<StatisticsItem>> getStatisticsItems(String type) {
  var strings = [
    "PARKING_ON_BIKE_LANES",
    "PARKING_ON_RESERVED_STALL",
    "DOUBLE_PARKING",
    "PARKING_ON_PEDESTRIAN_CROSSING",
    "PARKING_ON_SIDEWALK",
    "PARKING_ON_TRAFFIC_ISLAND",
    "PARKING_NOT_PAYED",
    "PARKING_ON_RED_ZONE",
    "TRAFFIC_LIGHT_VIOLATION",
    "SPEED_VIOLATION",
    "AGAINST_TRAFFIC_VIOLATION",
    "OTHER_VIOLATION",
  ];
  return Future.delayed(
      Duration(seconds: 1),
      () => strings
          .map((s) =>
              StatisticsItem(item: s, tail: "#" + strings.indexOf(s).toString()))
          .toList());
}

///An entry of a statistic, with main and secondary text.
class StatisticsItem {
  ///The main text, mandatory.
  String item;

  ///The shorter secondary text, not mandatory.
  String tail;

  StatisticsItem({@required this.item, this.tail = ""}) {
    tail = tail ?? "";
  }
}

//TODO change logic in handler_backend and put data in handler_data
class Report {
  DateTime deviceDateTime;
  DateTime violationDateTime;
  String mainImage;
  List<String> otherImages;
  DevicePosition devicePosition;
  String violationType;
  String plateNumber;
  String author; //TODO add author and review class

  Report({
    @required this.deviceDateTime,
    this.violationDateTime,
    @required this.mainImage,
    this.otherImages,
    this.devicePosition,
    this.violationType,
    this.author,
  });
}
