import 'package:flutter/material.dart';

import 'handler_localization.dart' as l;
import 'widget_around.dart' as around;
import 'widget_report.dart' as report;
import 'widget_statistics.dart' as statistics;

///The main page, with a bottom navigation bar.
//TODO: web version.
class BottomNavigation extends StatefulWidget {
  BottomNavigation({Key key}) : super(key: key);

  @override
  _BottomNavigationState createState() => _BottomNavigationState();
}

///The state for the main page
class _BottomNavigationState extends State<BottomNavigation> {
  //TODO check permissions

  ///The item of the bottom navigation bar that is displayed by default.
  int _currentItem = 2; //TODO change

  ///The items of the bottom bar and their actions.
  final List<_ExtendedBNI> _navigationItems = [
    _ExtendedBNI.route(
      icon: Icon(Icons.photo_camera),
      title: Text(l.local(l.AvailableStrings.NAV_NEW_REPORT)),
      child: () => report.NewReport(),
    ),
    _ExtendedBNI.widget(
      icon: Icon(Icons.place),
      title: Text(l.local(l.AvailableStrings.NAV_AROUND_ME)),
      child: () => around.AroundMe(),
    ),
    _ExtendedBNI.widget(
      icon: Icon(Icons.poll),
      title: Text(l.local(l.AvailableStrings.NAV_STATISTICS)),
      child: () => statistics.Statistics(),
    )
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: IndexedStack(
        index: _currentItem,
        children: _ExtendedBNI.getChildren(_navigationItems),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: _navigationItems,
        currentIndex: _currentItem,
        onTap: _elementTapped,
      ),
    );
  }

  ///Shows a new page or route depending on the type of the item tapped.
  void _elementTapped(int i) async {
    _navigationItems[i].isRoute
        ? Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => _navigationItems[i].child()))
        : setState(() => _currentItem = i);
  }
}

///A callback function that returns a widget.
typedef WidgetCallback = Widget Function();

///A bottom navigation bar item, extended to distinguish between a widget and a route.
class _ExtendedBNI extends BottomNavigationBarItem {
  ///The destination of the item when tapped.
  final WidgetCallback child;

  ///If true, the child should be treated as a route, else as a widget on the same route.
  final bool isRoute;

  ///Creates an item as a widget.
  _ExtendedBNI.widget({@required icon, @required title, @required this.child})
      : isRoute = false,
        super(icon: icon, title: title);

  ///Creates an item as a route.
  _ExtendedBNI.route({@required icon, @required title, @required this.child})
      : isRoute = true,
        super(icon: icon, title: title);

  ///Given some items, this returns them as a list of widget.
  static List<Widget> getChildren(Iterable<_ExtendedBNI> iterable) =>
      iterable.map((e) => e.child()).toList();
}
