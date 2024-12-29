package com.kaab.location_service.service;


import com.kaab.location_service.model.Location;
import com.kaab.location_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Method to update the location of a transporter
    public Location updateLocation(Integer transporterId, Double latitude, Double longitude) {
        // Get current time for the timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Create a new location object
        Location location = new Location(transporterId, latitude, longitude, timestamp);

        // Save the location to MongoDB
        return locationRepository.save(location);
    }

    // Method to get the latest location of a transporter
    public Location getLatestLocation(Integer transporterId) {
        List<Location> locations = locationRepository.findByTransporterId(transporterId);

        // If no locations found, return null
        if (locations.isEmpty()) {
            return null;
        }

        // Return the latest location based on the timestamp
        return locations.stream()
                .max((loc1, loc2) -> loc1.getTimestamp().compareTo(loc2.getTimestamp()))
                .orElse(null);
    }
}