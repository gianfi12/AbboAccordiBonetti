import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'handler_backend.dart' as backend;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;
import 'widget_around.dart' as around;
import 'widget_municipality.dart' as municipality;
import 'widget_report.dart' as report;
import 'widget_statistics.dart' as statistics;

/// The main page, with a bottom navigation or a side bar depending on the orientation.
class BottomNavigation extends StatefulWidget {
  static final String name = '/bottomNavigation';
  final backend.DispatcherInterface dispatcher;

  BottomNavigation({Key key, @required this.dispatcher}) : super(key: key);

  @override
  _BottomNavigationState createState() => _BottomNavigationState();
}

//TODO(low): ask confirmation before closing when going back.
/// The state for the main page.
class _BottomNavigationState extends State<BottomNavigation> {
  /// The item of the bottom navigation bar that is displayed by default.
  int _currentItem = 1;

  /// The items of the bottom bar and their actions.
  List<_ExtendedBNI> _navigationItems = [];

  /// THis future controls the setup of the page.
  Future<void> _ready;

  /// Initializes the navigation items.
  @override
  void initState() {
    super.initState();
    _ready = widget.dispatcher.login().then((accessType) {
      if (accessType == model.AccessType.USER && !kIsWeb) {
        _navigationItems.add(_ExtendedBNI.route(
          icon: const Icon(Icons.photo_camera),
          title: Text(l.local(l.AvailableStrings.NAV_NEW_REPORT)),
          child: () => report.NewReport(dispatcher: widget.dispatcher),
        ));
      }
      _navigationItems.addAll([
        _ExtendedBNI.widget(
          icon: const Icon(Icons.place),
          title: Text(l.local(l.AvailableStrings.NAV_AROUND_ME)),
          child: () => around.AroundMe(dispatcher: widget.dispatcher),
        ),
        _ExtendedBNI.widget(
          icon: const Icon(Icons.poll),
          title: Text(l.local(l.AvailableStrings.NAV_STATISTICS)),
          child: () => statistics.Statistics(dispatcher: widget.dispatcher),
        ),
      ]);
      if (accessType == model.AccessType.MUNICIPALITY) {
        _navigationItems.addAll([
          _ExtendedBNI.widget(
            icon: const Icon(Icons.build),
            title: Text(l.local(l.AvailableStrings.NAV_SUGGESTIONS)),
            child: () =>
                municipality.Suggestions(dispatcher: widget.dispatcher),
          ),
          _ExtendedBNI.widget(
            icon: const Icon(Icons.report_problem),
            title: Text(l.local(l.AvailableStrings.NAV_REPORTS)),
            child: () =>
                municipality.AccessReports(dispatcher: widget.dispatcher),
          ),
        ]);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _ready,
      builder: (context, connection) {
        if (connection.connectionState == ConnectionState.done) {
          return OrientationBuilder(
            builder: (context, orientation) {
              switch (orientation) {
                case Orientation.landscape:
                  return _buildLandscape();
                default:
                  return _buildPortrait();
              }
            },
          );
        }
        return const Center(child: const CircularProgressIndicator());
      },
    );
  }

  /// In landscape mode builds a scaffold with a side navigation bar.
  _buildLandscape() {
    return Scaffold(
      body: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
            color: Theme.of(context).primaryColor,
            width: 200,
            child: ListView.separated(
              separatorBuilder: (context, index) => const Divider(),
              itemCount: _navigationItems.length,
              itemBuilder: (context, index) => FlatButton.icon(
                color: (index == _currentItem)
                    ? Theme.of(context).accentColor
                    : null,
                onPressed: () => _onElementTapped(index),
                icon: _navigationItems[index].icon,
                label: _navigationItems[index].title,
              ),
            ),
          ),
          Expanded(
            child: IndexedStack(
              index: _currentItem,
              children: _ExtendedBNI.getChildren(_navigationItems),
            ),
          ),
        ],
      ),
    );
  }

  /// In portrait mode, builds a scaffold with a bottom navigation bar.
  _buildPortrait() {
    return Scaffold(
      body: IndexedStack(
        index: _currentItem,
        children: _ExtendedBNI.getChildren(_navigationItems),
      ),
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Theme.of(context).primaryColor,
        items: _navigationItems,
        currentIndex: _currentItem,
        onTap: _onElementTapped,
      ),
    );
  }

  /// Shows a new page or route depending on the type of the item tapped.
  void _onElementTapped(int i) async => _navigationItems[i].isRoute
      ? Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => _navigationItems[i].child()),
        )
      : setState(() => _currentItem = i);
}

/// A callback function that returns a widget.
typedef WidgetCallback = Widget Function();

/// A bottom navigation bar item, extended to distinguish between a widget and a route.
class _ExtendedBNI extends BottomNavigationBarItem {
  /// The destination of the item when tapped.
  final WidgetCallback child;

  /// If true, the child should be treated as a route, else as a widget on the same route.
  final bool isRoute;

  /// Creates an item as a widget.
  const _ExtendedBNI.widget(
      {@required icon, @required title, @required this.child})
      : assert(child != null),
        isRoute = false,
        super(icon: icon, title: title);

  /// Creates an item as a route.
  const _ExtendedBNI.route({
    @required icon,
    @required title,
    @required this.child,
  })  : assert(child != null),
        isRoute = true,
        super(icon: icon, title: title);

  /// Given some items, this returns them as a list of widgets.
  static List<Widget> getChildren(Iterable<_ExtendedBNI> iterable) =>
      iterable.map((e) => e.child()).toList();
}
