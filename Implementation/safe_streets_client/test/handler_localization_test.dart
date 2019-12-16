import 'package:safe_streets_client/handler_localization.dart';
import 'package:test/test.dart';

void main() {
  //A value for which exists a translation.
  AvailableStrings available;
  //The name of the enum without the object identifier.
  String availableKey;

  setUp(() async {
    available = AvailableStrings.values.first;
    try {
      local(available);
    } catch (e) {
      fail('No translation for ${available.toString()}, modify setup()');
    }
    //Removing the object identifier: 'AvailableStrings.'
    availableKey = available.toString().substring(17);
  });

  group('With existing string', () {
    test('local returns a string without throwing', () {
      expect(() => local(available), returnsNormally);
      expect(local(available), isNotNull);
    });

    test('localKey returns a string without throwing', () {
      expect(() => localKey(availableKey), returnsNormally);
      expect(localKey(availableKey), isNotNull);
    });
  });

  group('With non existing string', () {
    test('localKey returns an error string that contains the key', () {
      String key = "Non existing key";
      expect(() => localKey(key), returnsNormally);
      expect(localKey("Non existing key"), contains(key));
    });

    test('all AvailableString values have a translation', () {
      AvailableStrings.values.forEach((v) {
        expect(() => local(v), returnsNormally);
        expect(local(v), isNotNull);
      });
    });

    test('passing null causes an assertion error', () {
      expect(() => local(null), throwsA(isA<AssertionError>()));
      expect(() => localKey(null), throwsA(isA<AssertionError>()));
    });
  });
}
