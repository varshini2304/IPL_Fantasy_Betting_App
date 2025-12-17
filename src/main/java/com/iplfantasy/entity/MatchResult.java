package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @OneToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "winner_team_id", nullable = false)
    private Team winnerTeam;

    @ManyToOne
    @JoinColumn(name = "toss_winner_team_id")
    private Team tossWinner;

    @Column(length = 100)
    private String manOfTheMatch;

    @Column(length = 100)
    private String topScorer;

    private Integer winningTeamScore;

    private LocalDateTime updatedAt;
}
