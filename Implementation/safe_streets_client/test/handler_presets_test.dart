import 'package:flutter/services.dart';
import 'package:mockito/mockito.dart';
import 'package:safe_streets_client/handler_model.dart';
import 'package:safe_streets_client/handler_presets.dart';
import 'package:test/test.dart';

void main() {
  group('Not null', () {
    test('accent must not be null', () {
      expect(accent, isNotNull);
    });

    test('date format must not be null', () {
      expect(dateFormat, isNotNull);
    });

    test('time must not be null', () {
      expect(timeFormat, isNotNull);
    });

    test('default theme must not return null', () {
      expect(getDefaultTheme(), isNotNull);
    });

    test('transparent logo must not return null', () {
      expect(getTransparentLogo(), isNotNull);
    });

    test('formatted app name must not return null', () {
      expect(getFormattedAppName(), isNotNull);
    });
  });

  group('Consistency', () {
    test('default theme has correct accent color', () {
      expect(getDefaultTheme().accentColor, accent);
    });
  });

  group('Methods in presets do not throw exceptions in normal situation', () {
    test('default theme must not throw exceptions', () {
      expect(getDefaultTheme, returnsNormally);
    });

    test('transparent logo must not throw exceptions', () {
      expect(getTransparentLogo, returnsNormally);
    });

    test('formatted name must not throw exceptions', () {
      expect(getFormattedAppName, returnsNormally);
    });
  });

  group('Tests on getHeatMapUri', () {
    test('values are correctly substituted', () {
      var mock = MockAssetBundle();
      when(mock.loadString('code.html')).thenAnswer((str) => Future.value(
          'before/*DATA_PLACEHOLDER*/middle/*POSITION_PLACEHOLDER*/end'));
      var result = getHeatMapURI(
        mock,
        true,
        Future.value([DevicePosition(latitude: 1, longitude: 2)]),
        DevicePosition(latitude: 3, longitude: 4),
      ).then((uri) => uri.data.contentText
          .replaceAll('%20', ' ')
          .replaceAll('%7B', '{')
          .replaceAll('%7D', '}'));
      expect(
          result,
          completion(
              'beforenew google.maps.LatLng(1.0, 2.0),middlecenter: {lat: 3.0, lng: 4.0},end'));
    });
  });
}

class MockAssetBundle extends Mock implements AssetBundle {}
