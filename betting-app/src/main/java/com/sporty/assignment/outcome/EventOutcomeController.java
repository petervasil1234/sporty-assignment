package com.sporty.assignment.outcome;

import com.sporty.assignment.api.http.ApiEventOutcome;
import com.sporty.assignment.api.http.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventOutcomeController {

    private final EventOutcomeKafkaProducer kafkaProducer;

    @PostMapping(ApiPaths.EVENT_OUTCOMES)
    public ResponseEntity<Void> publish(@Valid @RequestBody ApiEventOutcome request) {
        log.debug("Received outcome for event: {}", request.eventId());
        kafkaProducer.send(EventOutcomeMapper.toMessage(request));
        return ResponseEntity.ok().build();
    }
}
