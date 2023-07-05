package com.example.ItineraryGenerator;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItineraryGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItineraryGeneratorApplication.class, args);
		      // Create an instance of Itinerary
        Itinerary itinerary = new Itinerary();
        double[] coordinates  = new double[2]; 
        LocationConverter convert = new LocationConverter("AIzaSyBKNQMGCc_uf77FOOlXC_-Wl5uO2jnrVIA");
        coordinates = convert.getLocationCoordinates("Auburn, WA");
        // Retrieve attractions near a location (assuming you have latitude, longitude, radius, and apiKey)
        List<Attraction> attractions = itinerary.getAttractionsNearLocation(coordinates[0], coordinates[1], 8000, "AIzaSyBKNQMGCc_uf77FOOlXC_-Wl5uO2jnrVIA");
        List<Hotels> hotels = itinerary.getHotelsNearLocation(coordinates[0], coordinates[1], 8000, "AIzaSyBKNQMGCc_uf77FOOlXC_-Wl5uO2jnrVIA");
				// Create an instance of the ItineraryGenerator class
    		ItineraryGenerator itineraryGenerator = new ItineraryGenerator(attractions, hotels);

    // Generate the itinerary
    itineraryGenerator.generateItineraries(5);
	}	
	
}
