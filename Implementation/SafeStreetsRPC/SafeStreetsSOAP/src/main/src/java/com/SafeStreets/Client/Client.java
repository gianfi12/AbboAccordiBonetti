package com.SafeStreets.Client;


public class Client {
    public static void main(String[] args) {
        DispatcherService dispatcherService = new DispatcherService();
        Dispatcher dispatcher = dispatcherService.getDispatcherPort();
        Boolean response = dispatcher.userRegistration("prova");
        System.out.println(response);
    }
}