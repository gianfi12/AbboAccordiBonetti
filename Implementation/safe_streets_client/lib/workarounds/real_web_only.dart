import 'dart:ui' as ui;

export 'dart:html';

/// This file should be imported only when building for web.
///
/// This uses and exports packages that are not available on mobile.
/// See stub_web_only.dart, which substitutes this in mobile builds.

// ignore: camel_case_types
class platformViewRegistry {
  static registerViewFactory(String viewId, dynamic cb) {
    // ignore:undefined_prefixed_name
    ui.platformViewRegistry.registerViewFactory(viewId, cb);
  }
}
