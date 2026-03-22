package com.sporty.assignment.api.messaging;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EventOutcomeMessage(
        @NotNull String eventId,
        @NotNull String eventName,
        @NotNull String eventWinnerId) {}
