package com.kaab.transport_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationDto {
    private Integer transporterId;
    private Double latitude;
    private Double longitude;
    private String timestamp; // Optional: in case timestamp is needed
}