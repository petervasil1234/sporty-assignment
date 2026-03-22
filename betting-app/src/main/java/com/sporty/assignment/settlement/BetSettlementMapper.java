package com.sporty.assignment.settlement;

import com.sporty.assignment.api.http.ApiBetSettlement;

public final class BetSettlementMapper {

    private BetSettlementMapper() {}

    public static ApiBetSettlement toApi(BetSettlement settlement) {
        return ApiBetSettlement.builder()
                .id(settlement.id())
                .betId(settlement.betId())
                .result(settlement.result())
                .settledAt(settlement.settledAt())
                .build();
    }
}
