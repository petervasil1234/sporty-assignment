package com.sporty.assignment.outcome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.sporty.assignment.AbstractIntegrationTest;
import com.sporty.assignment.api.http.ApiPaths;
import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class EventOutcomeFlowIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldPublishAndConsumeEventOutcome() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>("""
                {"eventId":"event-100","eventName":"Final Match","eventWinnerId":"winner-A"}
                """, headers);

        var response = restTemplate.postForEntity(ApiPaths.EVENT_OUTCOMES, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var captor = ArgumentCaptor.forClass(EventOutcomeMessage.class);
        verify(eventOutcomeKafkaConsumer, timeout(5000)).consume(captor.capture());

        var message = captor.getValue();
        assertThat(message.eventId()).isEqualTo("event-100");
        assertThat(message.eventName()).isEqualTo("Final Match");
        assertThat(message.eventWinnerId()).isEqualTo("winner-A");
    }
}
