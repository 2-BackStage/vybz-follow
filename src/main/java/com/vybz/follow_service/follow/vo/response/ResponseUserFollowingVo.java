package com.vybz.follow_service.follow.vo.response;

import com.vybz.follow_service.follow.domain.Following;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseUserFollowingVo {

    private String userUuid;
    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public ResponseUserFollowingVo(String userUuid, String buskerUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
