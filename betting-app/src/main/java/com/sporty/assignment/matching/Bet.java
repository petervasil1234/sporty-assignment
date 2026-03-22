package com.sporty.assignment.matching;

import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record Bet(
        @Id Long id, String userId, String eventId, String eventMarketId, String eventWinnerId, BigDecimal betAmount) {}
