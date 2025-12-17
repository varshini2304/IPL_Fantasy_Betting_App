package com.iplfantasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(nullable = false, unique = true, length = 50)
    private String teamName;

    @Column(nullable = false)
    private int currentPoints;

    @OneToMany(mappedBy = "team1", fetch = FetchType.LAZY)
    private List<Match> matchesAsTeam1;

    @OneToMany(mappedBy = "team2", fetch = FetchType.LAZY)
    private List<Match> matchesAsTeam2;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> players;
}
