//TODO(test): backend
import 'dart:io';

import 'package:flutter/material.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/handler_backend.dart';
import 'package:safe_streets_client/handler_localization.dart' as l;
import 'package:safe_streets_client/handler_model.dart';

void main() async{
  test('Login Functionality', () async {
    var soap = DispatcherInterface.getNew("jak4", "jak");
    AccessType accessType = await soap.login();
    expect(accessType, AccessType.USER);
  });

  test('Login with wrong credentials', () {
    var soap = DispatcherInterface.getNew("jak4", "no_pass");
    soap.login().then((s) {
      expect(s, AccessType.NOT_REGISTERED);
    });
  });

  test('Signup Functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("", "");
    File picture = new File('assets/images/user.jpg');
    File document = new File('assets/images/document.jpg');
    var reponse = await soap.userRegistration(username: "user", email:  "user@mail.com", firstName: "Real", lastName: "User", placeOfBirth: "Milan", placeOfResidence: "Milan", picture: picture.path, idCard: document.path, fiscalCode: "SDCHSDC127NASD", dateOfBirth: new DateTime(1995,11,23), password: "not_a_password");
    //TODO response
  });

  test('New Report functionality', () async{
    WidgetsFlutterBinding.ensureInitialized();
    var soap = DispatcherInterface.getNew("jak4", "jak");
    File main = new File('assets/images/park-on-sidewalk.png');
    File other = new File('assets/images/park-on-sidewalk2.png');
    var images = [other.path];
    var report = new Report(deviceDateTime: new DateTime(2010,12,23),violationDateTime: new DateTime(2010,12,22), mainImage: main.path,otherImages: images, devicePosition: null,position: "Piazza Leonardo da Vinci 32 Milano", violationType: l.AvailableStrings.PARKING_ON_SIDEWALK.toString(),plateNumber: "AB345CD");
    var reponse = await soap.newReport(report: report);
  });
}