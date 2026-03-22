package com.sporty.assignment;

import com.sporty.assignment.api.messaging.KafkaTopics;
import com.sporty.assignment.matching.BetRepository;
import com.sporty.assignment.matching.EventOutcomeKafkaConsumer;
import com.sporty.assignment.settlement.BetSettlementRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

/**
 * Base class for all integration tests. Provides a shared Spring application context
 * with embedded Kafka and common spy beans. All integration tests must extend this class
 * to ensure a single context is reused — enforced by {@link SingleContextGuard}.
 */
@Import(AbstractIntegrationTest.TestClockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "rocketmq.enabled=false")
@EmbeddedKafka(topics = KafkaTopics.EVENT_OUTCOMES)
public abstract class AbstractIntegrationTest {

    public static final Instant FIXED_TIME = Instant.parse("2026-01-01T12:00:00Z");

    @TestConfiguration
    static class TestClockConfig {

        @Bean
        @Primary
        Clock testClock() {
            return Clock.fixed(FIXED_TIME, ZoneOffset.UTC);
        }
    }

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected BetRepository betRepository;

    @Autowired
    protected BetSettlementRepository betSettlementRepository;

    @MockitoSpyBean
    protected EventOutcomeKafkaConsumer eventOutcomeKafkaConsumer;

    @BeforeEach
    void cleanUp() {
        betSettlementRepository.deleteAll();
        betRepository.deleteAll();
    }
}
