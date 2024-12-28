package com.kaab.trip_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String origin;
    private String destination;
    private LocalDateTime createdAt;
    private LocalDateTime bookedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    // Transporter reference, if needed
    private Integer transporterId;

    public Trip(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        this.status = TripStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }
}
