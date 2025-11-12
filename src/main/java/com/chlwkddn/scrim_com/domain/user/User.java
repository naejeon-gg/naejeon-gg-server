package com.chlwkddn.scrim_com.domain.user;

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

    @Column
    private String puuid;

    @Column
    private String name;
    @Column
    private String tag;

    @Column
    private String topRank;
    @Column
    private String currentRank;

    @Column
    private String mainRole;

    @Column
    private String subRole;

    @Column
    private Long review;
}
