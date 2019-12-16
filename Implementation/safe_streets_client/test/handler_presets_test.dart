import 'package:safe_streets_client/handler_presets.dart';
import 'package:test/test.dart';

void main() {
  group('Not null except assets', () {
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
}
