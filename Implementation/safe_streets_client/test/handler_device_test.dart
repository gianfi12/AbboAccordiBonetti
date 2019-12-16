import 'package:geolocator/geolocator.dart';
import 'package:permission/permission.dart';
import 'package:safe_streets_client/handler_device.dart';
import 'package:test/test.dart';

void main() {
  group('Requesting permissions', () {
    test('method recognizes that a permission is allowed', () {
      PermissionName.values.forEach((name) {
        expect(checkPerm(_permissionAllow(name), name), completion(isTrue));
        expect(checkPerm(_permissionUse(name), name), completion(isTrue));
        expect(checkPerm(_permissionAlways(name), name), completion(isTrue));
      });
    });

    test('method recognizes that a permission is denied', () {
      PermissionName.values.forEach((name) {
        expect(checkPerm(_permissionDeny(name), name), completion(isFalse));
        expect(checkPerm(_permissionDecided(name), name), completion(isFalse));
        expect(checkPerm(_permissionAgain(name), name), completion(isFalse));
        expect(checkPerm(_permissionMissing(name), name), completion(isFalse));
      });
    });

    test('on error, returns false', () {
      PermissionName.values.forEach((name) {
        expect(checkPerm(_errorInThen(), name), completion(isFalse));
        expect(checkPerm(_errorInFunction(), name), completion(isFalse));
      });
    });
  });

  group('Request position', () {
    test('position available', () async {
      double lat = 9210.1, lon = 0.233;
      var pos =
          await getPos(Future(() => Position(latitude: lat, longitude: lon)));
      expect(pos.latitude, lat);
      expect(pos.longitude, lon);
    });

    test('position not available', () {
      expect(() => getPos(Future(() => null)), throwsException);
    });
  });
}

//Mocking Permission.requestPermissions, which is static and does not require mockito.
//Grants only the provided permission.
Future<List<Permissions>> _permissionAllow(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.allow : PermissionStatus.deny))
        .toList());

//Denies the provided permission, allows the others.
Future<List<Permissions>> _permissionDeny(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.deny : PermissionStatus.allow))
        .toList());

//Sets the provided permission to notDecided, allows others.
Future<List<Permissions>> _permissionDecided(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(n,
            n == name ? PermissionStatus.notDecided : PermissionStatus.allow))
        .toList());

//Sets the provided permission to not again, allows the others.
Future<List<Permissions>> _permissionAgain(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.notAgain : PermissionStatus.allow))
        .toList());

//Sets to when in use the permission and denies the others.
Future<List<Permissions>> _permissionUse(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.whenInUse : PermissionStatus.deny))
        .toList());

//Sets to always the provided permission and denies the others.
Future<List<Permissions>> _permissionAlways(PermissionName name) =>
    Future(() => PermissionName.values
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.always : PermissionStatus.deny))
        .toList());

//Returns a list that does not contain the required permission.
Future<List<Permissions>> _permissionMissing(PermissionName name) =>
    Future(() => PermissionName.values
        .where((v) => v != name)
        .map((n) => Permissions(
            n, n == name ? PermissionStatus.always : PermissionStatus.deny))
        .toList());

//Causes an error to be thrown in the 'then' block.
Future<List<Permissions>> _errorInThen() => Future(() => null);

//Causes an error to be thrown before the 'catchError' is attached.
Future<List<Permissions>> _errorInFunction() => Future(() => throw Error());
