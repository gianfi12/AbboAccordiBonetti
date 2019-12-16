import 'package:flutter/foundation.dart';

import 'handler_model.dart' as model;

//TODO: implement backend
//TODO: handle errors

/// Returns a future containing whether the login was successful.
Future<bool> login({@required String username, @required String password}) {
  return Future.delayed(Duration(seconds: 1), () => true);
}

/// Returns a future containing the available statistic types.
Future<List<String>> getAvailableStatistics() {
  return Future.delayed(
      Duration(seconds: 1),
      () => [
            'STREETS_STAT',
            'EFFECTIVENESS_STAT',
            'VEHICLES_STAT',
            'VIOLATIONS_STAT',
          ]);
}

Future<List<String>> getAvailableReportCategories() {
  return Future.delayed(
      Duration(seconds: 1),
      () => [
            'PARKING_ON_BIKE_LANES',
            'PARKING_ON_RESERVED_STALL',
            'DOUBLE_PARKING',
            'PARKING_ON_PEDESTRIAN_CROSSING',
            'PARKING_ON_SIDEWALK',
            'PARKING_ON_TRAFFIC_ISLAND',
            'PARKING_NOT_PAYED',
            'PARKING_ON_RED_ZONE',
          ]);
}

/// Returns a future containing the statistic items for the requested type.
Future<List<model.StatisticsItem>> getStatisticsItems(String type) {
  var strings = [
    'PARKING_ON_BIKE_LANES',
    'PARKING_ON_RESERVED_STALL',
    'DOUBLE_PARKING',
    'PARKING_ON_PEDESTRIAN_CROSSING',
    'PARKING_ON_SIDEWALK',
    'PARKING_ON_TRAFFIC_ISLAND',
    'PARKING_NOT_PAYED',
    'PARKING_ON_RED_ZONE',
    'TRAFFIC_LIGHT_VIOLATION',
    'SPEED_VIOLATION',
    'AGAINST_TRAFFIC_VIOLATION',
    'OTHER_VIOLATION',
  ];
  return Future.delayed(
      Duration(seconds: 1),
      () => strings
          .map((s) => model.StatisticsItem(
              head: s, tail: '#' + strings.indexOf(s).toString()))
          .toList());
}
