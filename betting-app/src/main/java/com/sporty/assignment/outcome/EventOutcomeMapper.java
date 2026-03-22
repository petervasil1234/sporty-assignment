package com.sporty.assignment.outcome;

import com.sporty.assignment.api.http.ApiEventOutcome;
import com.sporty.assignment.api.messaging.EventOutcomeMessage;

public final class EventOutcomeMapper {

    private EventOutcomeMapper() {}

    public static EventOutcomeMessage toMessage(ApiEventOutcome apiEventOutcome) {
        return EventOutcomeMessage.builder()
                .eventId(apiEventOutcome.eventId())
                .eventName(apiEventOutcome.eventName())
                .eventWinnerId(apiEventOutcome.eventWinnerId())
                .build();
    }
}
