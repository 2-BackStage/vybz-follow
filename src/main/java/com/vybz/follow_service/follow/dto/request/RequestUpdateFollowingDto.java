package com.vybz.follow_service.follow.dto.request;

import com.vybz.follow_service.follow.vo.request.RequestUpdateFollowingVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateFollowingDto {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public RequestUpdateFollowingDto(String buskerUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static RequestUpdateFollowingDto from(RequestUpdateFollowingVo requestUpdateFollowingVo) {
        return RequestUpdateFollowingDto.builder()
                .buskerUuid(requestUpdateFollowingVo.getBuskerUuid())
                .nickname(requestUpdateFollowingVo.getNickname())
                .profileImageUrl(requestUpdateFollowingVo.getProfileImageUrl())
                .build();
    }

}
