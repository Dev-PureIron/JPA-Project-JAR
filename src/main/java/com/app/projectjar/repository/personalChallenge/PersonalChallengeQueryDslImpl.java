package com.app.projectjar.repository.personalChallenge;

import com.app.projectjar.entity.personalChallenge.PersonalChallenge;
import com.app.projectjar.type.ChallengeType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.projectjar.entity.challenge.QChallenge.challenge;
import static com.app.projectjar.entity.personalChallenge.QPersonalChallenge.personalChallenge;

@RequiredArgsConstructor
public class PersonalChallengeQueryDslImpl implements PersonalChallengeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<PersonalChallenge> findAllByChallengeStatus(String challengeStatus, Pageable pageable) {
        List<PersonalChallenge> personalChallengeList = query.select(personalChallenge)
                .from(personalChallenge)
                .leftJoin(personalChallenge.challenge, challenge)
                .fetchJoin()
                .where(challengeStatus.equals("OPEN") ? personalChallenge.challengeStatus.eq(ChallengeType.OPEN) : personalChallenge.challengeStatus.eq(ChallengeType.PRIVATE))
                .orderBy(personalChallenge.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(personalChallenge.count())
                            .from(personalChallenge)
                            .where(challengeStatus.equals("OPEN") ? personalChallenge.challengeStatus.eq(ChallengeType.OPEN) : personalChallenge.challengeStatus.eq(ChallengeType.PRIVATE))
                            .fetchOne();

        return new PageImpl<>(personalChallengeList, pageable, count);
    }

    @Override
    public List<PersonalChallenge> findByCreateDateYesterday(LocalDateTime localDateTime) {
        List<PersonalChallenge> personalChallengeList = query.select(personalChallenge)
                .from(personalChallenge)
                .leftJoin(personalChallenge.challenge, challenge)
                .fetchJoin()
                .where(personalChallenge.createdDate.eq(localDateTime))
                .fetch();

        return personalChallengeList;
    }
}
