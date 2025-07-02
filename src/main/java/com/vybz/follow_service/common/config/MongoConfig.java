package com.vybz.follow_service.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void createIndexes() {
        try {
            IndexOperations indexOps = mongoTemplate.indexOps("follow");

            // 팔로워 userUuid 인덱스 (배열 내 객체 필드)
            indexOps.ensureIndex(new Index("follower.userUuid", Sort.Direction.ASC)
                .named("idx_follower_user_uuid"));

            // 팔로잉 buskerUuid 인덱스 (배열 내 객체 필드)
            indexOps.ensureIndex(new Index("following.buskerUuid", Sort.Direction.ASC)
                .named("idx_following_busker_uuid"));

            // 팔로워 userUuid + created_at 복합 인덱스
            indexOps.ensureIndex(new Index("follower.userUuid", Sort.Direction.ASC)
                .on("created_at", Sort.Direction.ASC)
                .named("idx_follower_user_uuid_created_at"));

            // 팔로잉 buskerUuid + created_at 복합 인덱스
            indexOps.ensureIndex(new Index("following.buskerUuid", Sort.Direction.ASC)
                .on("created_at", Sort.Direction.ASC)
                .named("idx_following_busker_uuid_created_at"));

            System.out.println("✅ MongoDB 인덱스 생성 완료");
        } catch (Exception e) {
            System.err.println("❌ MongoDB 인덱스 생성 실패: " + e.getMessage());
        }
    }
}
