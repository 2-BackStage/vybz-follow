package com.vybz.follow_service.follow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public Follower(String userUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
