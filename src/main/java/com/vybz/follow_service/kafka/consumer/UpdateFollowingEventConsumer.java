package com.vybz.follow_service.kafka.consumer;

import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import com.vybz.follow_service.kafka.event.FollowingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateFollowingEventConsumer {

    private final FollowRepository followRepository;

    @KafkaListener(
            topics = "update-busker-info",
            groupId = "follow-group",
            containerFactory = "followingKafkaListenerContainerFactory"
    )
    public void consumeUpdateFollowEvent(FollowingEvent event) {
        log.info("🔥 Kafka 수신 - 버스커 정보 변경: {}", event.getBuskerUuid());
        followRepository.updateFollowing(event.getBuskerUuid(), event.getProfileImageUrl(), event.getNickname());
    }

}
