package com.sporty.assignment.settlement;

import static org.assertj.core.api.Assertions.assertThat;

import com.sporty.assignment.AbstractIntegrationTest;
import com.sporty.assignment.api.messaging.BetResult;
import com.sporty.assignment.api.messaging.BetSettlementMessage;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BetSettlementServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private BetSettlementService settlementService;

    @Test
    void shouldPersistSettlement() {
        var message = BetSettlementMessage.builder()
                .betId(1L)
                .userId("user-1")
                .eventId("event-100")
                .result(BetResult.WON)
                .betAmount(BigDecimal.valueOf(50))
                .build();

        settlementService.settle(message);

        var settlements = betSettlementRepository.findAll();
        assertThat(settlements).hasSize(1);

        var settlement = settlements.iterator().next();
        assertThat(settlement.betId()).isEqualTo(1L);
        assertThat(settlement.result()).isEqualTo(BetResult.WON);
        assertThat(settlement.settledAt()).isEqualTo(FIXED_TIME);
    }

    @Test
    void shouldBeIdempotent() {
        var message = BetSettlementMessage.builder()
                .betId(2L)
                .userId("user-2")
                .eventId("event-100")
                .result(BetResult.LOST)
                .betAmount(BigDecimal.valueOf(100))
                .build();

        settlementService.settle(message);
        settlementService.settle(message);

        var settlements = betSettlementRepository.findAll();
        assertThat(settlements).hasSize(1);
    }
}
