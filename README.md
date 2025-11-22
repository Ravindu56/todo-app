# Todo App

Simple Spring Boot Todo application with Tasks and Notes management.

Features
- Tasks: create, edit (modal), delete, toggle complete/incomplete
- Assign date and deadline date/time on tasks
- Sort tasks by title, assigned date, deadline or completion status
- Notes: create and delete notes (separate view)
- Home page with navigation to Tasks and Notes

Tech stack
- Java 21
- Spring Boot
- Spring Data JPA (H2/MySQL or your configured DB)
- Thymeleaf templates
- Bootstrap 5 for UI
- Lombok (compile-time)

Prerequisites
- JDK 21+ installed and `JAVA_HOME` configured
- Maven (or use the included Maven wrapper)

Build
Open PowerShell in the project root and run:

```powershell
.\mvnw.cmd -DskipTests clean package
```

Run (development)

```powershell
.\mvnw.cmd spring-boot:run
```

Or run the packaged JAR:

```powershell
java -jar .\target\todo-0.0.1-SNAPSHOT.jar
```

Open the app in your browser:

- Home: http://localhost:8080/
- Tasks: http://localhost:8080/tasks
- Notes: http://localhost:8080/notes

Database / schema notes
- The project uses JPA entities. If `spring.jpa.hibernate.ddl-auto=update` is enabled, Hibernate will add the new columns automatically (useful for development).
- New/important columns:
  - `Task`: `assigned_at` (LocalDateTime), `deadline_at` (LocalDateTime)
  - `Note`: `created_at` (LocalDateTime)
- If you use a managed/production database, add migration scripts (Flyway/Liquibase) to alter tables instead of relying on `ddl-auto`.

UI notes
- The Tasks page uses Bootstrap `list-group` items and provides Add/Edit modals.
- The Add and Edit modals include a `Home` button (links to `/`).

Development tips
- If CodeLens Run/Debug links do not appear in VS Code, ensure Java extensions are installed and the project is imported by the Java language server.
- For larger data sets, consider switching the in-memory sorting to database-level sorting via `Sort` and repository queries.

Contact
- If you want further changes (AJAX updates, validation, migrations), open an issue or ask for help.
