package com.iplfantasy.service;

import com.iplfantasy.entity.Player;
import com.iplfantasy.entity.Team;

import java.util.List;

public interface PlayerService {

    Player createOrUpdate(String playerName, Team team);

    List<Player> getPlayersByTeam(Team team);

    List<Player> getPlayersByTeams(List<Team> teams);
}
