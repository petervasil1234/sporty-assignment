package com.sporty.assignment.settlement;

import com.sporty.assignment.api.messaging.BetSettlementMessage;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetSettlementService {

    private final BetSettlementRepository repository;
    private final Clock clock;

    public void settle(BetSettlementMessage message) {
        try {
            repository.save(BetSettlement.builder()
                    .betId(message.betId())
                    .result(message.result())
                    .settledAt(Instant.now(clock))
                    .build());
            log.debug("Settled bet: {} result: {}", message.betId(), message.result());
        } catch (DbActionExecutionException e) {
            log.warn("Bet already settled: {}", message.betId());
        }
    }

    public List<BetSettlement> findAll() {
        return (List<BetSettlement>) repository.findAll();
    }
}
