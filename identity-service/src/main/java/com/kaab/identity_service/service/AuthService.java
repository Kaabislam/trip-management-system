package com.kaab.identity_service.service;

import com.kaab.identity_service.dto.UserInformationDto;
import com.kaab.identity_service.entity.UserCredential;
import com.kaab.identity_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private KafkaTemplate<String, UserInformationDto> kafkaTemplate;
    @Value("${kafka.topic.user-info}")
    private String userInformationTopic;
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        UserInformationDto userInformationDto = new UserInformationDto();
        userInformationDto.setEmail(credential.getEmail());
        userInformationDto.setName(credential.getName());
        kafkaTemplate.send(userInformationTopic, userInformationDto);
        repository.save(credential);

        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
