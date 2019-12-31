//TODO(test): backend
import 'package:flutter/material.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/handler_localization.dart';
import 'package:safe_streets_client/main.dart';
import 'package:safe_streets_client/widget_signup.dart';

void main() async{
  testWidgets('MyWidget has a title and message', (WidgetTester tester) async {
    await tester.pumpWidget(createWidgetForTesting(child: new SignUp()));

    await tester.pumpAndSettle();


    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_EMAIL)), "user@mail.com");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.LOGIN_USERNAME)), "user");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.LOGIN_PASSWORD)), "1234");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_FIRST_NAME)), "02-11-2000");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_DATE_BIRTH)), "user");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_PLACE_BIRTH)), "Milan");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_PLACE_RESIDENCE)), "Milan");
    await tester.enterText(find.byKey(ValueKey(AvailableStrings.SIGN_FISCAL)), "CRESFA2132H3J");
    var titlefinder = find.byKey(ValueKey(AvailableStrings.SIGN_DOCUMENT));
  });
}

Widget createWidgetForTesting({Widget child}){
  return MaterialApp(
    home: child,
  );
}