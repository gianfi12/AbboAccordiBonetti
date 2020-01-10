import 'dart:io';

import 'package:flutter/material.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/handler_backend.dart';
import 'package:safe_streets_client/handler_localization.dart' as l;
import 'package:safe_streets_client/handler_model.dart';

void main() async{
  ///This test verifies that a client can be correctly logged in the system, if it has been previously registered inside the system
  test('Login Functionality', () async {
    var soap = DispatcherInterface.getNew("jak4", "jak");
    AccessType accessType = await soap.login();
    expect(accessType, AccessType.USER);
  });

  ///This test verifies that a client can't be logged in inside the system if the password is wrong
  test('Login with wrong credentials', () {
    var soap = DispatcherInterface.getNew("jak4", "no_pass");
    soap.login().then((s) {
      expect(s, AccessType.NOT_REGISTERED);
    });
  });

  ///This test verifies that a client can't be logged in inside the system if the users is wrong
  test('Login with wrong users', () {
    var soap = DispatcherInterface.getNew("no_user", "jak");
    soap.login().then((s) {
      expect(s, AccessType.NOT_REGISTERED);
    });
  });

  ///This test verifies that a user can correctly perform a registration, if there is not another user with the same credential
  test('Signup Functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("", "");
    File picture = new File('assets/images/user.jpg');
    File document = new File('assets/images/document.jpg');
    var reponse = await soap.userRegistration(username: "user", email:  "user@mail.com", firstName: "Real", lastName: "User", placeOfBirth: "Milan", placeOfResidence: "Milan", picture: picture.path, idCard: document.path, fiscalCode: "SDCHSDC127NASD", dateOfBirth: new DateTime(1995,11,23), password: "not_a_password");
    expect(reponse,true);
  });

  ///This test verifies that a user cannot correctly perform a registration, if there is another user with the same credential
  test('Signup Functionality with user already registered', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("", "");
    File picture = new File('assets/images/user.jpg');
    File document = new File('assets/images/document.jpg');
    var reponse = await soap.userRegistration(username: "jak4", email:  "user@mail.com", firstName: "Real", lastName: "User", placeOfBirth: "Milan", placeOfResidence: "Milan", picture: picture.path, idCard: document.path, fiscalCode: "SDCHSDC127NASD", dateOfBirth: new DateTime(1995,11,23), password: "not_a_password");
    expect(reponse,false);
  });

  ///This test verifies that a New Report can be correctly memorized by the system
  test('New Report functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    File main = new File('assets/images/park-on-sidewalk.png');
    File other = new File('assets/images/park-on-sidewalk2.png');
    var images = [other.path];
    var report = new Report(deviceDateTime: new DateTime(2010,12,23),violationDateTime: new DateTime(2010,12,22), mainImage: main.path,otherImages: images, devicePosition: null,position: "Piazza Leonardo da Vinci 32 Milano", violationType: l.AvailableStrings.PARKING_ON_SIDEWALK.toString(),plateNumber: "AB345CD");
    var response = await soap.newReport(report: report);
    expect(response,true);
  });


  ///This test checks that the client can retrieve the available statistics
  test('Available Statistics functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    var statistics = await soap.getAvailableStatistics();
    expect(statistics[0],"STREETS_STAT");
    expect(statistics[1],"EFFECTIVENESS_STAT");
    expect(statistics[2],"VIOLATIONS_STAT");
  });

  ///This test checks that a municipality with the right contract code can be correctly logged in
  test('Municipality Registration Functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    var response = await soap.municipalityRegistration(code: "14", username: "Florence", password: "ARealPassowrd");
    expect(response,true);
  });

  ///This test checks that the result returned by a VIOLATIONS_STAT request return the right value
  test('Data Analysis Functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    List<StatisticsItem> response = await soap.requestDataAnalysis(statisticsType: l.AvailableStrings.VIOLATIONS_STAT.toString(), location: new DevicePosition(latitude: 45.4408, longitude: 12.3155));
    expect(response[0].head,l.local(l.AvailableStrings.PARKING_ON_RESERVED_STALL));
    expect(response[0].tail, "");
  });

  ///This test checks that the result returned by a VEHICLE_STAT request return the right value
  test('Data Analysis Functionality for Vehicle', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("Milano", "Milan");
    List<StatisticsItem> response = await soap.requestDataAnalysis(statisticsType: l.AvailableStrings.VEHICLES_STAT.toString(), location: new DevicePosition(latitude: 45.4642, longitude: 9.1900));
    expect(response[0].head,"The vehicle with plate number FB452RT has generate 7 violations.");
    expect(response[0].tail, "");
  });

  test('Data Analysis Functionality for Effectivness', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("Milano", "Milan");
    List<StatisticsItem> response = await soap.requestDataAnalysis(statisticsType: l.AvailableStrings.EFFECTIVENESS_STAT.toString(), location: new DevicePosition(latitude: 45.4642, longitude: 9.1900));
    expect(response[0].head,"The stats of the system were:\nNumber of reports: 16\nNumber of users: 2\nRatio reports on users: 8.0\nAll the stat up to 2020-01-09 00:00:00.000");
    expect(response[0].tail, "");
  });

  ///This test checks that the result returned by a  STREETS_STAT request return the right value
  test('Data Analysis Functionality for streets', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("Milano", "Milan");
    List<StatisticsItem> response = await soap.requestDataAnalysis(statisticsType: l.AvailableStrings.STREETS_STAT.toString(), location: new DevicePosition(latitude: 45.4642, longitude: 9.1900));
    expect(response[0].head,"45.4801297");
    expect(response[0].tail, "9.2229332");
  });

  ///This test checks that the client can ge the right set of report send to the system by the usersset
  test('Access Reports Functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("Milano", "Milan");
    var response = await soap.accessReports(from: new DateTime(2010,10,11));
    expect(response[0].violationDateTime, null);
    expect(response[0].mainImage, null);
    expect(response[0].otherImages.length, 0);
    expect(response[0].devicePosition.latitude, 45.47693);
    expect(response[0].devicePosition.longitude, 9.23229);
    expect(response[0].position, "City: Milano Address: Via Camillo Golgi");
    expect(response[0].violationType, "PARKING_ON_SIDEWALK");
    expect(response[0].plateNumber, "FF456ZZ");
    expect(response[0].author, "jak4");
  });

  ///
  test('new report with empty plate number', () async{
    WidgetsFlutterBinding.ensureInitialized();
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    File main = new File('assets/images/park-on-sidewalk.png');
    File other = new File('assets/images/park-on-sidewalk2.png');
    var images = [other.path];
    var report = new Report(deviceDateTime: new DateTime(2011,12,23),violationDateTime: new DateTime(2011,12,22), mainImage: main.path,otherImages: images, devicePosition: null,position: "Piazza Leonardo da Vinci 32 Milano", violationType: l.AvailableStrings.PARKING_ON_SIDEWALK.toString(),plateNumber: null);
    var response = await soap.newReport(report: report);
    expect(response,true);
  });


  

}