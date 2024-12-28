package com.kaab.trip_service.controller;

import com.kaab.trip_service.model.Trip;
import com.kaab.trip_service.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    // Track trip status
    @GetMapping("/status/{tripId}")
    public ResponseEntity<String> trackTripStatus(@PathVariable int tripId) {
        String status = tripService.getTripStatus(tripId);
        return ResponseEntity.ok(status);
    }

    // Create a new trip
    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(@RequestParam String origin, @RequestParam String destination) {
        Trip trip = tripService.createTrip(origin, destination);
        return ResponseEntity.ok(trip);
    }

    // Book a trip (assign transporter as part of this step)
    @PostMapping("/book/{tripId}")
    public ResponseEntity<Trip> bookTrip(@PathVariable int tripId, @RequestParam Integer transporterId) {
        Trip trip = tripService.bookTrip(tripId, transporterId);
        if (trip != null) {
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Start the trip
    @PostMapping("/start/{tripId}")
    public ResponseEntity<Trip> startTrip(@PathVariable int tripId) {
        Trip trip = tripService.startTrip(tripId);
        if (trip != null) {
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Complete the trip
    @PostMapping("/complete/{tripId}")
    public ResponseEntity<Trip> completeTrip(@PathVariable int tripId) {
        Trip trip = tripService.completeTrip(tripId);
        if (trip != null) {
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.status(404).body(null);
    }
}
