# Sports Betting Settlement Trigger Service — Design Spec

## Overview

Backend service simulating sports betting event outcome handling and bet settlement via Kafka and RocketMQ. Sporty Group mid-senior BE assignment.

The system receives a sports event outcome via API endpoint, publishes it to Kafka, consumes the event from Kafka, matches it against existing bets, forwards settlement messages to RocketMQ, and persists settlement results.

## Tech Stack

- Java 25, Spring Boot 3.5.x, Maven multi-module
- Spring Data JDBC + H2 in-memory database
- Apache Kafka (KRaft mode, Docker)
- Apache RocketMQ (NameServer + Broker, Docker) with mock fallback
- Lombok - for builders and boilerplate reduction
- springdoc-openapi (Swagger UI)

## Project Structure

Maven multi-module with two modules:

- **`betting-api`** — shared API and message contracts (pure Java records + enum), zero Spring dependencies
- **`betting-app`** — Spring Boot application with packages organized by domain boundary: `outcome`, `matching`, `settlement`, `config`

### Module rationale

`betting-api` is a separate Maven module containing shared API and message contracts (pure Java records + enum). In production, this JAR would be shared between microservices. It has zero Spring dependencies.

### Package rationale

Each package (outcome, matching, settlement) represents a potential microservice boundary. No cross-package repository dependencies. Persistence lives inside the package that owns it.

## Domain Model

### betting-api module

**EventOutcomeMessage** (record)
- `eventId` (String)
- `eventName` (String)
- `eventWinnerId` (String)

**BetSettlementMessage** (record)
- `betId` (Long)
- `userId` (String)
- `eventId` (String)
- `result` (BetResult)
- `betAmount` (BigDecimal)

**BetResult** (enum)
- `WON`, `LOST`

### betting-app module

**Bet** — Spring Data JDBC entity, `@Builder`
- `id` (Long, @Id, auto-increment)
- `userId` (String)
- `eventId` (String)
- `eventMarketId` (String)
- `eventWinnerId` (String)
- `betAmount` (BigDecimal)

Immutable fact — represents what the user wagered. Never mutated.

**BetSettlement** — Spring Data JDBC entity, `@Builder`
- `id` (Long, @Id, auto-increment)
- `betId` (Long, UNIQUE constraint)
- `result` (BetResult)
- `settledAt` (Instant)

UNIQUE constraint on `betId` provides idempotency. Duplicate INSERT throws `DuplicateKeyException` — catch, log, skip.

### Why separate entities

Bet and BetSettlement are separate because a bet is an immutable fact and a settlement is the result of processing. In production they would live in separate databases. The code respects this boundary even in a single H2 instance.

## Flow

1. **API** — `POST /api/event-outcomes` receives event outcome, publishes to Kafka topic `event-outcomes` (key = eventId)
2. **Kafka consumer** — consumes from `event-outcomes`, finds matching bets by eventId, determines WON/LOST for each bet, sends individual messages to RocketMQ topic `bet-settlements`
3. **RocketMQ consumer** — consumes from `bet-settlements`, persists settlement to DB (UNIQUE constraint on betId provides idempotency)

### Key decisions

- **eventId as Kafka key** — same event always goes to same partition, guaranteeing ordering per event.
- **One RocketMQ message per bet** — retry granularity, parallel processing, no partial failure ambiguity.
- **Idempotency via UNIQUE constraint** — no optimistic locking or extra tables needed for current scope.

## Hybrid RocketMQ Strategy

`BetSettlementRocketProducer` is an interface:

```java
public interface BetSettlementRocketProducer {
    void send(BetSettlementMessage message);
}
```

- **Real implementation** — uses `RocketMQTemplate.syncSend()`, activated when RocketMQ is available.
- **Mock implementation** — logs the payload, activated via `@ConditionalOnMissingBean` or Spring profile as fallback.

If `rocketmq-spring-boot-starter` has compatibility issues with Java 25 / Spring Boot 3.5, the mock is ready in minutes.

## Persistence

H2 in-memory with `schema.sql` + `data.sql`. Seed bets cover multiple events and users for demo (both WON and LOST outcomes). No foreign keys between tables — respects microservice boundary even in shared H2.

## REST Endpoints

| Method | Path | Package | Description |
|--------|------|---------|-------------|
| POST | /api/event-outcomes | outcome | Publish event outcome to Kafka |
| GET | /api/bets | matching | List all bets |
| GET | /api/bets?eventId={id} | matching | List bets for specific event |
| GET | /api/settlements | settlement | List all settlement results |

## Trade-offs & Known Gaps

See [trade-offs.md](trade-offs.md) for a full list of design trade-offs and production considerations.
