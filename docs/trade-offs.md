# Trade-offs & Known Gaps

Collected during development. Will be included in README.

- **No Spring profiles** — single environment (localhost), reviewer runs Docker + app directly. Dev/prod split would add complexity without value.
- **Spring Data JDBC instead of JPA** — two entities, one SELECT, one INSERT. JPA would add lazy loading, dirty checking, and session management overhead with no benefit. In production with more complex domain relationships, JPA might be justified.
- **No pagination on GET /api/bets** — `findAll()` loads everything into memory. Acceptable for seed data demo. Production would use cursor-based pagination with filters.
