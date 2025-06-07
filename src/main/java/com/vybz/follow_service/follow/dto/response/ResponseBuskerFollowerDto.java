package com.vybz.follow_service.follow.dto.response;

import com.vybz.follow_service.follow.domain.Follower;
import com.vybz.follow_service.follow.vo.response.ResponseBuskerFollowerVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBuskerFollowerDto {

    private String buskerUuid;
    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public ResponseBuskerFollowerDto(String buskerUuid, String userUuid, String nickname, String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static ResponseBuskerFollowerDto from(String buskerUuid, Follower follower) {
        return ResponseBuskerFollowerDto.builder()
                .buskerUuid(buskerUuid)
                .userUuid(follower.getUserUuid())
                .nickname(follower.getNickname())
                .profileImageUrl(follower.getProfileImageUrl())
                .build();
    }

    public ResponseBuskerFollowerVo toVo() {
        return ResponseBuskerFollowerVo.builder()
                .buskerUuid(buskerUuid)
                .userUuid(userUuid)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
