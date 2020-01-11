import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:xml/xml.dart' as xml;

import 'handler_localization.dart' as l;
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
      _SOAP(username, password);

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
  ///
  /// This must set the [model.Report.author] field.
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

/// This is the class responsible of the serialization and deserialization of
/// the client request and the server response, it acts as a middleware between them.
class _SOAP implements DispatcherInterface {
  /// The [_username] is the name selected by the user
  /// while the [_password] is the password selected by the user
  String _username, _password;

  /// [ip] is the ip address where the server is correctly running
  final String ip = '192.168.43.108';

  /// This is the constructor of the SOAP class
  _SOAP(this._username, this._password);

  /// This method is used to access the reports stored by the system, by specifying
  /// the period of time in which we are interested in, by using [from] and [until]
  @override
  Future<List<model.Report>> accessReports(
      {DateTime from, DateTime until}) async {
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction': 'http://SafeStreets.com/Dispatcher/accessReportsRequest',
      },
      body:
          utf8.encode(getSoapAccessReports(_username, _password, from, until)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(new List());
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    List<model.Report> resp = new List();
    var elements = returnElement.toList();
    elements.forEach((element) =>
        resp.add(model.Report.fromJson(json.decode(element.text))));
    print(resp);
    return Future.value(resp);
  }

  /// This method returns a list with the possible statistics that the client can request to the server
  @override
  Future<List<String>> getAvailableStatistics() async {
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction':
            'http://SafeStreets.com/Dispatcher/getAvailableStatisticsRequest',
      },
      body: utf8.encode(getSoapAvailableStatistics(_username, _password)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(new List());
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    List<String> resp = new List();
    var elements = returnElement.toList();
    elements.forEach((element) => resp.add(element.text.replaceAll("\"", "")));
    return Future.value(resp);
  }

  /// This method return the suggestions made by the system to the municipality
  @override
  Future<List<String>> getSuggestions() {
    // TODO(low): implement getSuggestions
    throw UnimplementedError();
  }

  /// This method return an AccessType that indicates how the client has been recognized by the system:
  /// that says if the client is a User, or a Municipality, or if it has not been already registered
  @override
  Future<model.AccessType> login() async {
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction': 'http://SafeStreets.com/Dispatcher/loginRequest',
      },
      body: utf8.encode(getSoapLogin(_username, _password)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(model.AccessType.NOT_REGISTERED);
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    var access;
    var string = returnElement.toList().elementAt(0).text.replaceAll("\"", "");
    if (string == "USER") {
      access = model.AccessType.USER;
    } else if (string == "MUNICIPALITY") {
      access = model.AccessType.MUNICIPALITY;
    } else {
      access = model.AccessType.NOT_REGISTERED;
    }
    return Future.value(access);
  }

  /// This method perform a registration of a Municipality with a valid [code], that is its contract code
  @override
  Future<bool> municipalityRegistration(
      {String code,
      String username,
      String password,
      String dataIntegrationIp,
      String dataIntegrationPort,
      String dataIntegrationPassword}) async {
    var dataIntegrationInfo;
    if (dataIntegrationIp == null) {
      dataIntegrationInfo = new DataIntegrationInfo("", "", "");
    } else {
      dataIntegrationInfo = new DataIntegrationInfo(
          dataIntegrationIp, dataIntegrationPort, dataIntegrationPassword);
    }
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction':
            'http://SafeStreets.com/Dispatcher/municipalityRegistrationRequest',
      },
      body: utf8.encode(getSoapMunicipalityRegistration(
          code, username, password, dataIntegrationInfo)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(false);
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    var resp;
    var string = returnElement.toList().elementAt(0).text.replaceAll("\"", "");
    if (string == "true") {
      resp = true;
    } else if (string == "false") {
      resp = false;
    }
    return Future.value(resp);
  }

  /// This method sends a [report] made by the user to the server in order to be validate and memorized
  @override
  Future<bool> newReport({model.Report report}) async {
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction': 'http://SafeStreets.com/Dispatcher/newReportRequest',
      },
      body: utf8.encode(
          getSoapNewReport(jsonEncode(report.toJson()), _username, _password)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(false);
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    var resp;
    var string = returnElement.toList().elementAt(0).text.replaceAll("\"", "");
    if (string == "true") {
      resp = true;
    } else if (string == "false") {
      resp = false;
    }
    return Future.value(resp);
  }

  /// This method requests to the server the statistic about the provided [statisticsType] and [location]
  @override
  Future<List<model.StatisticsItem>> requestDataAnalysis(
      {String statisticsType, model.DevicePosition location}) async {
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction':
            'http://SafeStreets.com/Dispatcher/requestDataAnalysisRequest',
      },
      body: utf8.encode(
          getSoapDataAnalysis(_username, _password, statisticsType, location)),
    );
    List<model.StatisticsItem> returnList = new List();
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(returnList);
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    //var string = returnElement.toList().elementAt(0).text.replaceAll("\"", "");
    var elements = returnElement.toList();
    elements.forEach((element) =>
        returnList.addAll(statisticFromJson(json.decode(element.text))));

    return Future.value(returnList);
  }

  /// This method is an helper of the requestDataAnalysis method that helps us to deserialize the statistic that comes from the server
  Iterable<model.StatisticsItem> statisticFromJson(
      Map<String, dynamic> parsedJson) {
    List<model.StatisticsItem> statisticsItem = new List();
    String statisticType = parsedJson["statisticType"];
    switch (statisticType) {
      case "VIOLATIONS_STAT":
        statisticsItem.add(new model.StatisticsItem(
            head: l.localKey(parsedJson["violationType"])));
        break;
      case "VEHICLES_STAT":
        statisticsItem.add(new model.StatisticsItem(
            head: "The vehicle with plate number " +
                parsedJson["vehicle"].toUpperCase() +
                " has generate " +
                parsedJson["numberOfViolationsOfVehicle"].toString() +
                " violations."));
        break;
      case "EFFECTIVENESS_STAT":
        DateTime date;
        date = DateTime.parse(parsedJson["date"]);
        statisticsItem.add(new model.StatisticsItem(
            head: "The stats of the system were:\nNumber of reports: " +
                parsedJson["numberOfReports"].toString() +
                "\nNumber of users: " +
                parsedJson["numberOfUsers"].toString() +
                "\nRatio reports on users: " +
                parsedJson["reportsNoDivUsersNo"].toString() +
                "\nAll the stat up to " +
                date.toString()));
        break;
      case "STREETS_STAT":
        var coordinateList = parsedJson["coordinateListForStreet"];
        for (String coordinate in coordinateList) {
          Map<String, dynamic> parsed = jsonDecode(coordinate);
          statisticsItem.add(new model.StatisticsItem(
              head: parsed["latitude"].toString(),
              tail: parsed["longitude"].toString()));
        }
        break;
    }
    return statisticsItem;
  }

  /// This method performs a registration of the user, in which:
  /// [username] is the username selected by the user
  /// [email] is the email provided by the user
  /// [firstName] is the first name of the user
  /// [lastName] is the last name of the user
  /// [placeOfBirth] is where the user was born
  /// [placeOfResidence] is where the user actually live
  /// [picture] is a photo of the user
  /// [idCard] is a photo of the identification document of the user
  /// [fiscalCode] is the unique identifier of the user, usually provided by its county
  /// [dateOfBirth] is when the user was born
  /// [password] is the password selected by the user
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
      String password}) async {
    var user = new User(username, email, firstName, lastName, placeOfBirth,
        placeOfResidence, picture, idCard, fiscalCode, dateOfBirth);
    http.Response response = await http.post(
      'http://' + ip + ':8080/SafeStreetsSOAP/DispatcherService',
      headers: {
        'content-type': 'text/xml',
        'SOAPAction':
            'http://SafeStreets.com/Dispatcher/userRegistrationRequest',
      },
      body: utf8.encode(getSoapUserRegistration(user, password)),
    );
    if (response.statusCode != 200) {
      print("Respons error from the server");
      return Future.value(false);
    }
    var parser = xml.parse(response.body);
    var returnElement = parser.findAllElements("return");
    var resp;
    var string = returnElement.toList().elementAt(0).text.replaceAll("\"", "");
    if (string == "true") {
      resp = true;
    } else if (string == "false") {
      resp = false;
    }
    return Future.value(resp);
  }
}

/// This method returns a string that contains the SOAP request for the accessReport method exposed by the server
String getSoapAccessReports(
    String username, String password, DateTime from, DateTime until) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:accessReports xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  string = string + username;
  string = string + ''''</arg0><arg1>''';
  string = string + password;
  string = string + ''''</arg1><arg2>''';
  string = string + from.toString();
  string = string + ''''</arg2><arg3>''';
  string = string + until.toString();
  string = string + ''''</arg3></ns2:accessReports></S:Body></S:Envelope>''';
  return string;
}

/// This method returns a string that contains the SOAP request for the dataAnalysis method exposed by the server
String getSoapDataAnalysis(String username, String password,
    String statisticsType, model.DevicePosition location) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:requestDataAnalysis xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  string = string + username;
  string = string + ''''</arg0><arg1>''';
  string = string + password;
  string = string + ''''</arg1><arg2>''';
  string = string + statisticsType;
  string = string + ''''</arg2><arg3>''';
  string = string + jsonEncode(location.toJson());
  string =
      string + ''''</arg3></ns2:requestDataAnalysis></S:Body></S:Envelope>''';
  return string;
}

/// This method returns a string that contains the SOAP request for the municipalityRegistration method exposed by the server
String getSoapMunicipalityRegistration(String code, String username,
    String password, DataIntegrationInfo dataIntegrationInfo) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:municipalityRegistration xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  string = string + code;
  string = string + ''''</arg0><arg1>''';
  string = string + username;
  string = string + ''''</arg1><arg2>''';
  string = string + password;
  string = string + ''''</arg2><arg3>''';
  string = string + jsonEncode(dataIntegrationInfo.toJson());
  string = string +
      ''''</arg3></ns2:municipalityRegistration></S:Body></S:Envelope>''';
  return string;
}

/// This method returns a string that contains the SOAP request for the login method exposed by the server
String getSoapLogin(String username, String password) =>
    '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:login xmlns:ns2="http://SafeStreets.com/"><arg0>''' +
    username +
    ''''</arg0><arg1>''' +
    password +
    ''''</arg1></ns2:login></S:Body></S:Envelope>''';

/// This method returns a string that contains the SOAP request for the userRegistration method exposed by the server
String getSoapUserRegistration(User user, String password) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:userRegistration xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  var userJson = jsonEncode(user.toJson());
  string = string + userJson;
  string = string + ''''</arg0><arg1>''';
  string = string + password;
  string = string + ''''</arg1></ns2:userRegistration></S:Body></S:Envelope>''';
  return string;
}

/// This method returns a string that contains the SOAP request for the newReport method exposed by the server
String getSoapNewReport(String report, String username, String password) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:newReport xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  string = string + username;
  string = string + ''''</arg0><arg1>''';
  string = string + password;
  string = string + ''''</arg1><arg2>''';
  string = string + report;
  string = string + ''''</arg2></ns2:newReport></S:Body></S:Envelope>''';
  return string;
}

/// This method returns a string that contains the SOAP request for the getAvailableStatistics method exposed by the server
String getSoapAvailableStatistics(String username, String password) {
  var string =
      '''<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Body><ns2:getAvailableStatistics xmlns:ns2="http://SafeStreets.com/"><arg0>''';
  string = string + username;
  string = string + ''''</arg0><arg1>''';
  string = string + password;
  string = string +
      ''''</arg1></ns2:getAvailableStatistics></S:Body></S:Envelope>''';
  return string;
}

/// This class represents an instance of a user, and it's used in order to serialize a instance of user that can be understood from the server
class User {
  /// These are the parameters of the user that has been provided by the user in order to complete its registration
  String _username,
      _email,
      _firstName,
      _lastName,
      _placeOfBirth,
      _placeOfResidence,
      _picture,
      _idCard,
      _fiscalCode;
  DateTime _dateOfBirth;

  /// This is the constructor of a user
  User(
      this._username,
      this._email,
      this._firstName,
      this._lastName,
      this._placeOfBirth,
      this._placeOfResidence,
      this._picture,
      this._idCard,
      this._fiscalCode,
      this._dateOfBirth);

  /// This method specify to the json parser how an instance of a user can be serialized into JSON
  Map<String, dynamic> toJson() => {
        'username': _username,
        'email': _email,
        'firstName': _firstName,
        'lastName': _lastName,
        'placeOfBirth': _placeOfBirth,
        'placeOfResidence': _placeOfResidence,
        'picture': imageToString(_picture),
        'idCard': imageToString(_idCard),
        'fiscalCode': _fiscalCode,
        'dateOfBirth': _dateOfBirth.toString(),
      };

  /// This method is used to convert a file path provided by the user, into a string that describe the image, that can be inserted into json
  String imageToString(String path) {
    List<int> imageBytes = File(path).readAsBytesSync();
    return base64Encode(imageBytes);
  }
}

/// This is the DataIntegrationInfo class, that contains the information that can be used by the server to communicate with the server of the municipality
class DataIntegrationInfo {
  /// This fields contains the information useful to contact the server of the municipality, like the ip and the password of their machine,
  /// in order to provide an access point for the system
  String _dataIntegrationIp, _dataIntegrationPort, _dataIntegrationPassword;

  /// This is the constructor of the class
  DataIntegrationInfo(this._dataIntegrationIp, this._dataIntegrationPort,
      this._dataIntegrationPassword);

  /// This method returns the password used to enter the access point
  get dataIntegrationPassword => _dataIntegrationPassword;

  /// This method returns the port of the access point
  get dataIntegrationPort => _dataIntegrationPort;

  /// This method returns the ip of the access point
  String get dataIntegrationIp => _dataIntegrationIp;

  /// This method is used by the json parser to serialize the instance of the class in a json object
  Map<String, dynamic> toJson() => {
        'ip': dataIntegrationIp,
        'port': dataIntegrationPort,
        'password': dataIntegrationPassword,
      };
}
