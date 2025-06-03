package com.vybz.follow_service.follow.vo.request;

import com.vybz.follow_service.follow.domain.Follower;
import com.vybz.follow_service.follow.domain.Following;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestAddFollowVo {

    private List<Follower> follower;
    private List<Following> following;

}
