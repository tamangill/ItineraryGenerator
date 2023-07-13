/* Taman Gill Copyright 2023 
 * This class is responsible for testing the itinerary created
 *  by printing the lists on to terminal.
 */

package com.example.ItineraryGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItineraryGenerator {
    private List<Attraction> attractions;
    private List<Hotels> hotels;
    private Random random;

    public ItineraryGenerator(List<Attraction> attractions, List<Hotels> hotels) {
        this.attractions = attractions;
        this.hotels = hotels;
        this.random = new Random();
    }

    public void generateItineraries(int count) {
        if (hotels.isEmpty() || attractions.isEmpty()) {
            System.out.println("Insufficient data to generate itineraries.");
            return;
        }

        for (int i = 0; i < count; i++) {
            Hotels hotel = getRandomHotels();
            List<Attraction> selectedAttractions = getRandomAttractions(5);

            System.out.println("Itinerary " + (i + 1) + ":");
            System.out.println("Hotel:");
            hotel.displayHotelDetails();

            System.out.println("Attractions:");
            for (int j = 0; j < selectedAttractions.size(); j++) {
                Attraction attraction = selectedAttractions.get(j);
                System.out.println("Attraction " + (j + 1) + ":");
                attraction.displayAttractionDetails();
                System.out.println();
            }
            System.out.println("---------------------------------");
        }
    }

    private Hotels getRandomHotels() {
        int index = random.nextInt(hotels.size());
        return hotels.get(index);
    }

    private List<Attraction> getRandomAttractions(int count) {
        List<Attraction> selectedAttractions = new ArrayList<>();
        int attractionsCount = attractions.size();

        if (count >= attractionsCount) {
            selectedAttractions.addAll(attractions);
        } else {
            List<Integer> selectedIndices = new ArrayList<>();
            while (selectedIndices.size() < count) {
                int index = random.nextInt(attractionsCount);
                if (!selectedIndices.contains(index)) {
                    selectedIndices.add(index);
                    selectedAttractions.add(attractions.get(index));
                }
            }
        }
        return selectedAttractions;
    }
}
