package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "predictions", uniqueConstraints = @UniqueConstraint(columnNames = { "match_id", "user_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictionId;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "predicted_team_id", nullable = false)
    private Team predictedTeam;

    // Additional prediction types
    @ManyToOne
    @JoinColumn(name = "predicted_toss_winner_id")
    private Team predictedTossWinner;

    @Column(length = 100)
    private String predictedTopScorer;

    @Column(length = 100)
    private String predictedManOfTheMatch;

    private Integer predictedTotalRunsMin;
    private Integer predictedTotalRunsMax;

    private LocalDateTime predictionTime;

    private boolean locked;

    private Integer pointsAwarded;

    @OneToMany(mappedBy = "prediction", fetch = FetchType.LAZY)
    private List<PredictionHistory> history;
}
