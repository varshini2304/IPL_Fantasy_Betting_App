package com.iplfantasy.service;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchStatus;
import com.iplfantasy.entity.Team;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchService {

    Match create(Team t1, Team t2, LocalDateTime startTime);

    Match get(Long id);

    List<Match> getAll();

    List<Match> getUpcoming();

    List<Match> getCurrentMatches();

    void setToss(Long matchId, LocalDateTime tossTime);

    void setTossWinner(Long matchId, Team tossWinnerTeam);

    void setWinner(Long matchId, Team winnerTeam);

    void setMatchResult(Long matchId, Team winnerTeam, String manOfTheMatch, String topScorer,
            Integer winningTeamScore);

    void updateStatus(Long matchId, MatchStatus status);
}
