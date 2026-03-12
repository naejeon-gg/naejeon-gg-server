package com.chlwkddn.scrim_com.domain.user.dto.req;

import java.util.List;

public record TeamShuffleReq(List<PlayerReq> players) {
    public record PlayerReq(
            String name,
            String topRank,
            String mainPosition,
            String subPosition
    ) {
    }
}
