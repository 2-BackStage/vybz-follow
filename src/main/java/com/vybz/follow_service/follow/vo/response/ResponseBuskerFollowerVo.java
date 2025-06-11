package com.vybz.follow_service.follow.vo.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBuskerFollowerVo {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public ResponseBuskerFollowerVo(String userUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
