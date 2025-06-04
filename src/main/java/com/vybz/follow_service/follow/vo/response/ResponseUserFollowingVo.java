package com.vybz.follow_service.follow.vo.response;

import com.vybz.follow_service.follow.domain.Following;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseUserFollowingVo {

    private String userUuid;
    private List<Following> following;

    @Builder
    public ResponseUserFollowingVo(String userUuid, List<Following> following) {
        this.userUuid = userUuid;
        this.following = following;
    }

}
