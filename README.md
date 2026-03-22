# Sports Betting Settlement Trigger Service

Backend service simulating sports betting event outcome handling and bet settlement via Kafka and RocketMQ.

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
