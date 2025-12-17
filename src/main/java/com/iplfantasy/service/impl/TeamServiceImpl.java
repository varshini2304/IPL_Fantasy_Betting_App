package com.iplfantasy.service.impl;

import com.iplfantasy.entity.Team;
import com.iplfantasy.repository.TeamRepository;
import com.iplfantasy.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository repo;

    @Override
    public Team createOrUpdate(String name, int points) {

        Team t = repo.findByName(name);

        if (t == null) {
            t = Team.builder()
                    .teamName(name)
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
        return repo.findByName(name);
    }

    @Override
    public List<Team> getAllTeams() {
        return repo.findAll();
    }
}
