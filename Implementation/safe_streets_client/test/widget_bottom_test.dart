import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/widget_bottom.dart';

void main() {
  group('Extended bni', () {
    var icon = MockWidget();
    var title = MockWidget();
    var child = () => MockWidget();
    test('BNI with widget is correctly created', () {
      var bni = ExtendedBNI.widget(icon: icon, title: title, child: child);
      expect(bni.icon, icon);
      expect(bni.title, title);
      expect(bni.child, child);
      expect(bni.isRoute, false);
    });

    test('BNI with route is correctly created', () {
      var bni = ExtendedBNI.route(icon: icon, title: title, child: child);
      expect(bni.icon, icon);
      expect(bni.title, title);
      expect(bni.child, child);
      expect(bni.isRoute, true);
    });
  });
}

// mock widget
class MockWidget extends Widget {
  @override
  Element createElement() {
    return null;
  }
}
