package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation that logs settlement messages instead of sending to RocketMQ.
 * Activated when no real RocketMQ producer is available.
 */
@Slf4j
@Component
@ConditionalOnMissingBean(value = BetSettlementProducer.class, ignored = LoggingBetSettlementProducer.class)
public class LoggingBetSettlementProducer implements BetSettlementProducer {

    @Override
    public void send(BetSettlementMessage message) {
        log.info(
                "Settlement [mock] bet: {} user: {} event: {} result: {} amount: {}",
                message.betId(),
                message.userId(),
                message.eventId(),
                message.result(),
                message.betAmount());
    }
}
