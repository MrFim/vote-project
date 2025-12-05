package com.ua.core.config;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class VoteConfig {
    private final Map<String, Set<String>> proposalsByMeeting;

    public VoteConfig(Map<String, Set<String>> proposalsByMeeting) {
        this.proposalsByMeeting = Objects.requireNonNullElseGet(proposalsByMeeting, VoteConfig::initializeMap);
    }

    public VoteConfig() {
        this.proposalsByMeeting = initializeMap();
    }

    public boolean isProposalValid(String meetingId, String proposalId) {
        return proposalsByMeeting
                .getOrDefault(meetingId, Set.of())
                .contains(proposalId);
    }

    private static Map<String, Set<String>> initializeMap() {
        return Map.of(
                "MEETINGS_ID_1", Set.of("PROPOSAL_ID_10", "PROPOSAL_ID_21", "PROPOSAL_ID_37"),
                "MEETINGS_ID_2", Set.of("PROPOSAL_ID_3", "PROPOSAL_ID_44"),
                "MEETINGS_ID_7", Set.of("PROPOSAL_ID_5", "PROPOSAL_ID_18", "PROPOSAL_ID_90"),
                "MEETINGS_ID_12", Set.of("PROPOSAL_ID_1"),
                "MEETINGS_ID_25", Set.of("PROPOSAL_ID_2", "PROPOSAL_ID_8"),
                "MEETINGS_ID_101", Set.of("PROPOSAL_ID_7", "PROPOSAL_ID_19", "PROPOSAL_ID_55"),
                "MEETINGS_ID_204", Set.of("PROPOSAL_ID_6"),
                "MEETINGS_ID_350", Set.of("PROPOSAL_ID_11", "PROPOSAL_ID_22"),
                "MEETINGS_ID_999", Set.of("PROPOSAL_ID_4", "PROPOSAL_ID_14", "PROPOSAL_ID_77"),
                "MEETINGS_ID_2025", Set.of("PROPOSAL_ID_9", "PROPOSAL_ID_33")
        );
    }

    public Map<String, Set<String>> getProposalsByMeeting() {
        return proposalsByMeeting;
    }
}
