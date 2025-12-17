package com.iplfantasy.service.impl;

import com.iplfantasy.entity.Player;
import com.iplfantasy.entity.Team;
import com.iplfantasy.repository.PlayerRepository;
import com.iplfantasy.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repo;

    @Override
    public Player createOrUpdate(String playerName, Team team) {
        List<Player> existing = repo.findByTeam(team);
        for (Player p : existing) {
            if (p.getPlayerName().equalsIgnoreCase(playerName)) {
                return p; 
            }
        }

        Player player = Player.builder()
                .playerName(playerName)
                .team(team)
                .build();
        repo.save(player);
        return player;
    }

    @Override
    public List<Player> getPlayersByTeam(Team team) {
        return repo.findByTeam(team);
    }

    @Override
    public List<Player> getPlayersByTeams(List<Team> teams) {
        return repo.findByTeams(teams);
    }
}
