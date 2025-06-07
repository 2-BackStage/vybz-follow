package com.vybz.follow_service.follow.dto.response;

import com.vybz.follow_service.follow.domain.Following;
import com.vybz.follow_service.follow.vo.response.ResponseUserFollowingVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUserFollowingDto {

    private String userUuid;
    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public ResponseUserFollowingDto(String userUuid, String buskerUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static ResponseUserFollowingDto from(String userUuid, Following following) {
        return ResponseUserFollowingDto.builder()
                .userUuid(userUuid)
                .buskerUuid(following.getBuskerUuid())
                .nickname(following.getNickname())
                .profileImageUrl(following.getProfileImageUrl())
                .build();
    }

    public ResponseUserFollowingVo toVo() {
        return ResponseUserFollowingVo.builder()
                .userUuid(userUuid)
                .buskerUuid(buskerUuid)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
