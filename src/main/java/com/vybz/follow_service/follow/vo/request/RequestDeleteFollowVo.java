package com.vybz.follow_service.follow.vo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDeleteFollowVo {

    private String userUuid;
    private String buskerUuid;

}
