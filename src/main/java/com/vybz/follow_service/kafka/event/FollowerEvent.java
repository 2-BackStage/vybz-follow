package com.vybz.follow_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowerEvent {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public FollowerEvent(String userUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
