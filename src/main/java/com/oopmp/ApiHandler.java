package com.oopmp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiHandler {

     public static String getJSON(String query) throws Exception{
        String apiKey = "2d19fe2c8b7f230f84509b2ad6904985";

        String apiUrl = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + query;

        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        String jsonResponse = null;
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            jsonResponse = response.toString();
            System.out.println(jsonResponse);
        } else {
            System.out.println("Error: Failed to retrieve weather data. Response code: " + responseCode);
        }

        connection.disconnect();
        return jsonResponse;
    }

}
