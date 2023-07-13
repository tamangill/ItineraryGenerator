/* Taman Gill Copyright 2023
 * This class represents a hotel with the information 
 * need to create an itinerary. 
 */
package com.example.ItineraryGenerator;

public class Hotels {
    private String name;
    private String address;
    private double rating;

    public Hotels(String name, String address, double rating) {
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

    // Getters and setters for the attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // Method to display hotel details
    public void displayHotelDetails() {
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Rating: " + rating);
        System.out.println();
    }
}