package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prediction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "prediction_id", nullable = false)
    private Prediction prediction;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "old_team_id")
    private Team oldTeam;

    @ManyToOne
    @JoinColumn(name = "new_team_id")
    private Team newTeam;

    @ManyToOne
    @JoinColumn(name = "old_toss_winner_id")
    private Team oldTossWinner;

    @ManyToOne
    @JoinColumn(name = "new_toss_winner_id")
    private Team newTossWinner;

    @Column(length = 100)
    private String oldTopScorer;

    @Column(length = 100)
    private String newTopScorer;

    @Column(length = 100)
    private String oldManOfTheMatch;

    @Column(length = 100)
    private String newManOfTheMatch;

    private Integer oldTotalRunsMin;
    private Integer oldTotalRunsMax;
    private Integer newTotalRunsMin;
    private Integer newTotalRunsMax;

    private LocalDateTime changeTime;

    private String changeReason;
}
