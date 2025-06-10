package com.vybz.follow_service.follow.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateFollowingVo {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public RequestUpdateFollowingVo(String buskerUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
