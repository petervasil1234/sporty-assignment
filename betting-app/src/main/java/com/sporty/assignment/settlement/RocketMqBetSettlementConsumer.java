package com.sporty.assignment.settlement;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import com.sporty.assignment.api.messaging.RocketMqTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "true")
@RocketMQMessageListener(topic = RocketMqTopics.BET_SETTLEMENTS, consumerGroup = "settlement-consumer")
public class RocketMqBetSettlementConsumer implements RocketMQListener<BetSettlementMessage> {

    private final BetSettlementService settlementService;

    @Override
    public void onMessage(BetSettlementMessage message) {
        log.debug("Consuming settlement for bet: {} result: {}", message.betId(), message.result());
        settlementService.settle(message);
    }
}
