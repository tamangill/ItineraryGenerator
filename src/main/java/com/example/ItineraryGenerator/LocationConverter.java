/* Taman Gill Copyright 2023 
 *  This class uses the Google geocoding api to convert the user enterd location 
 *  which can be in coordintes, zip code, or name and converts all to cooridiantes
 *  that are used by the Google Places api to find the correct location.
 */

package com.example.ItineraryGenerator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LocationConverter {
    private final String apiKey;

    public LocationConverter(String apiKey) {
        this.apiKey = apiKey;
    }

    public double[] getLocationCoordinates(String location) {
        double[] coordinates = new double[2];
        try {
            String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
            String geocodingApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedLocation + "&key=" + apiKey;

            URL url = new URL(geocodingApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray results = jsonResponse.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject locationObj = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    coordinates[0] = locationObj.getDouble("lat");
                    coordinates[1] = locationObj.getDouble("lng");
                }
            } else {
                System.out.println("Error response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}