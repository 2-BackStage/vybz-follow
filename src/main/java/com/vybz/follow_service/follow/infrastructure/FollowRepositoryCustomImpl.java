package com.vybz.follow_service.follow.infrastructure;

import com.vybz.follow_service.common.util.MongoCursorPageHelper;
import com.vybz.follow_service.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

    /**
     * 버스커 UUID를 기준으로 커서 기반 팔로워 목록 조회
     * @param buskerUuid
     * @param lastId
     * @param pageSize
     * @param page
     */
    @Override
    public List<Follow> findFollowerByCursor(String buskerUuid, String lastId, Integer pageSize, Integer page) {
        Query baseQuery = new Query();
        baseQuery.addCriteria(Criteria.where("following.buskerUuid").is(buskerUuid));

        Query finalQuery = MongoCursorPageHelper.build(baseQuery, lastId, pageSize, page);

        log.info("Follower 페이징 조회 - buskerUuid={}, page={}, pageSize={}, lastId={}", buskerUuid, page, pageSize, lastId);

        return mongoTemplate.find(finalQuery, Follow.class);
    }

    /**
     * 유저 uuid 기준으로 관계되는 팔로워 정보 업데이트
     * @param userUuid
     * @param profileImageUrl
     * @param nickname
     */
    @Override
    public void updateFollower(String userUuid, String profileImageUrl, String nickname) {
        Query query = Query.query(Criteria.where("follower.userUuid").is(userUuid));
        Update update = new Update()
                .set("follower.$[elem].nickname", nickname)
                .set("follower.$[elem].profileImageUrl", profileImageUrl)
                .filterArray(Criteria.where("elem.userUuid").is(userUuid));
        mongoTemplate.updateMulti(query, update, Follow.class);
    }

    /**
     * 버스커 uuid 기준으로 관계되는 팔로잉 정보 업데이트
     * @param buskerUuid
     * @param profileImageUrl
     * @param nickname
     */
    @Override
    public void updateFollowing(String buskerUuid, String profileImageUrl, String nickname) {
        Query query = Query.query(Criteria.where("following.buskerUuid").is(buskerUuid));
        Update update = new Update()
                .set("following.$[elem].nickname", nickname)
                .set("following.$[elem].profileImageUrl", profileImageUrl)
                .filterArray(Criteria.where("elem.buskerUuid").is(buskerUuid));
        mongoTemplate.updateMulti(query, update, Follow.class);
    }

    /**
     * 사용자 UUID를 기준으로 팔로워 관계 삭제
     * @param userUuid
     */
    @Override
    public void deleteFollowerRelationsByUserUuid(String userUuid) {
        Query query = Query.query(Criteria.where("follower.userUuid").is(userUuid));
        mongoTemplate.remove(query, Follow.class);
    }

    /**
     * 버스커 UUID를 기준으로 팔로잉 관계 삭제
     * @param buskerUuid
     */
    @Override
    public void deleteFollowingRelationsByBuskerUuid(String buskerUuid) {
        Query query = Query.query(Criteria.where("following.buskerUuid").is(buskerUuid));
        mongoTemplate.remove(query, Follow.class);
    }
}
