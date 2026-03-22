package com.sporty.assignment.matching;

import com.sporty.assignment.api.messaging.BetResult;
import com.sporty.assignment.api.messaging.BetSettlementMessage;
import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetMatchingService {

    private final BetRepository betRepository;
    private final BetSettlementProducer settlementProducer;

    public void matchAndSettle(EventOutcomeMessage outcome) {
        List<Bet> bets = betRepository.findByEventId(outcome.eventId());

        if (bets.isEmpty()) {
            log.debug("No bets found for event: {}", outcome.eventId());
            return;
        }

        long wonCount = 0;
        long lostCount = 0;

        for (Bet bet : bets) {
            BetResult result = bet.eventWinnerId().equals(outcome.eventWinnerId()) ? BetResult.WON : BetResult.LOST;

            settlementProducer.send(toSettlementMessage(bet, result));

            if (result == BetResult.WON) {
                wonCount++;
            } else {
                lostCount++;
            }
        }

        log.debug(
                "Matched {} bets for event: {} won: {} lost: {}", bets.size(), outcome.eventId(), wonCount, lostCount);
    }

    private BetSettlementMessage toSettlementMessage(Bet bet, BetResult result) {
        return BetSettlementMessage.builder()
                .betId(bet.id())
                .userId(bet.userId())
                .eventId(bet.eventId())
                .result(result)
                .betAmount(bet.betAmount())
                .build();
    }
}
