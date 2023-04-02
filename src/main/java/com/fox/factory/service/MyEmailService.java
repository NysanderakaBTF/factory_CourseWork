package com.fox.factory.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyEmailService extends SimpleMailMessage {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String text, String to){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("sharaga1937@outlook.com");
            mailMessage.setTo(to);
            mailMessage.setSubject("Chocolate factory purshace");
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
