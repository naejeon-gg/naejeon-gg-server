package com.chlwkddn.scrim_com.domain.user.dto.res;

import java.util.List;

public record TeamShuffleRes(
        TeamRes teamA,
        TeamRes teamB
) {
    public record TeamRes(
            List<PlayerRes> members,
            double averageRating
    ) {
        public record PlayerRes(
                String name,
                double rating,
                String mainPosition,
                String subPosition
        ) {
        }
    }
}
