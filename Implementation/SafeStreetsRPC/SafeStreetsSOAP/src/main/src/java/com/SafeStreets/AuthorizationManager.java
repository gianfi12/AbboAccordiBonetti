package com.SafeStreets;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class AuthorizationManager {

    public static boolean verifyPassword(String hash, String salt, String password) {
        String stringToHash=password+salt;
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(stringToHash.getBytes());
        String hashToVerify = Hex.toHexString(digest);

        return hash.equals(hashToVerify);
    }
}
