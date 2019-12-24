import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

import 'handler_backend.dart' as backend;
import 'handler_device.dart' as device;
import 'handler_localization.dart' as l;
import 'handler_model.dart' as model;
import 'handler_presets.dart' as presets;
import 'workarounds/stub_web_only.dart'
    if (dart.library.html) 'workarounds/real_web_only.dart' as web;

/// A widget that contains the map and a way to change the current location.
class AroundMe extends StatefulWidget {
  final backend.DispatcherInterface dispatcher;

  const AroundMe({Key key, @required this.dispatcher}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _AroundMeState();
}

//TODO(med): add text field functionality and fix overlay.
//TODO(low): side bar menu.
/// The state for the AroundMe widget.
class _AroundMeState extends State<AroundMe> {
  /// Whether this has location permissions.
  bool _hasPermission = false;

  /// The heat map on the current position.
  Widget _heatMap;

  /// Refreshes the permissions and position.
  @override
  void initState() {
    super.initState();
    _refreshPermissionsAndPosition();
  }

  @override
  Widget build(BuildContext context) {
    if (_hasPermission && _heatMap != null) {
      return Stack(
        children: <Widget>[
          _heatMap,
          SafeArea(
            minimum: const EdgeInsets.all(8.0),
            child: Container(
              decoration: BoxDecoration(
                color: presets.getDefaultTheme().bottomAppBarColor,
                borderRadius: BorderRadius.all(Radius.circular(10)),
              ),
              child: Row(
                children: <Widget>[
                  IconButton(
                    icon: Icon(Icons.menu),
                    onPressed: null,
                  ),
                  Flexible(
                    child: TextField(
                      onChanged: (string) => null,
                      decoration: InputDecoration(
                        hintText:
                            l.local(l.AvailableStrings.AROUND_CHANGE_AREA),
                        focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.transparent),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      );
    }

    //Without permissions: show button to request permissions.
    return Center(
      child: FlatButton(
        child: Text(l.local(l.AvailableStrings.REQUEST_PERMISSIONS)),
        onPressed: _refreshPermissionsAndPosition,
      ),
    );
  }

  /// Asks the user for location permissions, when granted refreshes the position.
  Future<void> _refreshPermissionsAndPosition() async {
    device.hasPositionPermissions().then((has) async {
      _hasPermission = has;
      if (has) _refreshPosition(await device.getDevicePosition(context));
    });
  }

  /// Takes the camera to the specified position.
  void _refreshPosition(model.DevicePosition position) async =>
      setState(() => _heatMap = HeatMap(context, widget.dispatcher, position));
}

/// A widget that shows a heat map, correct both for mobile and for web.
///
/// Implementation note: Google Maps widget for Flutter still does not implement
/// heat maps, the only way was to use the JavaScript API; in order to do so,
/// the map was implemented as a local HTML page. A WebView widget is used to
/// show the page on mobile, but this widget is not available in Flutter for web:
/// in the web version a HtmlElementView is used (which is not available for
/// mobile). The packages required for the web version do not allow to build the
/// mobile version, for this reason conditional imports are used.
/// Web and mobile versions further differ in how the assets are accessed, in
/// the presets file.
/// The way this class was implemented (completer, async methods, future...)
/// depends heavily on the capabilities of the framework and the needs of the
/// widgets that are used.
///
class HeatMap extends StatelessWidget {
  /// The controller for the mobile version.
  final Completer<WebViewController> _controller = Completer();

  /// The heat map for web or mobile.
  final Completer<Widget> _widgets = Completer();

  /// Requests the data to display and the html page, constructs the widget.
  ///
  /// The map will be centered on [position].
  HeatMap(
    BuildContext context,
    backend.DispatcherInterface dispatcher,
    model.DevicePosition position,
  ) {
    var listFuture = dispatcher
        .requestDataAnalysis(statisticsType: 'STREETS_STAT', location: position)
        .then((items) => items
            .map((i) => model.DevicePosition(
                latitude: double.parse(i.head),
                longitude: double.parse(i.tail)))
            .toList());
    var uriFuture = presets.getHeatMapURI(
      DefaultAssetBundle.of(context),
      kIsWeb,
      listFuture,
      position,
    );
    _widgets.complete(kIsWeb ? _buildWeb(uriFuture) : _buildMobile(uriFuture));
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      initialData: Center(child: CircularProgressIndicator()),
      future: _widgets.future,
      builder: (context, wid) => wid.data,
    );
  }

  /// Builds the widget for the web.
  Future<Widget> _buildWeb(Future<Uri> uriFuture) async {
    String uriStr = (await uriFuture).toString();
    web.platformViewRegistry.registerViewFactory(
        'heat-map-html',
        (int viewId) => web.IFrameElement()
          ..width = '640'
          ..height = '360'
          ..src = uriStr
          ..style.border = 'none');
    return HtmlElementView(viewType: 'heat-map-html');
  }

  /// Builds the widget for mobile.
  Future<Widget> _buildMobile(Future<Uri> uriFuture) async {
    String uriStr = (await uriFuture).toString();
    _controller.future.then((controller) => controller.loadUrl(uriStr));
    return WebView(
      javascriptMode: JavascriptMode.unrestricted,
      initialUrl: 'about:blank',
      onWebViewCreated: (controller) => _controller.complete(controller),
    );
  }
}
