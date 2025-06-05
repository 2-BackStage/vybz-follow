package com.vybz.follow_service.kafka.producer;

import com.vybz.follow_service.kafka.event.UnfollowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnfollowKafkaProducer {

    private final KafkaTemplate<String, UnfollowEvent> unfollowKafkaTemplate;

    private final String topicName = "delete-follow";

    public void sendUnfollowEvent(UnfollowEvent event) {
        log.info("[Kafka] Sending UnfollowEvent to topic '{}': {}", topicName, event);
        CompletableFuture<SendResult<String, UnfollowEvent>> future =
                unfollowKafkaTemplate.send(topicName, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send UnfollowEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent UnfollowEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}