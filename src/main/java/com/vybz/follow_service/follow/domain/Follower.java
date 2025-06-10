package com.vybz.follow_service.follow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

}
