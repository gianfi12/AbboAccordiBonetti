import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:safe_streets_client/theme_presets.dart';

import 'device_handler.dart' as device;
import 'localized_strings.dart' as l;

///A widget that contains the map and a way to change the current location.
//TODO: Add top navigation.
class AroundMe extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AroundMeGoogleState();
}

///The state for the AroundMe widget that works with Google maps.
class _AroundMeGoogleState extends State<AroundMe> {
  ///The controller for the map.
  final Completer<GoogleMapController> _controller = Completer();

  ///Whether this has location permissions.
  bool _hasPermission = false;

  ///The current position of the camera.
  CameraPosition _currentPosition = CameraPosition(target: LatLng(0, 0));

  @override
  void initState() {
    super.initState();
    _refreshPermissions();
  }

  @override
  Widget build(BuildContext context) {
    //On the web, the map plugin is not yet available.
    if (kIsWeb) {
      //TODO: put web code here.
      return Text("WEB VERSION");
    }

    //Not on the web, with location permission: show map.
    else if (_hasPermission) {
      return Stack(
        children: <Widget>[
          GoogleMap(
            onMapCreated: _controller.complete,
            initialCameraPosition: _currentPosition,
            myLocationEnabled: true,
            myLocationButtonEnabled: false,
            mapType: MapType.normal,
          ),
          SafeArea(
            minimum: const EdgeInsets.all(8.0),
            child: Container(
              decoration: BoxDecoration(
                  color: getDefaultTheme().bottomAppBarColor,
                  borderRadius: BorderRadius.all(Radius.circular(10))),
              child: Row(
                children: <Widget>[
                  IconButton(
                    icon: Icon(Icons.menu),
                    onPressed: () => null, //TODO: menu
                  ),
                  Flexible(
                    child: TextField(
                      onChanged: (string) => null,
                      //TODO verify and change map
                      decoration: InputDecoration(
                        hintText:
                            l.local(l.AvailableStrings.AROUND_CHANGE_AREA),
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: Colors.transparent)),
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

    //Not on the web and without permissions: show button to request permissions.
    return Center(
      child: FlatButton(
        child: Text(l.local(l.AvailableStrings.REQUEST_PERMISSIONS)),
        onPressed: _refreshPermissions,
      ),
    );
  }

  ///Asks the user for location permissions, if granted refreshes the location.
  Future<void> _refreshPermissions() async {
    device.hasPositionPermissions().then((has) {
      _hasPermission = has;
      if (has) _refreshLocation();
    });
  }

  ///Takes the camera to the location of the user.
  void _refreshLocation() async {
    device.getDevicePosition().then((position) {
      setState(() => _currentPosition = CameraPosition(
          target: LatLng(position.latitude, position.longitude), zoom: 14));
      _controller.future.then((controller) => controller
          .animateCamera(CameraUpdate.newCameraPosition(_currentPosition)));
    });
  }
}
