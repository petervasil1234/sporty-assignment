package com.sporty.assignment.integration;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 * Fails fast if more than one Spring application context is created during the test suite.
 * All integration tests should extend {@link AbstractIntegrationTest} to share a single context.
 * A static counter tracks context creation across the JVM — if a second context is instantiated,
 * the constructor throws {@link IllegalStateException}.
 */
@Component
public class SingleContextGuard {

    private static final AtomicInteger CONTEXT_COUNT = new AtomicInteger(0);

    public SingleContextGuard() {
        if (CONTEXT_COUNT.incrementAndGet() > 1) {
            throw new IllegalStateException(
                    "Multiple Spring contexts detected! Ensure all integration tests extend AbstractIntegrationTest.");
        }
    }
}
