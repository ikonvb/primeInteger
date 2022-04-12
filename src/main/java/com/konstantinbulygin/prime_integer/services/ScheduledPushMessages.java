package com.konstantinbulygin.prime_integer.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledPushMessages {

    @Scheduled(fixedRate = 2000)
    public void sendMessage(SimpMessagingTemplate simpMessagingTemplate, List<Integer> list) {
        simpMessagingTemplate.convertAndSend("/topic/automessage", LocalDateTime.now());
    }

}
