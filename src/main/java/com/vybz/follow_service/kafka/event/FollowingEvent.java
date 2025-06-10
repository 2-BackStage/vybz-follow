package com.vybz.follow_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingEvent {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public FollowingEvent(String buskerUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
