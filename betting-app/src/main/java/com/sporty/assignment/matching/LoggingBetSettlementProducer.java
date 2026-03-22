package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation that logs settlement messages instead of sending to RocketMQ.
 * Activated when rocketmq.enabled is not true.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "false", matchIfMissing = true)
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
