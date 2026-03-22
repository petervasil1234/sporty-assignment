package com.sporty.assignment.api.http;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ApiEventOutcome(
        @NotNull String eventId,
        @NotNull String eventName,
        @NotNull String eventWinnerId) {}
