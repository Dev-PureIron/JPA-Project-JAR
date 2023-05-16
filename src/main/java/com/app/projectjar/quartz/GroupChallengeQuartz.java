package com.app.projectjar.quartz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GroupChallengeQuartz {

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateGroupChallengeBoardType() {

    }
}
