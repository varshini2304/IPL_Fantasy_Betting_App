package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leaderboard")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private int totalPoints;

    private int rank;
}
	