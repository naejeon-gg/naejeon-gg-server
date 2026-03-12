package com.chlwkddn.scrim_com.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String puuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rank topRank;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rank currentRank;

    @Column(nullable = false)
    private Position mainPosition;

    @Column(nullable = false)
    private Position subPosition;
}
