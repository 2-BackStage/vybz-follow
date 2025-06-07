package com.vybz.follow_service.follow.vo.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBuskerFollowerVo {

    private String buskerUuid;
    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public ResponseBuskerFollowerVo(String buskerUuid, String userUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
