# Repository Guidelines

## Project Structure & Module Organization
- Root Maven aggregator: `pom.xml` (Java 11, Spring Boot).
- Core modules: `media-platform-common/`, `media-platform-utils/`, `media-platform-model/`, `media-platform-feign-api/`.
- Services: `media-platform-service/` (user, article, wemedia, schedule, search, behavior, admin, comment).
- Gateways: `media-platform-gateway/` (app, admin, wemedia gateways).
- Tests and samples: `media-platform-test/` (Kafka, ES init, Mongo, Freemarker, Minio, Tesseract).
- Frontend artifacts: `html/app-web/`, `html/wemedia-web/` (prebuilt bundles).
- Database DDL/seed: `mysql/` (SQL files).

## Build, Test, and Development Commands
- Build all modules: `mvn clean install -DskipTests`
- Run unit tests: `mvn test`
- Run a service locally (example wemedia):
  - `mvn spring-boot:run -pl media-platform-service/media-platform-wemedia -am`
- Package a service JAR: `mvn -pl media-platform-service/media-platform-wemedia -am package`
- Run a gateway (example wemedia):
  - `mvn spring-boot:run -pl media-platform-gateway/media-platform-wemedia-gateway -am`

## Coding Style & Naming Conventions
- Java 11, 4-space indentation, UTF-8.
- Packages: `com.mediaplatform.<module>...` (lowercase).
- Classes: `PascalCase`; methods/fields: `camelCase`; constants: `UPPER_SNAKE_CASE`.
- Prefer Lombok (`@Data`, `@Slf4j`) and Spring stereotypes (`@Service`, `@RestController`).
- Controllers under `controller/v1`, services under `service`/`service/impl`, mappers under `mapper`.

## Testing Guidelines
- Frameworks: Spring Boot Test, JUnit 4.
- Test classes end with `*Test.java` under `src/test/java`.
- Run module tests only: `mvn test -pl <module-path>`.
- Add focused tests for mappers, services, and web layers; include basic context loads.

## Commit & Pull Request Guidelines
- Commits: short imperative subject; optional scope, e.g., `wemedia: fix material upload null check`.
- PRs: clear description, linked issues, steps to validate; include screenshots only if UI/API behavior changes.
- Keep diffs modular (one concern per PR). Update SQL or config samples when schema/config changes.

## Security & Configuration Tips
- External deps: MySQL, Kafka, Elasticsearch, Nacos (discovery/config), MinIO (optional).
- Local dev: set `bootstrap.yml`/`application.yml` for datasource, Nacos, Kafka, ES.
- Do not commit secrets; use env vars or Nacos config. Provide example values in README/issue, not in code.

