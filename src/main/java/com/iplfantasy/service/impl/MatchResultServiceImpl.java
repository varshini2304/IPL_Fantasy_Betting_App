package com.iplfantasy.service.impl;

import java.time.LocalDateTime;

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
import com.iplfantasy.service.MatchResultService;
import com.iplfantasy.service.PredictionService;

@Service
@Transactional
public class MatchResultServiceImpl implements MatchResultService {

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private MatchResultRepository resultRepo;

    @Autowired
    private PredictionService predictionService;

    @Override
    public void updateWinner(Long matchId, String winnerTeamName) {

        Match match = matchRepo.findById(matchId);
        Team winner = teamRepo.findByName(winnerTeamName);

        if (winner == null)
            throw new RuntimeException("Winner does not exist!");

        match.setWinnerTeam(winner);
        match.setMatchStatus(MatchStatus.COMPLETED);
        matchRepo.update(match);

        MatchResult result = MatchResult.builder()
                .match(match)
                .winnerTeam(winner)
                .updatedAt(LocalDateTime.now())
                .build();

        resultRepo.save(result);

        predictionService.awardPoints(match);

        winner.setCurrentPoints(winner.getCurrentPoints() + 2);
        teamRepo.update(winner);
    }
}
