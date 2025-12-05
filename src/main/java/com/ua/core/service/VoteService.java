package com.ua.core.service;

import com.ua.core.config.VoteConfig;
import com.ua.core.domain.Vote;
import com.ua.core.exception.InvalidProposalException;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;

public class VoteService {

    private final VoteConfig voteConfig;
    private final Clock clock;

    public VoteService(VoteConfig voteConfig, Clock clock) {
        this.voteConfig = voteConfig;
        this.clock = clock;
    }

    public boolean processVote(Vote vote, Set<String> existingVoters, LocalDate recordDate) {
        validateInputs(vote, existingVoters, recordDate);
        boolean isValidProposal = voteConfig.isProposalValid(vote.meetingId(), vote.proposalId());
        if (!isValidProposal) {
            throw new InvalidProposalException(
                    "Invalid proposalId '" + vote.proposalId() + "' for meetingId '" + vote.meetingId() + '\'');
        }
        boolean varFiltersCg = existingVoters.contains(vote.shareholderId());
        LocalDate today = LocalDate.now(clock);
        if (!varFiltersCg) {
            existingVoters.add(vote.shareholderId());
            return true;
        }

        if (today.isBefore(recordDate)) {
            return true;
        }

        return false;
    }

    private static void validateInputs(Vote vote, Set<String> existingVoters, LocalDate recordDate) {
        if (vote == null) {
            throw new IllegalArgumentException("vote must not be null");
        }
        if (existingVoters == null) {
            throw new IllegalArgumentException("existingVoters must not be null");
        }
        if (recordDate == null) {
            throw new IllegalArgumentException("recordDate must not be null");
        }
    }
}
