import 'package:flutter/material.dart';

import 'handler_backend.dart' as logic;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as data;

///A widget that shows a statistic chosen by the user.
///A statistic is considered as a list of statistics item.
class Statistics extends StatefulWidget {
  Statistics({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => StatisticsState();
}

///A state for the statistics class.
class StatisticsState extends State<Statistics> {
  ///The items for the current statistic.
  List<data.StatisticsItem> _statisticsItems = <data.StatisticsItem>[];

  ///The items in the dropdown menu, the types of statistic available.
  List<String> _dropdownItems = <String>[];

  ///The chosen statistics type.
  String _selectedItem;

  ///Fetches the available statistics.
  @override
  void initState() {
    super.initState();
    logic.DispatcherInterface.getNew("", "") //TODO: pass the entity
        .getAvailableStatistics()
        .then((list) => setState(() => _dropdownItems.addAll(list)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.menu),
          onPressed: () => null, //TODO: menu
        ),
        title: _buildDropdown(),
        centerTitle: true,
      ),
      body: ListView.builder(
          padding: EdgeInsets.all(16),
          itemBuilder: (BuildContext _context, int i) {
            //No statistic type selected: show message.
            if (_selectedItem == null && i == 0) {
              return Text(
                  l.local(l.AvailableStrings.STATISTICS_EMPTY_DROPDOWN));
            }

            //Add a divider between elements.
            if (i.isOdd) {
              return Divider();
            }

            //Build the element if it exists.
            int index = i ~/ 2;
            if (index < _statisticsItems.length) {
              return _buildRow(_statisticsItems[index]);
            }
            return null;
          }),
    );
  }

  ///Builds the dropdown menu for the statistic types.
  Widget _buildDropdown() {
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

  ///Builds a row of the list with the provided item.
  ///The main text is displayed on the left, localized, the secondary on the right.
  Widget _buildRow(data.StatisticsItem item) {
    return ListTile(
      title: Text(l.localKey(item.head)),
      trailing: Text(item.tail),
    );
  }

  ///Called when the user selects a statistics type, changes the statistic displayed.
  void _valueChanged(String value) async {
    logic.DispatcherInterface.getNew("", "") //TODO pass the entity
        .requestDataAnalysis(statisticsType: value, location: null)
        .then((items) => setState(() {
              _selectedItem = value;
              _statisticsItems.clear();
              _statisticsItems.addAll(items);
            }));
  }
}
