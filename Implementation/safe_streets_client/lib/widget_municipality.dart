import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import 'handler_backend.dart' as backend;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;
import 'handler_presets.dart' as presets;

/// A list of the reports in the specified date range.
class AccessReports extends StatefulWidget {
  final backend.DispatcherInterface dispatcher;

  AccessReports({Key key, this.dispatcher}) : super(key: key);

  @override
  _AccessReportsState createState() => _AccessReportsState();
}

/// The state for the access reports widget.
class _AccessReportsState extends State<AccessReports> {
  /// The reports to show.
  List<model.Report> reports = [];

  /// The start of the reports dates range, yesterday by default.
  DateTime from = DateTime.now().subtract(Duration(days: 1));

  /// The end of the reports dates range, today by default.
  DateTime until = DateTime.now();

  @override
  void initState() {
    super.initState();
    _onMoreItems();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: ButtonBar(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            RaisedButton(
              child: Text(l.local(l.AvailableStrings.MUN_FROM)),
              onPressed: _onFrom,
            ),
            RaisedButton(
              child: Text(l.local(l.AvailableStrings.MUN_UNTIL)),
              onPressed: _onUntil,
            ),
          ],
        ),
        centerTitle: true,
      ),
      body: ListView.separated(
        itemBuilder: (context, index) {
          if (index + 1 >= reports.length) _onMoreItems();
          return _buildItem(reports[index]);
        },
        separatorBuilder: (context, int) => const Divider(),
        itemCount: reports.length,
        padding: const EdgeInsets.all(8),
      ),
    );
  }

  _buildItem(model.Report r) {
    return ListTile(
      title: Text(l.localKey(r.violationType)),
      subtitle: Text(r.position),
      trailing: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Text(DateFormat(presets.dateFormat).format(r.violationDateTime)),
          Text(DateFormat(presets.timeFormat).format(r.violationDateTime)),
        ],
      ),
      dense: true,
    );
  }

  /// Called when more items are needed, this tries to add items to the list.
  void _onMoreItems() async {
    var more = await widget.dispatcher.accessReports(from: from, until: until);
    setState(() => reports.addAll(more));
  }

  /// Called when the from date is changed: shows a calendar to pick a date.
  void _onFrom() async {
    var date = await showDatePicker(
      context: context,
      initialDate: from,
      firstDate: DateTime(2019),
      lastDate: until,
    );
    if (date != null) {
      setState(() {
        from = date;
        reports.clear();
        _onMoreItems();
      });
    }
  }

  /// Called when the until date is changed: shows a calendar to pick a date.
  void _onUntil() async {
    var date = await showDatePicker(
      context: context,
      initialDate: until,
      firstDate: from,
      lastDate: DateTime.now(),
    );
    if (date != null) {
      setState(() {
        until = date;
        reports.clear();
        _onMoreItems();
      });
    }
  }
}

/// A list of the suggestions available.
class Suggestions extends StatefulWidget {
  final backend.DispatcherInterface dispatcher;

  Suggestions({Key key, this.dispatcher}) : super(key: key);

  @override
  _SuggestionsState createState() => _SuggestionsState();
}

/// The state for the suggestions widget.
class _SuggestionsState extends State<Suggestions> {
  /// The suggestions to show.
  List<String> suggestions = [];

  @override
  void initState() {
    super.initState();
    _onMoreItems();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.separated(
      itemBuilder: (context, index) {
        if (index + 1 >= suggestions.length) _onMoreItems();
        return _buildItem(suggestions[index]);
      },
      separatorBuilder: (context, int) => const Divider(),
      itemCount: suggestions.length,
      padding: const EdgeInsets.all(8),
    );
  }

  _buildItem(String s) {
    return ListTile(
      title: Text(l.localKey(s)),
    );
  }

  /// Called when more items are needed, this tries to add items to the list.
  void _onMoreItems() async {
    var more = await widget.dispatcher.getSuggestions();
    setState(() => suggestions.addAll(more));
  }
}
