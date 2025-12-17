package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(nullable = false, length = 100)
    private String playerName;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
