package com.vybz.follow_service.follow.application;

import com.vybz.follow_service.common.entity.BaseResponseStatus;
import com.vybz.follow_service.common.util.CursorPageUtil;
import com.vybz.follow_service.exception.BaseException;
import com.vybz.follow_service.follow.domain.Follow;
import com.vybz.follow_service.follow.dto.request.RequestAddFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestDeleteFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestUpdateFollowerDto;
import com.vybz.follow_service.follow.dto.request.RequestUpdateFollowingDto;
import com.vybz.follow_service.follow.dto.response.ResponseBuskerFollowerDto;
import com.vybz.follow_service.follow.dto.response.ResponseUserFollowingDto;
import com.vybz.follow_service.follow.infrastructure.FollowRepository;
import com.vybz.follow_service.kafka.producer.FollowKafkaProducer;
import com.vybz.follow_service.kafka.producer.UnfollowKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final MongoTemplate mongoTemplate;
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
     * 사용자 uuid로 팔로잉 목록 조회
     * @param userUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    @Override
    public CursorPageUtil<ResponseUserFollowingDto, String> getFollowingByUserUuid(String userUuid, String lastId, Integer pageSize, Integer page) {
        List<Follow> follows = followRepository.findFollowingByCursor(userUuid, lastId, pageSize + 1, page);
        boolean hasNext = follows.size() > pageSize;
        if (hasNext) {
            follows = follows.subList(0, pageSize);
        }

        List<ResponseUserFollowingDto> dto = follows.stream()
                .flatMap(follow -> follow.getFollowing().stream()
                        .map(following -> ResponseUserFollowingDto.from(
                                follow.getFollower().get(0).getUserUuid(), following
                        ))
                ).toList();

        String nextCursor = hasNext ? follows.get(follows.size() - 1).getId() : null;

        return CursorPageUtil.<ResponseUserFollowingDto, String>builder()
                .content(dto)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .pageSize(pageSize)
                .page(page)
                .build();
    }

    /**
     * 버스커 uuid로 팔로워 목록 조회
     * @param buskerUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    @Override
    public CursorPageUtil<ResponseBuskerFollowerDto, String> getFollowerByBuskerUuid(String buskerUuid, String lastId, Integer pageSize, Integer page) {
        List<Follow> follows = followRepository.findFollowerByCursor(buskerUuid, lastId, pageSize + 1, page);
        boolean hasNext = follows.size() > pageSize;
        if (hasNext) {
            follows = follows.subList(0, pageSize);
        }

        List<ResponseBuskerFollowerDto> dto = follows.stream()
                .flatMap(follow -> follow.getFollower().stream()
                        .map(follower -> ResponseBuskerFollowerDto.from(
                                follow.getFollowing().get(0).getBuskerUuid(), follower
                        ))
                ).toList();

        String nextCursor = hasNext ? follows.get(follows.size() - 1).getId() : null;

        return CursorPageUtil.<ResponseBuskerFollowerDto, String>builder()
                .content(dto)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .pageSize(pageSize)
                .page(page)
                .build();
    }

    /**
     * 팔로워 정보 업데이트
     * @param requestUpdateFollowerDto
     */
    @Override
    public void updateFollower(RequestUpdateFollowerDto requestUpdateFollowerDto) {
        followRepository.updateFollower(requestUpdateFollowerDto.getUserUuid(), requestUpdateFollowerDto.getNickname(), requestUpdateFollowerDto.getProfileImageUrl());
    }

    /**
     * 팔로잉 정보 업데이트
     * @param requestUpdateFollowingDto
     */
    @Override
    public void updateFollowing(RequestUpdateFollowingDto requestUpdateFollowingDto) {
        followRepository.updateFollowing(requestUpdateFollowingDto.getBuskerUuid(), requestUpdateFollowingDto.getNickname(), requestUpdateFollowingDto.getProfileImageUrl());
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
