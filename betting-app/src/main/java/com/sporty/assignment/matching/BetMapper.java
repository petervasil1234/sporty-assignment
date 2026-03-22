package com.sporty.assignment.matching;

import com.sporty.assignment.api.http.ApiBet;

public final class BetMapper {

    private BetMapper() {}

    public static ApiBet toApi(Bet bet) {
        return ApiBet.builder()
                .id(bet.id())
                .userId(bet.userId())
                .eventId(bet.eventId())
                .eventMarketId(bet.eventMarketId())
                .eventWinnerId(bet.eventWinnerId())
                .betAmount(bet.betAmount())
                .build();
    }
}
