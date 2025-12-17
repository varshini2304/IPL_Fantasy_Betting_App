package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team1_id", nullable = false)
    private Team team1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team2_id", nullable = false)
    private Team team2;

    private LocalDateTime matchStartTime;
    private LocalDateTime tossTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MatchStatus matchStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toss_winner_team_id")
    private Team tossWinnerTeam;

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private List<Prediction> predictions;

    @OneToOne(mappedBy = "match", fetch = FetchType.LAZY)
    private MatchResult result;
}
