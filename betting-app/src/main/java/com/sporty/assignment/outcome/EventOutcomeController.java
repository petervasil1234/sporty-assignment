package com.sporty.assignment.outcome;

import com.sporty.assignment.api.http.ApiEventOutcome;
import com.sporty.assignment.api.http.ApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Event Outcomes", description = "Publish sports event outcomes to Kafka")
public class EventOutcomeController {

    private final EventOutcomeKafkaProducer kafkaProducer;

    @PostMapping(ApiPaths.EVENT_OUTCOMES)
    @Operation(
            summary = "Publish event outcome",
            description = "Publishes a sports event outcome to Kafka for bet settlement processing")
    @ApiResponse(responseCode = "200", description = "Outcome published successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<Void> publish(@Valid @RequestBody ApiEventOutcome request) {
        log.debug("Received outcome for event: {}", request.eventId());
        kafkaProducer.send(EventOutcomeMapper.toMessage(request));
        return ResponseEntity.ok().build();
    }
}
