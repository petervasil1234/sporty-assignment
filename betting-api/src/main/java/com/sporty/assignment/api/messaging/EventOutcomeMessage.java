package com.sporty.assignment.api.messaging;

import lombok.Builder;

@Builder
public record EventOutcomeMessage(String eventId, String eventName, String eventWinnerId) {}
