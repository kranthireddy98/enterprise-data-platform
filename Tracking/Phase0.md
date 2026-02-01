✅ Phase 0.1 — Project creation

Spring Boot project

Correct dependencies

No auto JPA

DevTools enabled

✅ Phase 0.2 — Profile-based configuration

application.yml

application-local.yml

application-dev.yml

Strict validation with @ConfigurationProperties

✅ Phase 0.3 — Logging foundation

Correlation ID

MDC

JSON log format

Request/response logging filter

✅ Phase 0.4 — DataSource foundation

Unified DataSourceManager

Hikari

No Spring auto datasource

Safe for multi-DB

✅ Phase 0.5 — DB bootstrap

Create DB if missing

Create schema

Create master tables

Idempotent

Profile-controlled