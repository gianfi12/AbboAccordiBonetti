/// This file should be imported only when building for mobile.
///
/// This provides the declaration of some methods and objects only available
/// in the web build, allowing to use them in the code for the web. This should
/// not be used in the code for android and ios.
/// See stub_web_only.dart, which should substitute this in web builds.

// ignore: camel_case_types
class platformViewRegistry {
  static registerViewFactory(String viewId, dynamic cb) {
    throw UnsupportedError('Not implemented in mobile version');
  }
}

class IFrameElement {
  IFrameElement() {
    throw UnsupportedError('Not implemented in mobile version');
  }

  String allow;
  bool allowFullscreen;
  bool allowPaymentRequest;
  String height;
  String name;
  String referrerPolicy;
  String src;
  String width;
  var style;
}
