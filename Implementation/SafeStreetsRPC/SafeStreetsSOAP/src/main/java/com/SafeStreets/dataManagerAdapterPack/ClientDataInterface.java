package com.SafeStreets.dataManagerAdapterPack;

public interface ClientDataInterface {
    boolean checkPassword(String username, String password);

    boolean exists(String username);
}
