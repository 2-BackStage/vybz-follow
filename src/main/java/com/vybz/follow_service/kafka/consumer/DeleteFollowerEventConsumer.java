package com.vybz.follow_service.kafka.consumer;

import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteFollowerEventConsumer {

    private final FollowRepository followRepository;

    @KafkaListener(
            topics = "delete-user-info",
            groupId = "delete-user-follow-group",
            containerFactory = "stringFollowerKafkaListenerContainerFactory"
    )
    public void consumeDeleteFollowerEvent(String userUuid) {
        log.info("🗑️ [Kafka] 유저 정보 삭제 이벤트 수신: {}", userUuid);

        if (!followRepository.existsByUserUuid(userUuid)) {
            log.warn("⚠️ [Kafka] 삭제할 유저 정보가 없습니다. userUuid: {}", userUuid);
            return;
        }

        followRepository.deleteFollowerRelationsByUserUuid(userUuid);
        log.info("✅ [Kafka] 유저 정보 삭제 완료: {}", userUuid);
    }

}
