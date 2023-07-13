/* Taman Gill Copyright 2023
 * This class represents a attraction with basic 
 * information that will be used to display on the 
 * itinaray. 
 */

package com.example.ItineraryGenerator;

public class Attraction {
    private String name;
    private String address;
    private double rating;
        
    public Attraction(String name, String address, double rating) {
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

    public void displayAttractionDetails() {
        System.out.println("Name: " + name);
        System.out.println("Location: " + address);
        System.out.println("Rating: " + rating);

    }
}
