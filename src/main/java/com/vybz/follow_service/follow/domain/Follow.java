package com.vybz.follow_service.follow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@Document("follow")
public class Follow {

    @Id
    private String id;

    /**
     * 팔로워 (유저)
     */
    @Field(name = "follower")
    private List<Follower> follower;

    /**
     * 팔로잉 (버스커)
     */
    @Field(name = "following")
    private List<Following> following;

    @CreatedDate
    @Field(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Instant updatedAt;

    @Builder
    public Follow(String id, List<Follower> follower, List<Following> following) {
        this.id = id;
        this.follower = follower;
        this.following = following;
    }

}
