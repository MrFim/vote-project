package com.ua.core;

import com.ua.core.config.VoteConfig;
import com.ua.core.domain.Vote;
import com.ua.core.service.VoteService;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        VoteConfig config = new VoteConfig();
        VoteService voteService = new VoteService(config, Clock.systemDefaultZone());

        Set<String> existingVoters = new HashSet<>();
        LocalDate recordDate = LocalDate.of(2025, 12, 31);

        Vote vote = new Vote("SHAREHOLDER_ID_1", "MEETINGS_ID_1", "PROPOSAL_ID_21");

        boolean accepted = voteService.processVote(vote, existingVoters, recordDate);
        System.out.println("Accepted: " + accepted);
    }
}