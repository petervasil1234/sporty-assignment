package com.sporty.assignment.matching;

import static org.assertj.core.api.Assertions.assertThat;

import com.sporty.assignment.AbstractIntegrationTest;
import com.sporty.assignment.api.http.ApiBet;
import com.sporty.assignment.api.http.ApiPaths;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class BetControllerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setUp() {
        betRepository.saveAll(List.of(
                Bet.builder()
                        .userId("user-1")
                        .eventId("event-100")
                        .eventMarketId("market-1")
                        .eventWinnerId("winner-A")
                        .betAmount(BigDecimal.valueOf(50))
                        .build(),
                Bet.builder()
                        .userId("user-2")
                        .eventId("event-100")
                        .eventMarketId("market-1")
                        .eventWinnerId("winner-B")
                        .betAmount(BigDecimal.valueOf(100))
                        .build(),
                Bet.builder()
                        .userId("user-3")
                        .eventId("event-200")
                        .eventMarketId("market-2")
                        .eventWinnerId("winner-X")
                        .betAmount(BigDecimal.valueOf(75))
                        .build()));
    }

    @Test
    void shouldReturnAllBets() {
        var response = restTemplate.getForEntity(ApiPaths.BETS, ApiBet[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void shouldReturnBetsForEvent() {
        var response = restTemplate.getForEntity(ApiPaths.BETS + "?eventId=event-100", ApiBet[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody())
                .allSatisfy(bet -> assertThat(bet.eventId()).isEqualTo("event-100"));
    }

    @Test
    void shouldReturnEmptyListForUnknownEvent() {
        var response = restTemplate.getForEntity(ApiPaths.BETS + "?eventId=unknown", ApiBet[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }
}
