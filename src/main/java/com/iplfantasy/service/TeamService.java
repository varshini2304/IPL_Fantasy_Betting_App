package com.iplfantasy.service;

import com.iplfantasy.entity.Team;
import java.util.List;

public interface TeamService {

    Team createOrUpdate(String name, int points);

    Team getById(Long id);

    Team getByName(String name);

    List<Team> getAllTeams();
}
