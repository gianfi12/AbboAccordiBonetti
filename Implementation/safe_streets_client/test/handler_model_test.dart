import 'package:safe_streets_client/handler_model.dart';
import 'package:test/test.dart';

void main() {
  group('DevicePosition', () {
    test('values are correctly stored', () {
      double lat = 10, lon = 0.1;
      var pos = DevicePosition(latitude: lat, longitude: lon);
      expect(pos.latitude, lat);
      expect(pos.longitude, lon);
    });

    test('values can not be null', () {
      expect(() => DevicePosition(latitude: 0.65, longitude: null),
          throwsA(isA<AssertionError>()));
      expect(() => DevicePosition(latitude: null, longitude: 765),
          throwsA(isA<AssertionError>()));
    });
  });

  group('StatisticItem', () {
    test('values are correctly stored', () {
      String h = 'head', t = 'the tail';
      var item = StatisticsItem(head: h, tail: t);
      expect(item.head, h);
      expect(item.tail, t);
    });

    test('tail is set to empty string if not provided', () {
      String h = 'the head';
      var item = StatisticsItem(head: h);
      expect(item.head, h);
      expect(item.tail, '');
    });

    test('values can not be null', () {
      expect(() => StatisticsItem(head: null), throwsA(isA<AssertionError>()));
    });
  });

  group('Report', () {
    var deviceDateTime = DateTime.now();
    var violationDateTime = DateTime.now().subtract(Duration(days: 3));
    var mainImage = 'The main image!';
    var otherImages = ['hello', 'these', 'are', 'images'];
    var devicePosition = DevicePosition(latitude: 53.01, longitude: 1);
    var violationType = 'a type';
    var plateNumber = '123abc';
    var author = 'the author';

    test('values are correctly stored', () {
      var myRep = Report(
        deviceDateTime: deviceDateTime,
        violationDateTime: violationDateTime,
        mainImage: mainImage,
        otherImages: otherImages,
        devicePosition: devicePosition,
        violationType: violationType,
        plateNumber: plateNumber,
        author: author,
      );
      expect(myRep.deviceDateTime, deviceDateTime);
      expect(myRep.violationDateTime, violationDateTime);
      expect(myRep.mainImage, mainImage);
      expect(myRep.otherImages, otherImages);
      expect(myRep.devicePosition, devicePosition);
      expect(myRep.violationType, violationType);
      expect(myRep.plateNumber, plateNumber);
      expect(myRep.author, author);
    });

    test('some fields can be null', () {
      expect(
          () => Report(
              deviceDateTime: deviceDateTime,
              mainImage: mainImage,
              devicePosition: devicePosition,
              violationType: violationType,
              author: author),
          returnsNormally);
    });
  });
}
