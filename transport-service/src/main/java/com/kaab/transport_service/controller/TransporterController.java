package com.kaab.transport_service.controller;


import com.kaab.transport_service.model.Transporter;
import com.kaab.transport_service.service.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transporter")
public class TransporterController {

    @Autowired
    private TransporterService transporterService;

    // API to add a new transporter
    @PostMapping("/add")
    public ResponseEntity<Transporter> addTransporter(@RequestBody Transporter transporter) {
        Transporter createdTransporter = transporterService.addTransporter(transporter);
        return ResponseEntity.ok(createdTransporter);
    }

    // API to check availability of a transporter
    @GetMapping("/{id}/available")
    public ResponseEntity<String> checkTransporterAvailability(@PathVariable int id) {
        Optional<Transporter> transporter = transporterService.checkTransporterAvailability(id);
        if (transporter.isPresent() && transporter.get().isAvailable()) {
            return ResponseEntity.ok("Transporter is available.");
        }
        return ResponseEntity.status(404).body("Transporter not available.");
    }

    // API to update availability of a transporter
    @PutMapping("/{id}/availability")
    public ResponseEntity<Transporter> updateTransporterAvailability(@PathVariable int id, @RequestParam boolean available) {
        Optional<Transporter> updatedTransporter = transporterService.updateTransporterAvailability(id, available);
        if (updatedTransporter.isPresent()) {
            return ResponseEntity.ok(updatedTransporter.get());
        }
        return ResponseEntity.status(404).body(null);
    }
}