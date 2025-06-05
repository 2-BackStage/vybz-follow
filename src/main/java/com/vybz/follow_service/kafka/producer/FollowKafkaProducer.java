package com.vybz.follow_service.kafka.producer;

import com.vybz.follow_service.kafka.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowKafkaProducer {

    private final KafkaTemplate<String, FollowEvent> followKafkaTemplate;

    private final String topicName = "create-follow";

    public void sendFollowEvent(FollowEvent event) {
        log.info("[Kafka] Sending FollowEvent to topic '{}': {}", topicName, event);
        CompletableFuture<SendResult<String, FollowEvent>> future =
                followKafkaTemplate.send(topicName, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send FollowEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent FollowEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }

}
