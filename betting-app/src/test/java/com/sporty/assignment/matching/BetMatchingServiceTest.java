package com.sporty.assignment.matching;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.sporty.assignment.api.messaging.BetResult;
import com.sporty.assignment.api.messaging.EventOutcomeMessage;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BetMatchingServiceTest {

    @Mock
    private BetRepository betRepository;

    @Mock
    private BetSettlementProducer settlementProducer;

    @InjectMocks
    private BetMatchingService betMatchingService;

    @Test
    void shouldMatchBetsAndSendSettlements() {
        var outcome = EventOutcomeMessage.builder()
                .eventId("event-100")
                .eventName("Final Match")
                .eventWinnerId("winner-A")
                .build();

        when(betRepository.findByEventId("event-100"))
                .thenReturn(List.of(
                        Bet.builder()
                                .id(1L)
                                .userId("user-1")
                                .eventId("event-100")
                                .eventMarketId("market-1")
                                .eventWinnerId("winner-A")
                                .betAmount(BigDecimal.valueOf(50))
                                .build(),
                        Bet.builder()
                                .id(2L)
                                .userId("user-2")
                                .eventId("event-100")
                                .eventMarketId("market-1")
                                .eventWinnerId("winner-B")
                                .betAmount(BigDecimal.valueOf(100))
                                .build()));

        betMatchingService.matchAndSettle(outcome);

        verify(settlementProducer).send(argThat(msg -> msg.betId() == 1L && msg.result() == BetResult.WON));
        verify(settlementProducer).send(argThat(msg -> msg.betId() == 2L && msg.result() == BetResult.LOST));
        verify(settlementProducer, times(2)).send(argThat(msg -> msg.eventId().equals("event-100")));
    }

    @Test
    void shouldNotSendSettlementsWhenNoBetsFound() {
        var outcome = EventOutcomeMessage.builder()
                .eventId("event-999")
                .eventName("Unknown Match")
                .eventWinnerId("winner-X")
                .build();

        when(betRepository.findByEventId("event-999")).thenReturn(List.of());

        betMatchingService.matchAndSettle(outcome);

        verifyNoInteractions(settlementProducer);
    }
}
