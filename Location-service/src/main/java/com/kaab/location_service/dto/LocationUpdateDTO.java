package com.kaab.location_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationUpdateDTO {
    private Integer transporterId;
    private Double latitude;
    private Double longitude;
    private String timestamp;

}
