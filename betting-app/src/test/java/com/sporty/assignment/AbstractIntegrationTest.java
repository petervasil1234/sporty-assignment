package com.sporty.assignment;

import com.sporty.assignment.api.messaging.Topics;
import com.sporty.assignment.matching.BetRepository;
import com.sporty.assignment.matching.EventOutcomeKafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

/**
 * Base class for all integration tests. Provides a shared Spring application context
 * with embedded Kafka and common spy beans. All integration tests must extend this class
 * to ensure a single context is reused — enforced by {@link SingleContextGuard}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = Topics.EVENT_OUTCOMES)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected BetRepository betRepository;

    @MockitoSpyBean
    protected EventOutcomeKafkaConsumer eventOutcomeKafkaConsumer;

    @BeforeEach
    void cleanUp() {
        betRepository.deleteAll();
    }
}
