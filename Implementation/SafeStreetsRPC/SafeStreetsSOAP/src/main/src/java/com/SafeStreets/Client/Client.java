package com.SafeStreets.Client;


import com.SafeStreets.Image;
import com.SafeStreets.Place;
import com.SafeStreets.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.Date;

public class Client {
    public static void main(String[] args) {
        DispatcherService dispatcherService = new DispatcherService();
        Dispatcher dispatcher = dispatcherService.getDispatcherPort();
        User user = new User("Gian","", "", "",new Place(),new Place(),new Image(),"",new Date(),"");
        Gson gson = new Gson();
        Boolean response = dispatcher.userRegistration(gson.toJson(user), "");
        System.out.println(response);
    }
}