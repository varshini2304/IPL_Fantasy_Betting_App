package com.iplfantasy.service;

import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.TeamName;
import java.util.List;

public interface TeamService {

    Team createOrUpdate(String name, int points);

    Team createOrUpdate(TeamName teamName, int points);

    Team getById(Long id);

    Team getByName(String name);

    Team getByTeamName(TeamName teamName);

    List<Team> getAllTeams();

    List<Team> getAllTeamsOrderByPoints();

    java.util.List<com.iplfantasy.dto.TeamStatistics> getTeamStatistics();
}
