/* Taman Gill Copyright 2023
 * This file contains the ItineraryController class, which serves as the RESTful API
 * controller for generating itineraries based on a given location. It handles the
 * incoming HTTP requests and delegates the processing to the ItineraryService.
 */
package com.example.ItineraryGenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.ItineraryGenerator.Itinerary;
import com.example.ItineraryGenerator.service.ItineraryService;

@RestController
public class ItineraryController {
    private final ItineraryService itineraryService;

    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping("/itinerary/{location}")
    public Itinerary generateItinerary(@PathVariable String location) {
        return itineraryService.generateItinerary(location);
    }
}
