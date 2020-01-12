import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/handler_localization.dart';
import 'package:safe_streets_client/widget_signup.dart';

void main() {
  group('Text form placeholder', () {
    var label = AvailableStrings.SIGN_ERROR;
    var error = AvailableStrings.SIGN_ERROR;
    var onSaved = (string) => error.index;
    var validator = (string) => 'test';

    test('Testing correct initialization', () {
      var placeholder = TextFormPlaceholder(
        onSaved: onSaved,
        label: label,
        validator: validator,
        error: error,
      );

      expect(placeholder.validator, validator);
      expect(placeholder.label, label);
      expect(placeholder.onSaved, onSaved);
      expect(placeholder.error, error);
    });

    test('Testing correct initialization', () {
      var placeholder = TextFormPlaceholder(
        onSaved: onSaved,
        label: label,
        error: error,
      );

      expect(placeholder.validator, isNotNull);
      expect(placeholder.label, label);
      expect(placeholder.onSaved, onSaved);
      expect(placeholder.error, error);
    });
  });
}
