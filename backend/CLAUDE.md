# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application
./mvnw spring-boot:run

# Build
./mvnw clean install

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ShifterApplicationTests

# Package (produces target/shifter.jar)
./mvnw clean package
```

The app loads a `.env` file from the project root for local development (using dotenv-java). Required env vars are listed in `ShifterApplication.java`. The server starts on port 8080 by default (overridable via `PORT`).

## Architecture

Spring Boot 3.5.3 / Java 17. The codebase is structured as DDD bounded contexts under `com.shifterwebapp.shifter`.

### Bounded contexts

| Context | Responsibility |
|---|---|
| `identity/` | User, Expert, Admin accounts |
| `auth/` | JWT, OAuth2 Google, email verification |
| `catalog/` | Courses, modules, lectures, skills, topics, pricing |
| `learning/` | Enrollments, lecture progress, certificates, reviews, user skills |
| `assessment/` | Quizzes, questions, attempts, scoring |
| `collection/` | Learning paths and bundles (curated + personalized) |
| `commerce/` | Orders and payments |
| `consultation/` | Expert meetings, scheduling, reminders |
| `notification/` | Email delivery via Zeptomail (`EmailGateway`), scheduling |
| `shared/` | `Language`, cross-context enums (`Difficulty`, `SkillProficiency`, `LanguageCode`), exceptions, utils |
| `infrastructure/` | `AppConfig`, `SecurityConfig`, `S3Gateway` (AWS file storage) |

### Internal layout (every context)

```
{context}/
├── domain/          # entities, domain enums
├── application/     # service interfaces + impl/
├── infrastructure/  # repositories, mappers, external adapters
└── web/
    ├── request/     # inbound HTTP bodies (*Request)
    └── response/    # outbound HTTP bodies (*Response)
```

### Naming conventions

- Service implementations: `XxxServiceImpl` (not `ImplXxx`)
- Inbound DTOs: `*Request`
- Outbound DTOs: `*Response`
- Translation entities: `*Translation` (e.g., `CourseTranslation`, not `CourseTranslate`)
- External adapters: `*Gateway` (e.g., `EmailGateway`, `S3Gateway`, `ZoomGateway`, `GoogleCalendarGateway`)

### Entity hierarchy

`Account` is an abstract `@MappedSuperclass`. `User`, `Expert`, and `Admin` extend it. Both `User` and `Expert` implement `UserDetails`; `AppConfig` queries both repositories during authentication. The DB table for `User` is `_user` (SQL reserved word).

### Cascade rules

Cascades and `orphanRemoval` are set **only for relationships within the same bounded context** (same aggregate). Cross-context relationships use `cascade = {}` and no `orphanRemoval` — the owning context manages its own lifecycle.

### Service pattern

Interfaces in `application/`, implementations in `application/impl/`. All use `@RequiredArgsConstructor` (Lombok) and `@Transactional` on write methods.

### API conventions

- Public prefix: `/api` (`api.base.path`)
- Admin prefix: `/api/admin` (`api.admin.path`)
- MapStruct mappers live in `infrastructure/mapper/` per context.

### Authentication

JWT tokens are HTTP cookies. `JwtService` signs with HMAC-SHA256; claims include `userId` and `role` (`ROLE_USER` / `ROLE_EXPERT`). `JwtAuthFilter` populates `SecurityContext`. OAuth2 Google handled by `OAuth2SuccessHandler`.

`SecurityConfig` and `AuthService` have significant sections commented out — check carefully before editing.

### Database

PostgreSQL, HikariCP (max 20 connections), OSIV disabled. Schema is managed by Hibernate (`ddl-auto`). Translation entities pair with their aggregate (e.g., `CourseTranslation`, `BundleTranslation`) for multilingual support.

### External services

| Service | Adapter class | Env vars |
|---|---|---|
| AWS S3 | `infrastructure/storage/S3Gateway` | `AWS_S3_REGION`, `AWS_S3_BUCKET_NAME`, `AWS_S3_ACCESS_KEY`, `AWS_S3_SECRET_KEY` |
| Zeptomail | `notification/EmailGateway` | `ZEPTOMAIL_API_KEY` |
| Google OAuth2 | `auth/infrastructure/OAuth2SuccessHandler` | `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET` |
| Google Calendar | `consultation/infrastructure/GoogleCalendarGateway` | `GOOGLE_EXPERT_CALENDAR_ID` |
| Zoom | `consultation/infrastructure/ZoomGateway` | `ZOOM_ACCOUNT_ID`, `ZOOM_CLIENT_ID`, `ZOOM_CLIENT_SECRET` |

### Key libraries

- **Lombok** — `@Data`, `@Builder`, `@RequiredArgsConstructor` throughout; no manual getters/setters in entities
- **MapStruct 1.6.3** — annotation processor; mappers in `infrastructure/mapper/` per context
- **jjwt 0.11.5** — JWT creation and validation
- **PDFBox 2.0.30** — certificate PDF generation (`learning/infrastructure/PdfManipulationService`)
