package com.vybz.follow_service.follow.infrastructure;

import com.vybz.follow_service.common.util.MongoCursorPageHelper;
import com.vybz.follow_service.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    /**
     * 사용자 UUID를 기준으로 커서 기반 팔로잉 목록 조회
     *
     * @param userUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    @Override
    public List<Follow> findFollowingByCursor(String userUuid, String lastId, Integer pageSize, Integer page) {
        Query baseQuery = new Query();
        baseQuery.addCriteria(Criteria.where("follower.userUuid").is(userUuid));

        Query finalQuery = MongoCursorPageHelper.build(baseQuery, lastId, pageSize, page);

        log.info("Follow 페이징 조회 - userUuid={}, page={}, pageSize={}, lastId={}", userUuid, page, pageSize, lastId);

        return mongoTemplate.find(finalQuery, Follow.class);
    }

    @Override
    public List<Follow> findFollowerByCursor(String buskerUuid, String lastId, Integer pageSize, Integer page) {
        Query baseQuery = new Query();
        baseQuery.addCriteria(Criteria.where("following.buskerUuid").is(buskerUuid));

        Query finalQuery = MongoCursorPageHelper.build(baseQuery, lastId, pageSize, page);

        log.info("Follower 페이징 조회 - buskerUuid={}, page={}, pageSize={}, lastId={}", buskerUuid, page, pageSize, lastId);

        return mongoTemplate.find(finalQuery, Follow.class);
    }
}
