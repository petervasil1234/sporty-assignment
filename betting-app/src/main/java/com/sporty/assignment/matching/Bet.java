package com.sporty.assignment.matching;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Builder
public record Bet(
        @Id Long id,
        @NonNull String userId,
        @NonNull String eventId,
        @NonNull String eventMarketId,
        @NonNull String eventWinnerId,
        @NonNull BigDecimal betAmount,
        @NonNull Instant createdAt) {}
