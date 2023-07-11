package com.example.ItineraryGenerator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Itinerary1 {
    // Existing attributes and constructor
    
public List<Attraction> getAttractionsNearLocation(double latitude, double longitude, int radius, String apiKey) {
    List<Attraction> attractions = new ArrayList<>();
    int attractionsLimit = 30; // Maximum number of attractions to retrieve

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
    int hotelsLimit = 15; // Maximum number of hotels to retrieve

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

     // Method to print the itinerary
    public void printItinerary(List<Attraction> attractions, List<Hotels> hotels) {
        System.out.println("Itinerary:");
        for (int i = 0; i < attractions.size(); i++) {
            Attraction attraction = attractions.get(i);
           
            System.out.println("Attraction " + (i + 1));
            System.out.println("Name: " + attraction.getName());
            System.out.println("Attraction Address: " + attraction.getAddress());
            System.out.println("Rating: " + attraction.getRating());
            System.out.println();


      }
      for (int i = 0; i < hotels.size(); i++) {   
         Hotels hotel = hotels.get(i);
        System.out.println("Hotel " + (i + 1));
        System.out.println("Name: " + hotel.getName());
        System.out.println("Hotel Address: " + hotel.getAddress());
        System.out.println("Rating: " + hotel.getRating());
        System.out.println();
      }
    }
}
