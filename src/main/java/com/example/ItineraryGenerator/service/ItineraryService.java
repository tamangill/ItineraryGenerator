/* Taman Gill Copyright 2023
 * This file contains the ItineraryService class, which is responsible for generating
 * an itinerary based on a given location. It interacts with the Google Maps API to
 * retrieve attractions and hotels near the specified location. The generated itinerary
 * consists of a list of attractions and hotels.
 */
package com.example.ItineraryGenerator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ItineraryGenerator.Attraction;
import com.example.ItineraryGenerator.Hotels;
import com.example.ItineraryGenerator.Itinerary;

@Service
public class ItineraryService {
    private final int attractionRadius = 20000; // Radius in meters for attractions
    private final int hotelRadius = 20000; // Radius in meters for hotels

    private final String apiKey; // Your Google Maps API key

    public ItineraryService(@Value("${google.maps.apiKey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public Itinerary generateItinerary(String location) {
        double[] coordinates = getLocationCoordinates(location);

        if (coordinates == null) {
            // Failed to retrieve coordinates for the location
            return null;
        }

        double latitude = coordinates[0];
        double longitude = coordinates[1];

        List<Attraction> attractions = getAttractionsNearLocation(latitude, longitude, attractionRadius, apiKey);
        List<Hotels> hotels = getHotelsNearLocation(latitude, longitude, hotelRadius, apiKey);

        return new Itinerary(attractions, hotels);
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

public List<Attraction> getAttractionsNearLocation(double latitude, double longitude, int radius, String apiKey) {
    List<Attraction> attractions = new ArrayList<>();
    int attractionsLimit = 100; // Maximum number of attractions to retrieve

    try {
        // Construct the API URL
        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + latitude + "," + longitude
                + "&radius=" + radius
                + "&type=tourist_attraction"
                + "&key=" + apiKey;

        // Create a URL object
        URL url = new URL(apiUrl);

        // Create an HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method
        connection.setRequestMethod("GET");

        // Get the API response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response and create Attraction objects
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.getJSONArray("results");
            for (int i = 0; i < Math.min(results.length(), attractionsLimit); i++) {
                JSONObject attractionJson = results.getJSONObject(i);
                String name = attractionJson.getString("name");
                String address = attractionJson.optString("vicinity", "");
                double rating = attractionJson.optDouble("rating", 0.0);

                Attraction attraction = new Attraction(name, address, rating);
                attractions.add(attraction);
            }
        } else {
            // Handle error response
            System.out.println("Error response code: " + responseCode);
        }

        // Disconnect the connection
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return attractions;
}

public List<Hotels> getHotelsNearLocation(double latitude, double longitude, int radius, String apiKey) {
    List<Hotels> hotels = new ArrayList<>();
    int hotelsLimit = 100; // Maximum number of hotels to retrieve

    try {
        // Construct the API URL
        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + latitude + "," + longitude
                + "&radius=" + radius
                + "&type=lodging"
                + "&key=" + apiKey;

        // Create a URL object
        URL url = new URL(apiUrl);

        // Create an HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method
        connection.setRequestMethod("GET");

        // Get the API response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response and create Hotel objects
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.getJSONArray("results");
            for (int i = 0; i < Math.min(results.length(), hotelsLimit); i++) {
                JSONObject hotelJson = results.getJSONObject(i);
                String name = hotelJson.getString("name");
                String address = hotelJson.optString("vicinity", "");
                double rating = hotelJson.optDouble("rating", 0.0);

                Hotels hotel = new Hotels(name, address, rating);
                hotels.add(hotel);
            }
        } else {
            // Handle error response
            System.out.println("Error response code: " + responseCode);
        }

        // Disconnect the connection
        connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}
