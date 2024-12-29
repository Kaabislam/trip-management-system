package com.kaab.location_service.controller;


import com.kaab.location_service.model.Location;
import com.kaab.location_service.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // API to update the location of a transporter
    @PostMapping("/update")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location) {
        Location updatedLocation = locationService.updateLocation(location.getTransporterId(), location.getLatitude(), location.getLongitude());
        return ResponseEntity.ok(updatedLocation);
    }

    // API to get the latest location of a transporter
    @GetMapping("/{transporterId}")
    public ResponseEntity<Location> getLatestLocation(@PathVariable Integer transporterId) {
        Location latestLocation = locationService.getLatestLocation(transporterId);
        if (latestLocation != null) {
            return ResponseEntity.ok(latestLocation);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}