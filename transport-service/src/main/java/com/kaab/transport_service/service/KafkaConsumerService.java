package com.kaab.transport_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaab.transport_service.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private TransporterService transporterService;

    @Autowired
    private ObjectMapper objectMapper; // To convert JSON to Java objects

    @KafkaListener(topics = "location-updates", groupId = "transporter-service-group")
    public void consumeLocationUpdate(String message) {
        try {
            // Convert the Kafka message (JSON) into LocationDto
            LocationDto locationDto = objectMapper.readValue(message, LocationDto.class);

            // Process the location update in TransporterService
            transporterService.updateTransporterLocation(
                    locationDto.getTransporterId(),
                    locationDto.getLatitude(),
                    locationDto.getLongitude()
            );
        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + e.getMessage());
        }
    }
}