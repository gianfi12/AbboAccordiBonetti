import 'package:flutter/material.dart';

import 'handler_backend.dart' as backend;
import 'handler_device.dart' as device;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;

/// A widget that shows a statistic chosen by the user.
///
/// A statistic is considered as a list of statistics item.
class Statistics extends StatefulWidget {
  final backend.DispatcherInterface dispatcher;

  const Statistics({Key key, @required this.dispatcher}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _StatisticsState();
}

//TODO(hig): decide and implement how to handle web version (no position).
//TODO(low): side bar menu.
/// A state for the statistics class.
class _StatisticsState extends State<Statistics> {
  /// The items for the current statistic.
  final List<model.StatisticsItem> _statisticsItems = <model.StatisticsItem>[];

  /// The items in the dropdown menu, the types of statistic available.
  final List<String> _dropdownItems = <String>[];

  /// The chosen statistics type.
  String _selectedItem;

  /// Fetches the available statistics.
  @override
  void initState() {
    super.initState();
    widget.dispatcher
        .getAvailableStatistics()
        .then((list) => setState(() => _dropdownItems.addAll(list)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
//        leading: IconButton(
//          icon: Icon(Icons.menu),
//          onPressed: null,
//        ),
        title: _buildDropdown(),
        centerTitle: true,
      ),
      body: ListView.separated(
        itemCount: _statisticsItems.length,
        separatorBuilder: (context, index) => const Divider(),
        padding: const EdgeInsets.all(16),
        itemBuilder: (context, i) {
          //No statistic type selected: show message.
          if (_selectedItem == null && i == 0) {
            return Text(l.local(l.AvailableStrings.STATISTICS_EMPTY_DROPDOWN));
          }

          //Build the element if it exists.
          if (i < _statisticsItems.length) {
            return _buildRow(_statisticsItems[i]);
          }
          return null;
        },
      ),
    );
  }

  /// Builds the dropdown menu for the statistic types.
  _buildDropdown() {
    return DropdownButton(
      items: _dropdownItems
          .map((value) => DropdownMenuItem(
                child: Text(l.localKey(value)),
                value: value,
              ))
          .toList(),
      value: _selectedItem,
      hint: Text(l.local(l.AvailableStrings.STATISTICS_DROPDOWN_HINT)),
      onChanged: _valueChanged,
      isExpanded: true,
    );
  }

  /// Builds a row of the list with the provided item.
  ///
  /// The main text is displayed on the left, localized, the secondary on the
  /// right, not localized.
  _buildRow(model.StatisticsItem item) {
    return ListTile(
      title: Text(item.head),
      trailing: Text(item.tail),
    );
  }

  /// Called when the user selects a statistics type, changes the statistic displayed.
  void _valueChanged(String value) async {
    device.hasPositionPermissions().then((has) async {
      var pos = await device.getDevicePosition(context);
      widget.dispatcher
          .requestDataAnalysis(statisticsType: value, location: pos)
          .then((items) => setState(() {
                _selectedItem = value;
                _statisticsItems.clear();
                _statisticsItems.addAll(items);
              }));
    }).catchError((e) {
      Scaffold.of(context).showSnackBar(SnackBar(
          content: Text(l.local(l.AvailableStrings.STATISTICS_ERROR))));
    });
  }
}
