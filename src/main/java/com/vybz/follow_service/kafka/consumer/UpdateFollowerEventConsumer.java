package com.vybz.follow_service.kafka.consumer;

import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import com.vybz.follow_service.kafka.event.FollowerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateFollowerEventConsumer {

    private final FollowRepository followRepository;

    @KafkaListener(
            topics = "update-user-info",
            groupId = "follow-group",
            containerFactory = "followerKafkaListenerContainerFactory"
    )
    public void consumeUpdateFollowEvent(FollowerEvent event) {
        log.info("🔥 Kafka 수신 - 유저 정보 변경: {}", event.getUserUuid());
        followRepository.updateFollower(event.getUserUuid(), event.getProfileImageUrl(), event.getNickname());
    }

}
