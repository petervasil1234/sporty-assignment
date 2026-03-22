package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import com.sporty.assignment.api.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOutcomeKafkaConsumer {

    private final BetMatchingService betMatchingService;

    @KafkaListener(topics = KafkaTopics.EVENT_OUTCOMES)
    public void consume(EventOutcomeMessage message) {
        log.debug("Consumed outcome for event: {} winner: {}", message.eventId(), message.eventWinnerId());
        betMatchingService.matchAndSettle(message);
    }
}
