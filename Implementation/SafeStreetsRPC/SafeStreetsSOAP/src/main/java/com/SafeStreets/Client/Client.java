package com.SafeStreets.Client;


import com.SafeStreets.Place;
import com.SafeStreets.User;
import com.google.gson.Gson;

import java.util.Date;

public class Client {
    public static void main(String[] args) {
        DispatcherService dispatcherService = new DispatcherService();
        Dispatcher dispatcher = dispatcherService.getDispatcherPort();
        User user = new User("Gian","", "", "",new Place(),new Place(), null,null,"",new Date(),"");
        Gson gson = new Gson();
        Boolean response = dispatcher.userRegistration(gson.toJson(user), "");
        System.out.println(response);
    }
}