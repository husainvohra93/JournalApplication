package com.example.JournalApplication.service;

import com.example.JournalApplication.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "husainTopic", groupId = "husain-group")
    public void consume(SentimentData sentimentData) {
        log.info("kafka Listener called from SentimentConsumerService");
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
