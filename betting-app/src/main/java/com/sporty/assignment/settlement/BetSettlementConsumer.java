package com.sporty.assignment.settlement;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Consumes settlement messages and persists them.
 * Currently called directly from LoggingBetSettlementProducer.
 * Will be replaced with RocketMQ consumer when RocketMQ is integrated.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BetSettlementConsumer {

    private final BetSettlementService settlementService;

    public void consume(BetSettlementMessage message) {
        log.debug("Consuming settlement for bet: {} result: {}", message.betId(), message.result());
        settlementService.settle(message);
    }
}
