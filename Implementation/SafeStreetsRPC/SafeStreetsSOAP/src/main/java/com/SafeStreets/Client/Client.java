package com.SafeStreets.Client;


import com.google.gson.Gson;

public class Client {
    public static void main(String[] args) {
        DispatcherService dispatcherService = new DispatcherService();
        Dispatcher dispatcher = dispatcherService.getDispatcherPort();
        Gson gson = new Gson();
        String response = dispatcher.login("gianfi","password");
        System.out.println(response);
    }
}