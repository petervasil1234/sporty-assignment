package com.sporty.assignment.settlement;

import static org.assertj.core.api.Assertions.assertThat;

import com.sporty.assignment.AbstractIntegrationTest;
import com.sporty.assignment.api.http.ApiBetSettlement;
import com.sporty.assignment.api.http.ApiPaths;
import com.sporty.assignment.api.messaging.BetResult;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class BetSettlementControllerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setUp() {
        betSettlementRepository.saveAll(List.of(
                BetSettlement.builder()
                        .betId(1L)
                        .result(BetResult.WON)
                        .settledAt(FIXED_TIME)
                        .build(),
                BetSettlement.builder()
                        .betId(2L)
                        .result(BetResult.LOST)
                        .settledAt(FIXED_TIME)
                        .build()));
    }

    @Test
    void shouldReturnAllSettlements() {
        var response = restTemplate.getForEntity(ApiPaths.SETTLEMENTS, ApiBetSettlement[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void shouldReturnSettlementsWithCorrectResults() {
        var response = restTemplate.getForEntity(ApiPaths.SETTLEMENTS, ApiBetSettlement[].class);

        assertThat(response.getBody())
                .anyMatch(s -> s.result() == BetResult.WON)
                .anyMatch(s -> s.result() == BetResult.LOST);
        assertThat(response.getBody()).allSatisfy(s -> assertThat(s.settledAt()).isEqualTo(FIXED_TIME));
    }
}
