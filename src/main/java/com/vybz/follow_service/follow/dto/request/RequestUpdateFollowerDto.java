package com.vybz.follow_service.follow.dto.request;

import com.vybz.follow_service.follow.vo.request.RequestUpdateFollowerVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateFollowerDto {

    private String userUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public RequestUpdateFollowerDto(String userUuid, String nickname, String profileImageUrl) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static RequestUpdateFollowerDto from(RequestUpdateFollowerVo requestUpdateFollowerVo) {
        return RequestUpdateFollowerDto.builder()
                .userUuid(requestUpdateFollowerVo.getUserUuid())
                .nickname(requestUpdateFollowerVo.getNickname())
                .profileImageUrl(requestUpdateFollowerVo.getProfileImageUrl())
                .build();
    }

}
