package com.ua.core.service;

import com.ua.core.config.VoteConfig;
import com.ua.core.domain.Vote;
import com.ua.core.exception.InvalidProposalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VoteServiceTest {

    private VoteService voteService;
    private Set<String> existingVoters;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(Instant.parse("2025-12-20T00:00:00Z"), ZoneId.of("UTC"));

        VoteConfig voteConfig = new VoteConfig();
        voteService = new VoteService(voteConfig, fixedClock);

        existingVoters = new HashSet<>();
    }

    @Test
    void newVote_shouldBeAccepted() {
        LocalDate recordDate = LocalDate.of(2025, 12, 31);
        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_10");

        boolean result = voteService.processVote(vote, existingVoters, recordDate);

        assertTrue(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_1"));
    }

    @Test
    void changeVoteBeforeRecordDate_shouldBeAccepted() {
        existingVoters.add("SHAREHOLDER_1");

        LocalDate recordDate = LocalDate.of(2025, 12, 31);
        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_21");

        boolean result = voteService.processVote(vote, existingVoters, recordDate);

        assertTrue(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_1"));
    }

    @Test
    void changeVoteOnRecordDate_shouldBeRejected() {
        existingVoters.add("SHAREHOLDER_1");

        LocalDate recordDate = LocalDate.of(2025, 12, 20);
        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_37");

        boolean result = voteService.processVote(vote, existingVoters, recordDate);

        assertFalse(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_1"));
    }

    @Test
    void changeVoteAfterRecordDate_shouldBeRejected() {
        existingVoters.add("SHAREHOLDER_1");

        LocalDate recordDate = LocalDate.of(2025, 12, 1);
        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_37");

        boolean result = voteService.processVote(vote, existingVoters, recordDate);

        assertFalse(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_1"));
    }

    @Test
    void invalidProposal_shouldThrowException() {
        LocalDate recordDate = LocalDate.of(2025, 12, 31);

        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_999");

        assertThrows(InvalidProposalException.class, () -> voteService.processVote(vote, existingVoters, recordDate));
    }

    @Test
    void nullArguments_shouldThrowIllegalArgumentException() {
        LocalDate recordDate = LocalDate.of(2025, 12, 31);
        Vote vote = new Vote("SHAREHOLDER_1", "MEETINGS_ID_1", "PROPOSAL_ID_10");

        assertThrows(IllegalArgumentException.class, () -> voteService.processVote(null, existingVoters, recordDate));
        assertThrows(IllegalArgumentException.class, () -> voteService.processVote(vote, null, recordDate));
        assertThrows(IllegalArgumentException.class, () -> voteService.processVote(vote, existingVoters, null));
    }
}