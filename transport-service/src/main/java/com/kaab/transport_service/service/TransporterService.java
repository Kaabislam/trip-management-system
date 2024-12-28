package com.kaab.transport_service.service;


import com.kaab.transport_service.model.Transporter;
import com.kaab.transport_service.repository.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransporterService {

    @Autowired
    private TransporterRepository transporterRepository;

    // Add a new transporter
    public Transporter addTransporter(Transporter transporter) {
        Transporter newTransporter = new Transporter();
        transporter.setName(transporter.getName());
        transporter.setVehicleType(transporter.getVehicleType());
        transporter.setAvailable(transporter.isAvailable());
        return transporterRepository.save(transporter);
    }

    // Check if a transporter is available
    public Optional<Transporter> checkTransporterAvailability(int transporterId) {
        return transporterRepository.findById(transporterId);
    }

    // Update the availability of a transporter
    public Optional<Transporter> updateTransporterAvailability(int transporterId, boolean available) {
        Optional<Transporter> transporterOptional = transporterRepository.findById(transporterId);
        if (transporterOptional.isPresent()) {
            Transporter transporter = transporterOptional.get();
            transporter.setAvailable(available);
            transporterRepository.save(transporter);
            return Optional.of(transporter);
        }
        return Optional.empty();
    }
}