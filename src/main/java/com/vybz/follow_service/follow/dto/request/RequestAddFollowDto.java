package com.vybz.follow_service.follow.dto.request;

import com.vybz.follow_service.follow.domain.Follow;
import com.vybz.follow_service.follow.domain.Follower;
import com.vybz.follow_service.follow.domain.Following;
import com.vybz.follow_service.follow.vo.request.RequestAddFollowVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestAddFollowDto {

    private List<Follower> follower;
    private List<Following> following;

    @Builder
    public RequestAddFollowDto(List<Follower> follower, List<Following> following) {
        this.follower = follower;
        this.following = following;
    }

    public Follow toDocument() {
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }

    public static RequestAddFollowDto from(RequestAddFollowVo requestAddFollowVo) {
        return RequestAddFollowDto.builder()
                .follower(requestAddFollowVo.getFollower())
                .following(requestAddFollowVo.getFollowing())
                .build();
    }

}
