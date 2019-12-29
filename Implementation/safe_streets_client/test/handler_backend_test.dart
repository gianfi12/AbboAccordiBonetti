//TODO(test): backend
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_streets_client/handler_localization.dart';
import 'package:safe_streets_client/main.dart';

void main(){
  testWidgets('MyWidget has a title and message', (WidgetTester tester) async {
    await tester.pumpWidget(MyApp());

    var titleFinder = find.text("Username");
    await tester.enterText(find.text("Username"), 'gian');
  });
}