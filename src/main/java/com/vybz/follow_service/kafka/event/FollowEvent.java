package com.vybz.follow_service.kafka.event;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowEvent {

    private String userUuid;
    private String buskerUuid;

    @Builder
    public FollowEvent(String userUuid, String buskerUuid) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
    }

}
