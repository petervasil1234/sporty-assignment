package com.sporty.assignment.api.messaging;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record BetSettlementMessage(
        @NotNull Long betId,
        @NotNull String userId,
        @NotNull String eventId,
        @NotNull BetResult result,
        @NotNull BigDecimal betAmount) {}
