package com.vybz.follow_service.follow.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
public class Following {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public Following(String buskerUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
