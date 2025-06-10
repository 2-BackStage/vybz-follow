package com.vybz.follow_service.kafka.consumer;

import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteFollowingEventConsumer {

    private final FollowRepository followRepository;

    @KafkaListener(
            topics = "delete-user-info",
            groupId = "follow-group",
            containerFactory = "stringFollowingKafkaListenerContainerFactory"
    )
    public void consumeDeleteFollowingEvent(String buskerUuid) {
        log.info("🗑️ [Kafka] 버스커 정보 삭제 이벤트 수신: {}", buskerUuid);

        if (!followRepository.existsByBuskerUuid(buskerUuid)) {
            log.warn("⚠️ [Kafka] 삭제할 버스커 정보가 없습니다. buskerUuid: {}", buskerUuid);
            return;
        }

        followRepository.deleteFollowingRelationsByBuskerUuid(buskerUuid);
        log.info("✅ [Kafka] 버스커 정보 삭제 완료: {}", buskerUuid);
    }

}
