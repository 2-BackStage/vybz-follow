package com.vybz.follow_service.follow.infrastructure;

import com.vybz.follow_service.follow.domain.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends MongoRepository<Follow, String>, FollowRepositoryCustom {

    /**
     * 사용자 uuid로 팔로잉 정보 조회
     * @param userUuid
     */
    @Query(value = "{ 'follower.userUuid': ?0 }")
    List<Follow> findAllByUserUuid(String userUuid);

    /**
     * 버스커 uuid로 팔로워 정보 조회
     * @param buskerUuid
     */
    @Query(value = "{ 'following.buskerUuid': ?0 }")
    List<Follow> findAllByBuskerUuid(String buskerUuid);

    /**
     * 사용자 uuid와 버스커 uuid로 팔로잉 여부 확인
     * @param userUuid
     * @param buskerUuid
     */
    @Query(value = "{ 'follower.userUuid': ?0, 'following.buskerUuid': ?1 }", exists = true)
    boolean existsByUserUuidAndBuskerUuid(String userUuid, String buskerUuid);

    /**
     * 사용자 uuid와 버스커 uuid로 팔로잉 정보 조회
     * @param userUuid
     * @param buskerUuid
     */
    @Query(value = "{ 'follower.userUuid': ?0, 'following.buskerUuid': ?1 }")
    Optional<Follow> findByUserUuidAndBuskerUuid(String userUuid, String buskerUuid);

}
