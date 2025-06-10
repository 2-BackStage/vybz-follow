package com.vybz.follow_service.follow.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateFollowerVo {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public RequestUpdateFollowerVo(String userUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
