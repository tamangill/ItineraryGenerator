package com.example.ItineraryGenerator;

import java.util.List;

public class Itinerary {
    private List<Attraction> attractions;
    private List<Hotels> hotels;

    public Itinerary(List<Attraction> attractions, List<Hotels> hotels) {
        this.attractions = attractions;
        this.hotels = hotels;
    }

    // Getters and setters for attractions and hotels

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public List<Hotels> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotels> hotels) {
        this.hotels = hotels;
    }
}
