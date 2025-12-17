package com.iplfantasy.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaderboardEntry {
    private Long userId;
    private String username;
    private int totalPoints;
    private int rank;
}
