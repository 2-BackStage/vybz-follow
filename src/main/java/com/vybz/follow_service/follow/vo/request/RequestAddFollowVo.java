package com.vybz.follow_service.follow.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vybz.follow_service.follow.domain.Follower;
import com.vybz.follow_service.follow.domain.Following;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestAddFollowVo {

    private List<Follower> follower;
    private List<Following> following;

}
