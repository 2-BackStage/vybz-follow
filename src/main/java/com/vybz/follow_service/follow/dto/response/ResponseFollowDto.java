package com.vybz.follow_service.follow.dto.response;

import com.vybz.follow_service.follow.domain.Follow;
import com.vybz.follow_service.follow.domain.Follower;
import com.vybz.follow_service.follow.domain.Following;
import com.vybz.follow_service.follow.vo.response.ResponseBuskerFollowerVo;
import com.vybz.follow_service.follow.vo.response.ResponseUserFollowingVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseFollowDto {

    private List<Follower> follower;
    private List<Following> following;

    @Builder
    public ResponseFollowDto(List<Follower> follower, List<Following> following) {
        this.follower = follower;
        this.following = following;
    }

    public static ResponseFollowDto from(Follow follow) {
        return ResponseFollowDto.builder()
                .follower(follow.getFollower())
                .following(follow.getFollowing())
                .build();
    }

    public ResponseUserFollowingVo toUserVo() {
        return ResponseUserFollowingVo.builder()
                .userUuid(follower.get(0).getUserUuid())
                .following(following)
                .build();
    }

    public ResponseBuskerFollowerVo toBuskerVo() {
        return ResponseBuskerFollowerVo.builder()
                .buskerUuid(following.get(0).getBuskerUuid())
                .follower(follower)
                .build();
    }

}
