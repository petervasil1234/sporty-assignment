# Trade-offs & Known Gaps

- **No Spring profiles** — single environment (localhost), reviewer runs Docker + app directly. Dev/prod split would add complexity without value.
- **Spring Data JDBC instead of JPA** — two entities, one SELECT, one INSERT. JPA would add lazy loading, dirty checking, and session management overhead with no benefit. In production with more complex domain relationships, JPA might be justified.
- **No pagination on GET /api/bets** — `findAll()` loads everything into memory. Acceptable for seed data demo. Production would use cursor-based pagination with filters.
- **`findByEventId()` loads all bets into memory** — blocks Kafka consumer and risks OOM with millions of bets per event. Production solution: persist outcome to inbox table (fast INSERT), process bets in paginated batches via a separate scheduled job.
- **No index on `event_id`** — unnecessary for seed data volume. Required in production.
- **No partial failure handling in matching loop** — if one settlement send fails, the entire batch fails. Production solution: scheduled job with cursor-based iteration, skip failed bets and retry later.
- **No API versioning** — single version, no evolution expected. Production would use path-based versioning (e.g. `/v1/api/...`).
- **`eventMarketId` not used in matching** — present on Bet per spec, but EventOutcome lacks market info. This would have to be explained to me a bit better to understand the intended domain model.
- **UUID vs Long IDs** — Long used for demo simplicity. Production would use UUIDs for cross-service uniqueness.
- **RocketMQ unavailable** — Kafka consumer blocks on `syncSend()`. Production solution: outbox pattern to decouple Kafka consumption from RocketMQ publishing.
- **No Flyway** — H2 in-memory, nothing to migrate. Production would use Flyway with a persistent database.
