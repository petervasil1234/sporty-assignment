package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import com.sporty.assignment.api.messaging.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventOutcomeKafkaConsumer {

    @KafkaListener(topics = Topics.EVENT_OUTCOMES)
    public void consume(EventOutcomeMessage message) {
        log.debug("Consumed outcome for event: {} winner: {}", message.eventId(), message.eventWinnerId());
    }
}
