package com.sporty.assignment.settlement;

import com.sporty.assignment.api.messaging.BetResult;
import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Builder
public record BetSettlement(
        @Id Long id,
        @NonNull Long betId,
        @NonNull BetResult result,
        @NonNull Instant settledAt) {}
