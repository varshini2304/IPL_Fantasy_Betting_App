package com.iplfantasy.service.impl;

import com.iplfantasy.dto.LeaderboardEntry;
import com.iplfantasy.repository.LeaderboardRepository;
import com.iplfantasy.service.LeaderboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private LeaderboardRepository repo;

    @Override
    public List<LeaderboardEntry> getLeaderboard() {

        List<Object[]> rows = repo.fetchLeaderboard();
        List<LeaderboardEntry> result = new ArrayList<>();

        int rank = 1;
        Long previousPoints = null;
        int currentRank = 1;

        for (Object[] row : rows) {

            Long userId = (Long) row[0];
            String username = (String) row[1];
            Long points = (Long) (row[2] == null ? 0L : row[2]);

            if (previousPoints != null && !points.equals(previousPoints)) {
                currentRank = rank;
            }
            previousPoints = points;

            result.add(
                    new LeaderboardEntry(
                            userId,
                            username,
                            points.intValue(),
                            currentRank));
            rank++;
        }

        return result;
    }

    @Override
    public LeaderboardEntry getUserStanding(Long userId) {
        return getLeaderboard().stream()
                .filter(e -> e.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<LeaderboardEntry> getTopThree() {
        return getLeaderboard()
                .stream()
                .limit(3)
                .toList();
    }
}
