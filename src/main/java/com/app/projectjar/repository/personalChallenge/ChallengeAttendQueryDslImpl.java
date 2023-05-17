package com.app.projectjar.repository.personalChallenge;


import com.app.projectjar.entity.personalChallenge.ChallengeAttend;
import com.app.projectjar.entity.personalChallenge.QChallengeAttend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.app.projectjar.entity.groupChallenge.QGroupChallengeAttend.groupChallengeAttend;
import static com.app.projectjar.entity.personalChallenge.QChallengeAttend.challengeAttend;

@RequiredArgsConstructor
public class ChallengeAttendQueryDslImpl implements ChallengeAttendQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Integer getAttendCountByPersonalChallengeId_QueryDsl(Long personalChallengeId) {
        return query.select(challengeAttend.count())
                .from(challengeAttend)
                .where(challengeAttend.personalChallenge.id.eq(personalChallengeId))
                .fetchOne().intValue();
    }

    @Override
    public Long findByChallengeIdAndMemberId_QueryDsl(Long personalChallengeId, Long memberId) {
        return query.select(challengeAttend.count())
                .from(challengeAttend)
                .where(challengeAttend.personalChallenge.id.eq(personalChallengeId).and(challengeAttend.member.id.eq(memberId)))
                .fetchOne();
    }

    @Override
    public void deleteByPersonalChallengeIdAndMemberId_QueryDsl(Long personalChallengeId, Long memberId) {
        query.delete(challengeAttend)
                .where(challengeAttend.personalChallenge.id.eq(personalChallengeId).and(challengeAttend.member.id.eq(memberId)))
                .execute();
    }

    @Override
    public ChallengeAttend findPersonalChallengeAttendByPersonalChallengeIdAndMemberId_QueryDsl(Long personalChallengeId, Long memberId) {
        return query.select(challengeAttend)
                .from(challengeAttend)
                .where(challengeAttend.personalChallenge.id.eq(personalChallengeId).and(challengeAttend.member.id.eq(memberId)))
                .fetchOne();
    }

    //    개인 챌린지 목록(페이징, 진행중)
    @Override
    public Page<ChallengeAttend> findAllWithPageAndChallenges_QueryDsl(Long memberId, Pageable pageable) {
//        List<ChallengeAttend> foundChallengeAttend = query.selectFrom(challengeAttend)
//                .join(challengeAttend.member, member)
//                .leftJoin(challengeAttend.personalChallenge, personalChallenge)
//                .where(challengeAttend.member.id.eq(memberId))
//                .where(challenge.challengeStatus.eq(ChallengeType.valueOf("OPEN")))
//                .leftJoin(challenge.challengeFiles, QChallengeFile.challengeFile)
//                .orderBy(challengeAttend.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long count = query.select(challengeAttend.count())
//                .from(challengeAttend)
//                .join(challengeAttend.member, member)
//                .where(member.id.eq(memberId))
//                .fetchOne();
//
//        return new PageImpl<>(foundChallengeAttend, pageable, count);
        return null;
    }

    //    내가 완료한 개인 챌린지 전체 조회 (종료된 챌린지)
    @Override
    public Page<ChallengeAttend> findAllWithPageAndEndChallenges_QueryDsl(Long memberId, Pageable pageable) {
//        List<ChallengeAttend> foundChallengeAttend = query.selectFrom(challengeAttend)
//                .join(challengeAttend.member, member)
//                .leftJoin(challengeAttend.personalChallenge, personalChallenge)
//                .where(challengeAttend.member.id.eq(memberId))
//                .where(challenge.challengeStatus.eq(ChallengeType.valueOf("PRIVATE")))
//                .leftJoin(challenge.challengeFiles, QChallengeFile.challengeFile)
//                .orderBy(challengeAttend.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long count = query.select(challengeAttend.count())
//                .from(challengeAttend)
//                .join(challengeAttend.member, member)
//                .where(member.id.eq(memberId))
//                .fetchOne();

//        return new PageImpl<>(foundChallengeAttend, pageable, count);
        return null;
    }

    //    개인 챌린지 목록(댓글 갯수)
    @Override
    public Long getChallengeReplyCount_QueryDsl(Long challengeId) {
//        QChallengeReply challengeReply = QChallengeReply.challengeReply;
//
//        return query.select(challengeReply.count())
//                .from(challengeReply)
//                .where(challengeReply.personalChallenge.id.eq(challengeId))
//                .fetchOne();
        return null;
    }
}