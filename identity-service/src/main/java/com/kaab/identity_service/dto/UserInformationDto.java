package com.kaab.identity_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInformationDto {
    private String name;
    private String email;

}