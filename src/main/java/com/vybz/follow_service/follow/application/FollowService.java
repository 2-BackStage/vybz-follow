package com.vybz.follow_service.follow.application;

import com.vybz.follow_service.follow.dto.request.RequestAddFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestDeleteFollowDto;
import com.vybz.follow_service.follow.dto.response.ResponseFollowDto;

import java.util.List;

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
     * 사용자 팔로우 리스트 조회
     * @param userUuid
     */
    List<ResponseFollowDto> getFollowingByUserUuid(String userUuid);

    /**
     * 버스커 팔로워 리스트 조회
     * @param buskerUuid
     */
    List<ResponseFollowDto> getFollowerByBuskerUuid(String buskerUuid);

    /**
     * 팔로우 삭제
     * @param requestDeleteFollowDto
     */
    void deleteFollowing(RequestDeleteFollowDto requestDeleteFollowDto);

}
