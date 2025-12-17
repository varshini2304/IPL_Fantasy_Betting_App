package com.iplfantasy.service.impl;

import com.iplfantasy.dto.TeamStatistics;
import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchResult;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.TeamName;
import com.iplfantasy.repository.MatchRepository;
import com.iplfantasy.repository.MatchResultRepository;
import com.iplfantasy.repository.TeamRepository;
import com.iplfantasy.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository repo;

    @Autowired
    private MatchRepository matchRepo;

    @Autowired
    private MatchResultRepository matchResultRepo;

    @Override
    public Team createOrUpdate(String name, int points) {
        TeamName teamName = TeamName.fromString(name);
        if (teamName == null) {
            throw new IllegalArgumentException("Invalid team name: " + name);
        }
        return createOrUpdate(teamName, points);
    }

    @Override
    public Team createOrUpdate(TeamName teamName, int points) {

        Team t = repo.findByTeamName(teamName);

        if (t == null) {
            t = Team.builder()
                    .teamName(teamName)
                    .currentPoints(points)
                    .build();
            repo.save(t);
        } else {
            t.setCurrentPoints(points);
            repo.update(t);
        }

        return t;
    }

    @Override
    public Team getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Team getByName(String name) {
        TeamName teamName = TeamName.fromString(name);
        if (teamName == null) {
            return null;
        }
        return repo.findByTeamName(teamName);
    }

    @Override
    public Team getByTeamName(TeamName teamName) {
        return repo.findByTeamName(teamName);
    }

    @Override
    public List<Team> getAllTeams() {
        return repo.findAll();
    }

    @Override
    public List<Team> getAllTeamsOrderByPoints() {
        return repo.findAllOrderByPointsDesc();
    }

    @Override
    public List<TeamStatistics> getTeamStatistics() {
        List<Team> teams = repo.findAllOrderByPointsDesc();
        List<TeamStatistics> statistics = new ArrayList<>();

        for (Team team : teams) {
            List<Match> completedMatches = matchRepo.findCompletedMatchesByTeam(team);
            
            int matchesPlayed = completedMatches.size();
            int matchesWon = 0;
            int matchesDrawn = 0;
            int matchesLost = 0;

            for (Match match : completedMatches) {
                MatchResult result = matchResultRepo.findByMatch(match);
                
                if (result != null && result.getIsDraw() != null && result.getIsDraw()) {
                    matchesDrawn++;
                } else if (match.getWinnerTeam() != null && match.getWinnerTeam().getTeamId().equals(team.getTeamId())) {
                    matchesWon++;
                } else {
                    matchesLost++;
                }
            }

            TeamStatistics stats = TeamStatistics.builder()
                    .team(team)
                    .matchesPlayed(matchesPlayed)
                    .matchesWon(matchesWon)
                    .matchesLost(matchesLost)
                    .matchesDrawn(matchesDrawn)
                    .build();

            statistics.add(stats);
        }

        return statistics;
    }
}
