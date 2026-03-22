# Sports Betting Settlement Trigger Service

Backend service simulating sports betting event outcome handling and bet settlement via Kafka and RocketMQ.

## Prerequisites

- Java 25 (install via [SDKMAN](https://sdkman.io/): `sdk env install`)
- Docker (for Kafka)

## How to run

Start infrastructure:

```bash
docker compose up -d
```

Start the application:

```bash
./mvnw spring-boot:run -pl betting-app
```

## API

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Publish event outcome

```bash
curl -X POST http://localhost:8080/api/event-outcomes \
  -H "Content-Type: application/json" \
  -d '{"eventId":"event-100","eventName":"Final Match","eventWinnerId":"winner-A"}'
```

### List all bets

```bash
curl http://localhost:8080/api/bets
```

### List bets for specific event

```bash
curl http://localhost:8080/api/bets?eventId=event-100
```

### List settlements

```bash
curl http://localhost:8080/api/settlements
```
