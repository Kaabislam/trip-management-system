package com.kaab.trip_service.service;

import com.kaab.trip_service.model.Trip;
import com.kaab.trip_service.model.TripStatus;
import com.kaab.trip_service.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Create a new trip
    public Trip createTrip(String origin, String destination) {
        Trip trip = new Trip(origin, destination);
        return tripRepository.save(trip);
    }

    private void updateTransporterAvailability(int transporterId, boolean availability) {
        String url = "http://localhost:9094/transporter/" + transporterId + "/availability?available=" + availability;
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.put(url, null);  // Make PUT request to update availability
        } catch (Exception e) {
            throw new RuntimeException("Failed to update transporter availability: " + e.getMessage());
        }
    }

    public boolean checkTransporterAvailability(Integer transporterId) {
        // Define the URL of the Transporter Service API
//        String transporterServiceUrl = "http://localhost:9094/transporter/" + transporterId + "/available";
        String transporterServiceUrl = "http://localhost:9094/transporter/" + transporterId + "/available";

        ResponseEntity<String> response = restTemplate.getForEntity(transporterServiceUrl, String.class);
        System.out.println("API Response: " + response.getBody());

        return response.getBody() != null && response.getBody().equalsIgnoreCase("Transporter is available.");

    }
    public Trip bookTrip(int tripId, Integer transporterId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            if (trip.getStatus() == TripStatus.CREATED) {
                boolean isTransporterAvailable = checkTransporterAvailability(transporterId);
                if (isTransporterAvailable) {
                    // API call to update transporter availability to false
                    updateTransporterAvailability(transporterId, false);

                    trip.setStatus(TripStatus.BOOKED);
                    trip.setBookedAt(LocalDateTime.now());
                    trip.setTransporterId(transporterId);  // Assign transporter
                    tripRepository.save(trip);
                    sendTripStatusUpdate(trip, "Trip Booked");
                } else {
                    // Handle case where the transporter is not available
                    throw new RuntimeException("Transporter not available for the trip.");
                }
            }
            return trip;
        }
        return null;
    }



    // Start the trip
    public Trip startTrip(int tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            if (trip.getStatus() == TripStatus.BOOKED) {
                trip.setStatus(TripStatus.RUNNING);
                trip.setStartedAt(LocalDateTime.now());
                tripRepository.save(trip);
                sendTripStatusUpdate(trip, "Trip Running");
            }
            return trip;
        }
        return null;
    }

    // Complete the trip
    public Trip completeTrip(int tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            if (trip.getStatus() == TripStatus.RUNNING) {
                trip.setStatus(TripStatus.COMPLETED);
                trip.setCompletedAt(LocalDateTime.now());
                tripRepository.save(trip);
                sendTripStatusUpdate(trip, "Trip Completed");
            }
            return trip;
        }
        return null;
    }

    // Send trip status updates to a message broker (optional)
    private void sendTripStatusUpdate(Trip trip, String statusMessage) {
        // Send an event to Kafka (or other event broker)
        String message = "Trip ID: " + trip.getId() + " Status: " + statusMessage;
        // kafkaTemplate.send("trip-status-updates", Integer.toString(trip.getId()), message);
    }

    // Get the current status of the trip
    public String getTripStatus(int tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            return "Trip ID: " + trip.getId() + " Status: " + trip.getStatus();
        }
        return "Trip not found!";
    }
}
