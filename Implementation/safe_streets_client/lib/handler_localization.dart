import 'dart:developer';

/// The strings that can be translated.
///
/// This enum helps to avoid requesting strings that do not have a translation.
enum AvailableStrings {
  LOGIN_USERNAME_REQ,
  LOGIN_PASSWORD_REQ,
  LOGIN_USERNAME,
  LOGIN_PASSWORD,
  LOGIN_NOT_REGISTERED_QUEST,
  LOGIN_SIGN_UP,
  LOGIN,
  LOGIN_ERROR,
  LOGIN_MUNICIPALITY_MESSAGE,
  LOGIN_MUNICIPALITY_LINK,
  SIGN_EMAIL,
  SIGN_FIRST_NAME,
  SIGN_LAST_NAME,
  SIGN_PLACE_BIRTH,
  SIGN_PLACE_RESIDENCE,
  SIGN_DATE_BIRTH,
  SIGN_FISCAL,
  SIGN_DOCUMENT,
  SIGN_PICTURE,
  SIGN_REQUIRED,
  SIGN_EMAIL_WRONG,
  SIGN_ERROR,
  SIGN_CODE,
  SIGN_DI_IP,
  SIGN_DI_PORT,
  SIGN_DI_PASSWORD,
  PHOTO_TEXT,
  PHOTO_CAMERA,
  PHOTO_DEVICE,
  NAV_NEW_REPORT,
  NAV_AROUND_ME,
  NAV_STATISTICS,
  NAV_SUGGESTIONS,
  NAV_REPORTS,
  REQUEST_PERMISSIONS,
  STATISTICS_DROPDOWN_HINT,
  STATISTICS_EMPTY_DROPDOWN,
  STATISTICS_ERROR,
  REPORT_PICTURE_PREVIEW,
  CATEGORY_CHOOSE,
  ADD_INFO,
  INFO_PLATE,
  INFO_DATE,
  INFO_TIME,
  INFO_POSITION,
  INFO_IMAGES,
  INFO_WRONG_DATE,
  INFO_WRONG_TIME,
  AROUND_CHANGE_AREA,
  MUN_FROM,
  MUN_UNTIL,
  CONFIRM_ABORT,
  CONFIRM_YES,
  CONFIRM_NO,
  REPORT_SENT,
  REPORT_ERROR,
  //StatisticType
  STREETS_STAT,
  EFFECTIVENESS_STAT,
  VEHICLES_STAT,
  VIOLATIONS_STAT,
  //ViolationType
  PARKING_ON_BIKE_LANES,
  PARKING_ON_RESERVED_STALL,
  DOUBLE_PARKING,
  PARKING_ON_PEDESTRIAN_CROSSING,
  PARKING_ON_SIDEWALK,
  PARKING_ON_TRAFFIC_ISLAND,
  PARKING_NOT_PAYED,
  PARKING_ON_RED_ZONE,
  TRAFFIC_LIGHT_VIOLATION,
  SPEED_VIOLATION,
  AGAINST_TRAFFIC_VIOLATION,
  OTHER_VIOLATION,
}

/// The translation of the strings.
const _values = {
  AvailableStrings.LOGIN_USERNAME_REQ: 'Please enter a username',
  AvailableStrings.LOGIN_PASSWORD_REQ: 'Please enter a password',
  AvailableStrings.LOGIN_USERNAME: 'Username',
  AvailableStrings.LOGIN_PASSWORD: 'Password',
  AvailableStrings.LOGIN_NOT_REGISTERED_QUEST: 'Not registered?  ',
  AvailableStrings.LOGIN_SIGN_UP: 'Sign up!',
  AvailableStrings.LOGIN: 'Login',
  AvailableStrings.LOGIN_ERROR: 'Invalid username or password! Retry.',
  AvailableStrings.LOGIN_MUNICIPALITY_MESSAGE:
      'Do you have a municipality activation code? Follow ',
  AvailableStrings.LOGIN_MUNICIPALITY_LINK: 'this link!',
  AvailableStrings.SIGN_EMAIL: 'Email',
  AvailableStrings.SIGN_FIRST_NAME: 'Name',
  AvailableStrings.SIGN_LAST_NAME: 'Surname',
  AvailableStrings.SIGN_PLACE_BIRTH: 'Place of birth',
  AvailableStrings.SIGN_PLACE_RESIDENCE: 'Place of residence',
  AvailableStrings.SIGN_DATE_BIRTH: 'Date of birth',
  AvailableStrings.SIGN_FISCAL: 'Fiscal code',
  AvailableStrings.SIGN_DOCUMENT: 'Choose ID photo',
  AvailableStrings.SIGN_PICTURE: 'Choose your photo',
  AvailableStrings.SIGN_REQUIRED: 'This field is mandatory',
  AvailableStrings.SIGN_EMAIL_WRONG: 'Enter a valid email',
  AvailableStrings.SIGN_ERROR: 'Unable to sign up: try another username.',
  AvailableStrings.SIGN_CODE: 'Activation code',
  AvailableStrings.SIGN_DI_IP: 'Optional: data integration IP',
  AvailableStrings.SIGN_DI_PORT: 'Optional: data integration PORT',
  AvailableStrings.SIGN_DI_PASSWORD: 'Optional: data integration password',
  AvailableStrings.PHOTO_TEXT: 'From where would you like to take the photos?',
  AvailableStrings.PHOTO_CAMERA: 'Camera',
  AvailableStrings.PHOTO_DEVICE: 'Device',
  AvailableStrings.NAV_NEW_REPORT: 'New report',
  AvailableStrings.NAV_AROUND_ME: 'Around me',
  AvailableStrings.NAV_STATISTICS: 'Statistics',
  AvailableStrings.NAV_SUGGESTIONS: 'Suggestions',
  AvailableStrings.NAV_REPORTS: 'Access Reports',
  AvailableStrings.REQUEST_PERMISSIONS: 'Request permissions',
  AvailableStrings.STATISTICS_DROPDOWN_HINT: 'Choose a statistics type',
  AvailableStrings.STATISTICS_EMPTY_DROPDOWN:
      'The statistics will be displayed here',
  AvailableStrings.STATISTICS_ERROR: 'Unable to fetch the statistic',
  AvailableStrings.REPORT_PICTURE_PREVIEW: 'Preview the picture',
  AvailableStrings.CATEGORY_CHOOSE: 'Choose the category',
  AvailableStrings.ADD_INFO: 'Add informations',
  AvailableStrings.INFO_PLATE: 'Plate number',
  AvailableStrings.INFO_DATE: 'Violation date',
  AvailableStrings.INFO_TIME: 'Violation time',
  AvailableStrings.INFO_POSITION: 'Violation position',
  AvailableStrings.INFO_IMAGES: 'Add other images',
  AvailableStrings.INFO_WRONG_DATE: 'Format: dd/mm/yyyy',
  AvailableStrings.INFO_WRONG_TIME: 'Format: hh:mm',
  AvailableStrings.AROUND_CHANGE_AREA: 'Change area',
  AvailableStrings.MUN_FROM: 'From',
  AvailableStrings.MUN_UNTIL: 'Until',
  AvailableStrings.CONFIRM_ABORT: 'The report will not be sent!',
  AvailableStrings.CONFIRM_NO: 'Back',
  AvailableStrings.REPORT_SENT: 'Report sent!',
  AvailableStrings.REPORT_ERROR: 'An error occurred, please retry',
  AvailableStrings.CONFIRM_YES: 'Abort',
  AvailableStrings.STREETS_STAT: 'Streets with most violations',
  AvailableStrings.EFFECTIVENESS_STAT: 'Effectiveness',
  AvailableStrings.VEHICLES_STAT: 'Worst drives',
  AvailableStrings.VIOLATIONS_STAT: 'Most common violation',
  AvailableStrings.PARKING_ON_BIKE_LANES: 'Parking on bike lanes',
  AvailableStrings.PARKING_ON_RESERVED_STALL: 'Parking on reserved stall',
  AvailableStrings.DOUBLE_PARKING: 'Double parking',
  AvailableStrings.PARKING_ON_PEDESTRIAN_CROSSING:
      'Parking on pedestrian crossing',
  AvailableStrings.PARKING_ON_SIDEWALK: 'Parking on sidewalk',
  AvailableStrings.PARKING_ON_TRAFFIC_ISLAND: 'Parking on traffic island',
  AvailableStrings.PARKING_NOT_PAYED: 'Parking not payed',
  AvailableStrings.PARKING_ON_RED_ZONE: 'Parking on red zone',
  AvailableStrings.TRAFFIC_LIGHT_VIOLATION: 'Traffic light violation',
  AvailableStrings.SPEED_VIOLATION: 'Speed violation',
  AvailableStrings.AGAINST_TRAFFIC_VIOLATION: 'Against traffic violation',
  AvailableStrings.OTHER_VIOLATION: 'Other violation',
};

/// This provides a method to obtain a localized string.
///
/// In the future localization can be added without modifying the code elsewhere.
/// If the string is not found, this throws an exception (this case can be avoided
/// with proper testing).
/// If the [key] is null, this throws an [AssertionError].
String local(AvailableStrings key) {
  assert(key != null);
  if (_values.containsKey(key)) return _values[key];
  throw Exception('Missing string: $key');
}

/// Returns a localized string for a key that is defined at compile time.
///
/// If no [AvailableStrings] correspond to the [key], it is logged and an error
/// string that contains the key is returned.
/// If the [key] is null, this throws an [AssertionError].
String localKey(String key) {
  assert(key != null);
  final keyVar = AvailableStrings.values.firstWhere(
      (e) => e.toString() == 'AvailableStrings.' + key,
      orElse: () => null);
  if (keyVar != null) return local(keyVar);
  log('Missing enum for $key');
  return 'No AvailableString for: $key, please report to the developers';
}

String getString(String string){
  for(AvailableStrings availableStrings in AvailableStrings.values){
    if(string == availableStrings.toString().split(".")[1]){
      return local(availableStrings);
    }
  }
  return null;
}
