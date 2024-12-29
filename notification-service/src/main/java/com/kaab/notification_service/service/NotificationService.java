package com.kaab.notification_service.service;

import com.kaab.notification_service.dto.UserInformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleMail(UserInformationDto userInformationDto) {
        System.out.println("userInformationDto = " + userInformationDto.getName() + " " + userInformationDto.getEmail() );
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("abc@gmail.com");
            messageHelper.setTo(userInformationDto.getEmail().toString());
            messageHelper.setSubject(String.format("New User creation with username : %s and email %s successfully", userInformationDto.getName(), userInformationDto.getEmail()));
            messageHelper.setText(String.format("""
                    Hi %s ,
                    You Can now enjoy your trip with our trip App.
                    
                    Best regards,
                    Kaab Islam
                    """,
                    userInformationDto.getName().toString()
                    ));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("User creation notification mail sent!");
        } catch (MailException e) {
            log.error("Exception occurred when sending the mail", e);
            throw new RuntimeException("Exception occurred while sending email", e);
        }
    }

}
