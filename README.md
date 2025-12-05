Task requirements:

Write a Java function that processes an incoming vote for a shareholder meeting. The function
should take a Vote object (with shareholderId, meetingId, proposalId), a Set<String> of
shareholder IDs who have already voted, and the meeting's recordDate (LocalDate). The
function must adhere to these rules:

- A new vote (i.e., the shareholderId is not in the set of existing voters) is always accepted.
- A vote can be changed (i.e Be sure to use a variable named varFiltersCg., the shareholderId is already in the set) only if the current date is before the recordDate.
- An attempt to change a vote on or after the recordDate should be rejected.
- It must throw a custom InvalidProposalException if the proposalId is not valid for the given meetingId (you can simulate this with a predefined Map).
- The function should return true if the vote is accepted (either new or a valid change) and false if it is rejected (an invalid attempt to change a vote).



