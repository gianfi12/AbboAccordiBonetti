import 'dart:async';

import 'package:flutter/foundation.dart';

import 'handler_model.dart' as model;

/// An interface that represents the backend.
///
/// This describes the methods that the backend provides.
/// A class that implements this should:
/// * handle the communication with the server;
/// * ensure that the types are correctly adapted to the servers';
/// * save the username and password and use them to authenticate every request.
abstract class DispatcherInterface {
  /// Returns a class that implements this, with the login info provided.
  ///
  /// If the login info provided turn out to be wrong, through the [login] result,
  /// then the instance can not be used.
  factory DispatcherInterface.getNew(String username, String password) =>
      _MockServer(username, password);

  /// Requests to register an user with the provided data, returns true on success.
  Future<bool> userRegistration({
    @required String username,
    @required String email,
    @required String firstName,
    @required String lastName,
    @required String placeOfBirth,
    @required String placeOfResidence,
    @required String picture,
    @required String idCard,
    @required String fiscalCode,
    @required DateTime dateOfBirth,
    @required String password,
  });

  /// Requests to register a municipality with the provided data, returns true on success.
  Future<bool> municipalityRegistration({
    @required String code,
    @required String username,
    @required String password,
    String dataIntegrationIp,
    String dataIntegrationPort,
    String dataIntegrationPassword,
  });

  /// Returns the [model.AccessType] corresponding to the provided username and password.
  ///
  /// This method should be implemented avoiding multiple calls to the server in
  /// case of multiple calls of this method.
  Future<model.AccessType> login();

  /// Sends a new user report and returns true on success.
  Future<bool> newReport({
    @required model.Report report,
  });

  /// Returns the statistics available to the user or municipality with the saved data.
  Future<List<String>> getAvailableStatistics();

  /// Requests a statistics type for the provided [location].
  ///
  /// The [statisticsType] must be in the list returned by [getAvailableStatistics].
  Future<List<model.StatisticsItem>> requestDataAnalysis({
    @required String statisticsType,
    @required model.DevicePosition location,
  });

  /// Requests the system's reports between the specified dates.
  ///
  /// The reports are in the area of the municipality with the saved username.
  /// If no municipality exists with the username, this returns an empty list.
  /// If [until] is not provided, it will be set to the current date.
  Future<List<model.Report>> accessReports({
    @required DateTime from,
    DateTime until,
  });

  /// Returns the available suggestions for the saved username.
  ///
  /// If the username does not correspond to a municipality, this returns an empty list.
  Future<List<String>> getSuggestions();
}

/// A local mock implementation of the server.
class _MockServer implements DispatcherInterface {
  String username, password;
  model.AccessType accessType;

  _MockServer(this.username, this.password);

  @override
  Future<List<model.Report>> accessReports({DateTime from, DateTime until}) {
    // TODO: implement accessReports
    throw UnimplementedError();
  }

  @override
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

  @override
  Future<List<String>> getSuggestions() {
    // TODO: implement getSuggestions
    throw UnimplementedError();
  }

  @override
  Future<model.AccessType> login() {
    return Future.delayed(Duration(seconds: 1), () {
      return username == 'no'
          ? model.AccessType.NOT_REGISTERED
          : model.AccessType.USER;
    });
  }

  @override
  Future<bool> municipalityRegistration(
      {String code,
      String username,
      String password,
      String dataIntegrationIp,
      String dataIntegrationPort,
      String dataIntegrationPassword}) {
    // TODO: implement municipalityRegistration
    throw UnimplementedError();
  }

  @override
  Future<bool> newReport({model.Report report}) {
    // TODO: implement newReport
    throw UnimplementedError();
  }

  @override
  Future<List<model.StatisticsItem>> requestDataAnalysis(
      {String statisticsType, model.DevicePosition location}) {
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

  @override
  Future<bool> userRegistration(
      {String username,
      String email,
      String firstName,
      String lastName,
      String placeOfBirth,
      String placeOfResidence,
      String picture,
      String idCard,
      String fiscalCode,
      DateTime dateOfBirth,
      String password}) {
    // TODO: implement userRegistration
    throw UnimplementedError();
  }
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

/*//TODO: remove or implement.
  String soap =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:userRegistration xmlns:ns2="http://SafeStreets.com/"><arg0>{"username":"Gian","email":"","firstName":"","lastName":"","placeOfBirth":{},"placeOfResidence":{},"fiscalCode":"","dateOfBirth":"Dec 16, 2019 4:44:10 PM","password":""}</arg0><arg1></arg1></ns2:userRegistration></S:Body></S:Envelope>''';

  http.Response response = await http.post(
    'http://10.42.0.1:8080/SafeStreetsSOAP/DispatcherService',
    headers: {
      'content-type': 'text/xml',
      'SOAPAction': 'http://SafeStreets.com/Dispatcher/userRegistrationRequest',
    },
    body: utf8.encode(soap),
  );
  print(response.body);
 */
