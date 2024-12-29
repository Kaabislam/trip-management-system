package com.kaab.notification_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaab.notification_service.dto.UserInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @Autowired
    private ObjectMapper objectMapper; // To convert JSON to Java objects

    @KafkaListener(topics = "user-info", groupId = "identity-service-group")
    public void consumeLocationUpdate(String message) {
        try {
            // Convert the Kafka message (JSON) into LocationDto
            UserInformationDto userInformationDto = objectMapper.readValue(message, UserInformationDto.class);

            System.out.println("user information from the kafka listener" + userInformationDto);
        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + e.getMessage());
        }
    }
}