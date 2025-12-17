package com.iplfantasy.service;

import java.util.List;

import com.iplfantasy.dto.LeaderboardEntry;

public interface LeaderboardService {

    List<LeaderboardEntry> getLeaderboard();

    LeaderboardEntry getUserStanding(Long userId);

    List<LeaderboardEntry> getTopThree();
}
