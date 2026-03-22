package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import com.sporty.assignment.api.messaging.RocketMqTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "true")
public class RocketMqBetSettlementProducer implements BetSettlementProducer {

    private final RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(BetSettlementMessage message) {
        rocketMQTemplate.convertAndSend(RocketMqTopics.BET_SETTLEMENTS, message);
        log.debug("Sent settlement to RocketMQ topic: {} betId: {}", RocketMqTopics.BET_SETTLEMENTS, message.betId());
    }
}
