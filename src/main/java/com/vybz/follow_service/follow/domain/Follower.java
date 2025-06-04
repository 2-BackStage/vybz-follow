package com.vybz.follow_service.follow.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Follower {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

}
