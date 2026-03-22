package com.sporty.assignment.api.http;

import com.sporty.assignment.api.messaging.BetResult;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Builder;

@Builder
public record ApiBetSettlement(
        @NotNull Long id,
        @NotNull Long betId,
        @NotNull BetResult result,
        @NotNull Instant settledAt) {}
