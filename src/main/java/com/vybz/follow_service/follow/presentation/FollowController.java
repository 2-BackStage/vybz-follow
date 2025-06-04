package com.vybz.follow_service.follow.presentation;

import com.vybz.follow_service.common.entity.BaseResponseEntity;
import com.vybz.follow_service.common.entity.BaseResponseStatus;
import com.vybz.follow_service.follow.application.FollowService;
import com.vybz.follow_service.follow.dto.request.RequestAddFollowDto;
import com.vybz.follow_service.follow.dto.request.RequestDeleteFollowDto;
import com.vybz.follow_service.follow.dto.response.ResponseFollowDto;
import com.vybz.follow_service.follow.vo.request.RequestAddFollowVo;
import com.vybz.follow_service.follow.vo.request.RequestDeleteFollowVo;
import com.vybz.follow_service.follow.vo.response.ResponseBuskerFollowerVo;
import com.vybz.follow_service.follow.vo.response.ResponseUserFollowingVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    /**
     * 팔로우 추가
     * @param requestAddFollowVo
     */
    @Operation(summary = "팔로우 추가 API", description = "팔로우 추가 API 입니다.", tags = {"Follow-Service"})
    @PostMapping
    public BaseResponseEntity<Void> createFollow(@RequestBody RequestAddFollowVo requestAddFollowVo) {
        followService.createFollow(RequestAddFollowDto.from(requestAddFollowVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    /**
     * 사용자 팔로우 여부 확인
     *
     */
    @Operation(summary = "사용자 팔로우 여부 확인 API", description = "사용자 팔로우 여부 확인 API 입니다.", tags = {"Follow-Service"})
    @GetMapping("/check")
    public BaseResponseEntity<Boolean> checkUserFollow(@RequestParam String userUuid, String buskerUuid) {
        boolean isFollowing = followService.checkFollow(userUuid, buskerUuid);
        return new BaseResponseEntity<>(isFollowing);
    }

    /**
     * 사용자 팔로잉 리스트 조회
     * @param userUuid
     */
    @Operation(summary = "사용자 UUID로 팔로잉 조회 API", description = "사용자 UUID로 팔로잉 조회 API 입니다.", tags = {"Follow-Service"})
    @GetMapping("/following-list/{userUuid}")
    public BaseResponseEntity<List<ResponseUserFollowingVo>> getUserFollowing(@PathVariable("userUuid") String userUuid) {
        List<ResponseUserFollowingVo> responseUserFollowingVo = followService.getFollowingByUserUuid(userUuid)
                .stream()
                .map(ResponseFollowDto::toUserVo)
                .toList();
        return new BaseResponseEntity<>(responseUserFollowingVo);
    }

    /**
     * 버스커 팔로워 리스트 조회
     * @param buskerUuid
     */
    @Operation(summary = "버스커 UUID로 팔로워 조회 API", description = "버스커 UUID로 팔로워 조회 API 입니다.", tags = {"Follow-Service"})
    @GetMapping("follower-list/{buskerUuid}")
    public BaseResponseEntity<List<ResponseBuskerFollowerVo>> getBuskerFollower(@PathVariable("buskerUuid") String buskerUuid) {
        List<ResponseBuskerFollowerVo> responseBuskerFollowerVo = followService.getFollowerByBuskerUuid(buskerUuid)
                .stream()
                .map(ResponseFollowDto::toBuskerVo)
                .toList();
        return new BaseResponseEntity<>(responseBuskerFollowerVo);
    }

    /**
     * 사용자 팔로잉 삭제
     * @param requestDeleteFollowVo
     */
    @Operation(summary = "팔로잉 삭제 API", description = "사용자 UUID, 버스커 UUID로 팔로잉 삭제 API 입니다.", tags = {"Follow-Service"})
    @DeleteMapping
    public BaseResponseEntity<Void> deleteUserFollowing(@RequestBody RequestDeleteFollowVo requestDeleteFollowVo) {
        followService.deleteFollowing(RequestDeleteFollowDto.from(requestDeleteFollowVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
