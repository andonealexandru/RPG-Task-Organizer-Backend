package com.backend.rpgtask.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {
    @Async
    void sendEmail(SimpleMailMessage simpleMailMessage);
}
