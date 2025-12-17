package com.iplfantasy.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchResult;
import com.iplfantasy.entity.MatchStatus;
import com.iplfantasy.entity.Team;
import com.iplfantasy.repository.MatchRepository;
import com.iplfantasy.repository.MatchResultRepository;
import com.iplfantasy.repository.TeamRepository;
import com.iplfantasy.service.MatchService;
import com.iplfantasy.service.PredictionService;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private MatchResultRepository resultRepo;

    @Autowired
    private PredictionService predictionService;

    @Override
    public Match create(Team t1, Team t2, LocalDateTime startTime) {

        Match match = Match.builder()
                .team1(t1)
                .team2(t2)
                .matchStartTime(startTime)
                .matchStatus(MatchStatus.UPCOMING)
                .build();

        matchRepo.save(match);
        return match;
    }

    @Override
    public Match get(Long id) {
        Match m = matchRepo.findById(id);
        if (m == null)
            throw new EntityNotFoundException("Match not found: " + id);
        return m;
    }

    @Override
    public List<Match> getAll() {
        return matchRepo.findAll();
    }

    @Override
    public List<Match> getUpcoming() {
        return matchRepo.findUpcoming();
    }

    @Override
    public List<Match> getCurrentMatches() {
        return matchRepo.findCurrentMatches();
    }

    @Override
    public void setToss(Long matchId, LocalDateTime tossTime) {
        Match match = get(matchId);
        match.setTossTime(tossTime);
        matchRepo.update(match);
        predictionService.lockTossPredictions(match);
    }

    @Override
    public void setTossWinner(Long matchId, Team tossWinnerTeam) {
        Match match = get(matchId);
        match.setTossWinnerTeam(tossWinnerTeam);
        matchRepo.update(match);

        MatchResult existingResult = resultRepo.findByMatch(match);
        if (existingResult != null) {
            existingResult.setTossWinner(tossWinnerTeam);
            existingResult.setUpdatedAt(LocalDateTime.now());
            resultRepo.update(existingResult);
        }

        predictionService.lockTossPredictions(match);
    }

    @Override
    public void setWinner(Long matchId, Team winnerTeam) {
        setMatchResult(matchId, winnerTeam, null, null, null);
    }

    @Override
    public void setMatchResult(Long matchId, Team winnerTeam, String manOfTheMatch, String topScorer,
            Integer winningTeamScore) {
        setMatchResult(matchId, winnerTeam, manOfTheMatch, topScorer, winningTeamScore, false);
    }

    @Override
    public void setMatchResult(Long matchId, Team winnerTeam, String manOfTheMatch, String topScorer,
            Integer winningTeamScore, Boolean isDraw) {

        Match match = get(matchId);

        if (isDraw != null && isDraw) {
            match.setWinnerTeam(null);
        } else {
            match.setWinnerTeam(winnerTeam);
        }
        match.setMatchStatus(MatchStatus.COMPLETED);
        matchRepo.update(match);

        MatchResult existingResult = resultRepo.findByMatch(match);
        MatchResult result;

        if (existingResult != null) {
            result = existingResult;
            if (isDraw != null && isDraw) {
                result.setWinnerTeam(null);
                result.setIsDraw(true);
            } else {
                result.setWinnerTeam(winnerTeam);
                result.setIsDraw(false);
            }
            result.setManOfTheMatch(manOfTheMatch);
            result.setTopScorer(topScorer);
            result.setWinningTeamScore(winningTeamScore);
            result.setUpdatedAt(LocalDateTime.now());
            resultRepo.update(result);
        } else {
            Team tossWinner = match.getTossWinnerTeam();
            result = MatchResult.builder()
                    .match(match)
                    .winnerTeam(isDraw != null && isDraw ? null : winnerTeam)
                    .tossWinner(tossWinner)
                    .manOfTheMatch(manOfTheMatch)
                    .topScorer(topScorer)
                    .winningTeamScore(winningTeamScore)
                    .isDraw(isDraw != null ? isDraw : false)
                    .updatedAt(LocalDateTime.now())
                    .build();
            resultRepo.save(result);
        }

        predictionService.awardPoints(match);

        if (!(isDraw != null && isDraw) && winnerTeam != null) {
            winnerTeam.setCurrentPoints(winnerTeam.getCurrentPoints() + 2);
            teamRepo.update(winnerTeam);
        }
    }

    @Override
    public void updateStatus(Long matchId, MatchStatus status) {
        Match match = get(matchId);
        match.setMatchStatus(status);
        matchRepo.update(match);
    }
}
