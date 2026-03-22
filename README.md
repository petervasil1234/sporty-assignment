# Sports Betting Settlement Trigger Service

Backend service simulating sports betting event outcome handling and bet settlement via Kafka and RocketMQ.

## Prerequisites

- Docker and Docker Compose

## How to run

### Docker Compose (recommended)

```bash
docker compose up -d --build
```

This starts Kafka, RocketMQ, and the application. Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

### Local development

Prerequisites: Java 25 ([SDKMAN](https://sdkman.io/): `sdk env install`), Docker

```bash
docker compose up -d kafka rocketmq-namesrv rocketmq-broker
./mvnw spring-boot:run -pl betting-app
```

## Testing the flow

All endpoints are available in [Swagger UI](http://localhost:8080/swagger-ui.html) or via curl below.

The application starts with 4 pre-populated bets:

| Bet ID | User   | Event     | Predicted Winner |  Amount |
|--------|--------|-----------|------------------|---------|
| 1      | user-1 | event-100 | winner-A         |   50.00 |
| 2      | user-2 | event-100 | winner-A         |  100.00 |
| 3      | user-3 | event-100 | winner-B         |   75.00 |
| 4      | user-1 | event-200 | winner-X         |  200.00 |

### Step 1: Verify bets are loaded

```bash
curl -s http://localhost:8080/api/bets | jq
```

### Step 2: Verify no settlements exist yet

```bash
curl -s http://localhost:8080/api/settlements | jq
```

Expected: `[]`

### Step 3: Publish event outcome — winner-A wins event-100

```bash
curl -X POST http://localhost:8080/api/event-outcomes \
  -H "Content-Type: application/json" \
  -d '{"eventId":"event-100","eventName":"Final Match","eventWinnerId":"winner-A"}'
```

### Step 4: Check settlements

```bash
curl -s http://localhost:8080/api/settlements | jq
```

Expected: 3 settlements — bet 1 WON, bet 2 WON, bet 3 LOST. Bet 4 is untouched (different event).

### Step 5: Verify idempotency — publish same outcome again

```bash
curl -X POST http://localhost:8080/api/event-outcomes \
  -H "Content-Type: application/json" \
  -d '{"eventId":"event-100","eventName":"Final Match","eventWinnerId":"winner-A"}'
```

```bash
curl -s http://localhost:8080/api/settlements | jq
```

Expected: still 3 settlements — duplicates are ignored via UNIQUE constraint on bet_id.
