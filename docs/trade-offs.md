# Trade-offs & Known Gaps

Collected during development. Will be included in README.

- **No Spring profiles** — single environment (localhost), reviewer runs Docker + app directly. Dev/prod split would add complexity without value.
- **Spring Data JDBC instead of JPA** — two entities, one SELECT, one INSERT. JPA would add lazy loading, dirty checking, and session management overhead with no benefit. In production with more complex domain relationships, JPA might be justified.
- **No pagination on GET /api/bets** — `findAll()` loads everything into memory. Acceptable for seed data demo. Production would use cursor-based pagination with filters.
- **`findByEventId()` loads all bets into memory** — blocks Kafka consumer and risks OOM with millions of bets per event. Production solution: persist outcome to inbox table (fast INSERT), process bets in paginated batches via a separate scheduled job.
- **No index on `event_id`** — unnecessary for seed data volume. Required in production.
- **No partial failure handling in matching loop** — if one settlement send fails, the entire batch fails. Production solution: scheduled job with cursor-based iteration, skip failed bets and retry later.
