package com.vybz.follow_service.follow.infrastructure;

import com.vybz.follow_service.follow.domain.Follow;

import java.util.List;

public interface FollowRepositoryCustom {

    /**
     * 사용자 uuid로 팔로잉 목록 조회
     * @param userUuid
     * @param lastId
     * @param pageSize
     * @param page
     * @return
     */
    List<Follow> findFollowingByCursor(String userUuid, String lastId, Integer pageSize, Integer page);

    /**
     * 버스커 uuid로 팔로워 목록 조회
     * @param buskerUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    List<Follow> findFollowerByCursor(String buskerUuid, String lastId, Integer pageSize, Integer page);

    /**
     * 팔로워 정보 업데이트
     * @param userUuid
     * @param profileImageUrl
     * @param nickname
     */
    void updateFollower(String userUuid, String profileImageUrl, String nickname);

    /**
     * 팔로잉 정보 업데이트
     * @param buskerUuid
     * @param profileImageUrl
     * @param nickname
     */
    void updateFollowing(String buskerUuid, String profileImageUrl, String nickname);

    /**
     * 팔로워 삭제
     * @param userUuid
     */
    void deleteFollowerRelationsByUserUuid(String userUuid);

    /**
     * 팔로잉 삭제
     * @param buskerUuid
     */
    void deleteFollowingRelationsByBuskerUuid(String buskerUuid);

}
