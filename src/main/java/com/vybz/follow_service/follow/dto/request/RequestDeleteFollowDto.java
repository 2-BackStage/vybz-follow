package com.vybz.follow_service.follow.dto.request;

import com.vybz.follow_service.follow.vo.request.RequestDeleteFollowVo;
import com.vybz.follow_service.kafka.event.UnfollowEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDeleteFollowDto {

    private String userUuid;
    private String buskerUuid;

    @Builder
    public RequestDeleteFollowDto(String userUuid, String buskerUuid) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
    }

    public static RequestDeleteFollowDto from(RequestDeleteFollowVo requestDeleteFollowVo) {
        return RequestDeleteFollowDto.builder()
                .userUuid(requestDeleteFollowVo.getUserUuid())
                .buskerUuid(requestDeleteFollowVo.getBuskerUuid())
                .build();
    }

    public static UnfollowEvent toUnfollowEvent(String userUuid, String buskerUuid) {
        return UnfollowEvent.builder()
                .userUuid(userUuid)
                .buskerUuid(buskerUuid)
                .build();
    }

}
