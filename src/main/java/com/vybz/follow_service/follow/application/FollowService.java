package com.vybz.follow_service.follow.application;

import com.vybz.follow_service.common.util.CursorPageUtil;
import com.vybz.follow_service.follow.dto.request.RequestAddFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestDeleteFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestUpdateFollowerDto;
import com.vybz.follow_service.follow.dto.request.RequestUpdateFollowingDto;
import com.vybz.follow_service.follow.dto.response.ResponseBuskerFollowerDto;
import com.vybz.follow_service.follow.dto.response.ResponseUserFollowingDto;

public interface FollowService {

    /**
     * 팔로우 등록
     * @param requestAddFollowDto
     */
    void createFollow(RequestAddFollowDto requestAddFollowDto);

    /**
     * 팔로우 여부 확인
     * @param userUuid
     * @param buskerUuid
     */
    boolean checkFollow(String userUuid, String buskerUuid);

    /**
     * 사용자 uuid로 팔로잉 목록 조회
     * @param userUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    CursorPageUtil<ResponseUserFollowingDto, String> getFollowingByUserUuid(String userUuid, String lastId, Integer pageSize, Integer page);

    /**
     * 버스커 uuid로 팔로워 목록 조회
     * @param buskerUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    CursorPageUtil<ResponseBuskerFollowerDto, String> getFollowerByBuskerUuid(String buskerUuid, String lastId, Integer pageSize, Integer page);


    /**
     * 유저 uuid 기준으로 관계되는 팔로워 정보 업데이트
     * @param requestUpdateFollowerDto
     */
    void updateFollower(RequestUpdateFollowerDto requestUpdateFollowerDto);

    /**
     * 버스커 uuid 기준으로 관계되는 팔로잉 정보 업데이트
     * @param requestUpdateFollowingDto
     */
    void updateFollowing(RequestUpdateFollowingDto requestUpdateFollowingDto);

    /**
     * 팔로우 삭제
     * @param requestDeleteFollowDto
     */
    void deleteFollowing(RequestDeleteFollowDto requestDeleteFollowDto);

}
