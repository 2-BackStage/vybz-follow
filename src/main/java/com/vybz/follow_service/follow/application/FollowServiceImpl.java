package com.vybz.follow_service.follow.application;

import com.vybz.follow_service.common.entity.BaseResponseStatus;
import com.vybz.follow_service.exception.BaseException;
import com.vybz.follow_service.follow.domain.Follow;
import com.vybz.follow_service.follow.dto.request.RequestAddFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestDeleteFollowDto;
import com.vybz.follow_service.follow.dto.response.ResponseFollowDto;
import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import com.vybz.follow_service.kafka.event.FollowEvent;
import com.vybz.follow_service.kafka.producer.FollowKafkaProducer;
import com.vybz.follow_service.kafka.producer.UnfollowKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final FollowKafkaProducer followKafkaProducer;
    private final UnfollowKafkaProducer unfollowKafkaProducer;

    /**
     * 팔로우 생성
     *
     * @param requestAddFollowDto
     */
    @Override
    public void createFollow(RequestAddFollowDto requestAddFollowDto) {
        if (followRepository.existsByUserUuidAndBuskerUuid(
                requestAddFollowDto.getFollower().get(0).getUserUuid(), requestAddFollowDto.getFollowing().get(0).getBuskerUuid())) {
            throw new BaseException(BaseResponseStatus.ALREADY_FOLLOWED);
        }
        followRepository.save(requestAddFollowDto.toDocument());

        followKafkaProducer.sendFollowEvent(RequestAddFollowDto.toFollowEvent(requestAddFollowDto.getFollower().get(0).getUserUuid(),
                requestAddFollowDto.getFollowing().get(0).getBuskerUuid()));

        log.info("Follow saved: {}", requestAddFollowDto);
    }

    /**
     * 팔로우 여부 확인
     *
     * @param userUuid
     * @param buskerUuid
     * @return
     */
    @Override
    public boolean checkFollow(String userUuid, String buskerUuid) {
        return followRepository.existsByUserUuidAndBuskerUuid(userUuid, buskerUuid);
    }

    /**
     * 유저 팔로잉 리스트 조회
     *
     * @param userUuid
     */
    @Override
    public List<ResponseFollowDto> getFollowingByUserUuid(String userUuid) {
        return followRepository.findAllByUserUuid(userUuid)
                .stream()
                .map(ResponseFollowDto::from)
                .toList();
    }

    /**
     * 버스커 팔로워 리스트 조회
     *
     * @param buskerUuid
     */
    @Override
    public List<ResponseFollowDto> getFollowerByBuskerUuid(String buskerUuid) {
        return followRepository.findAllByBuskerUuid(buskerUuid)
                .stream()
                .map(ResponseFollowDto::from)
                .toList();
    }

    /**
     * 팔로우 삭제
     *
     * @param requestDeleteFollowDto
     */
    @Override
    public void deleteFollowing(RequestDeleteFollowDto requestDeleteFollowDto) {
        Follow follow = followRepository.findByUserUuidAndBuskerUuid(requestDeleteFollowDto.getUserUuid(), requestDeleteFollowDto.getBuskerUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_FOLLOW));
        followRepository.delete(follow);

        unfollowKafkaProducer.sendUnfollowEvent(RequestDeleteFollowDto.toUnfollowEvent(requestDeleteFollowDto.getUserUuid(),
                requestDeleteFollowDto.getBuskerUuid()));
        log.info("Unfollow completed and Kafka event sent: {}", requestDeleteFollowDto);
    }

}
