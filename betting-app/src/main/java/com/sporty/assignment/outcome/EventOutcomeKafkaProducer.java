package com.sporty.assignment.outcome;

import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import com.sporty.assignment.api.messaging.Topics;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOutcomeKafkaProducer {

    private final KafkaTemplate<String, EventOutcomeMessage> kafkaTemplate;

    public void send(EventOutcomeMessage message) {
        try {
            kafkaTemplate
                    .send(Topics.EVENT_OUTCOMES, message.eventId(), message)
                    .get();
            log.debug("Published to topic: {} eventId: {}", Topics.EVENT_OUTCOMES, message.eventId());
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to publish to topic: {} eventId: {}", Topics.EVENT_OUTCOMES, message.eventId(), e);
            throw new KafkaException("Failed to publish to topic: " + Topics.EVENT_OUTCOMES, e);
        }
    }
}
