package com.vybz.follow_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnfollowEvent {

    private String userUuid;
    private String buskerUuid;

    @Builder
    public UnfollowEvent(String userUuid, String buskerUuid) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
    }

}
