# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Maven multi-module Spring Boot 3.5 application using Java 25. Modules: `betting-api` (shared contracts) and `betting-app` (Spring Boot app with Kafka, RocketMQ, Spring Data JDBC, H2).

## Build & Test Commands

```bash
./mvnw clean install                        # Build all modules and run all tests
./mvnw test -pl betting-app                 # Run all tests in betting-app
./mvnw test -pl betting-app -Dtest=BetMatchingServiceTest  # Run a single test class
./mvnw test -pl betting-app -Dtest="BetMatchingServiceTest#methodName"  # Run a single test method
./mvnw spring-boot:run -pl betting-app      # Run the application
```

## Code Style

- Enforced via `.editorconfig`
- Base package: `com.sporty.assignment` (`com.sporty.assignment.api` for betting-api)
- No wildcard/star imports
- REST DTOs use `Api` prefix — e.g. `ApiEventOutcome`, `ApiBetSettlement`
- Kafka/RocketMQ messages use `Message` suffix — e.g. `EventOutcomeMessage`, `BetSettlementMessage`
- Log format: `key: value` style — e.g. `log.debug("Published to topic: {} eventId: {}", topic, id)`
- Use `@MockitoSpyBean` / `@MockitoBean` instead of deprecated `@SpyBean` / `@MockBean`
- Integration tests must extend `AbstractIntegrationTest` (shared context, enforced by `SingleContextGuard`)

## Git Conventions

Use [Conventional Commits](https://www.conventionalcommits.org/): `<type>(<scope>): <description>`

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `ci`, `perf`, `build`
