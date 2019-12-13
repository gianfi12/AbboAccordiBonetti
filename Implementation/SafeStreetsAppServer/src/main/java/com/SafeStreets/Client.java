package com.SafeStreets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class Client {
    public static void main(String[] args) {
        String string = "";
        try {

            InputStream InputStream = new FileInputStream("/home/gianfi/Documents/object.json");
            InputStreamReader Reader = new InputStreamReader(InputStream);
            BufferedReader br = new BufferedReader(Reader);
            String line;
            while ((line = br.readLine()) != null) {
                string += line + "\n";
            }

            JSONObject jsonObject = new JSONObject(string);
            System.out.println(jsonObject);

            try {
                URL url = new URL("http://localhost:8080/SafeStreetsAppServer/ServerService/userRegistration");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (in.readLine() != null) {
                }
                System.out.println("\nREST Service Invoked Successfully..");
                in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling REST Service");
                System.out.println(e);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}