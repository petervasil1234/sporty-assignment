package com.sporty.assignment.api.http;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ApiBet(
        @NotNull Long id,
        @NotNull String userId,
        @NotNull String eventId,
        @NotNull String eventMarketId,
        @NotNull String eventWinnerId,
        @NotNull BigDecimal betAmount) {}
