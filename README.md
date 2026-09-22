# Student Record Management System (SRMS)

A full-stack CRUD app: Spring Boot + H2 (in-memory) REST API on the backend,
a single static HTML/CSS/JS page on the frontend.

```
srms/
├── pom.xml
├── src/main/java/com/example/srms/
│   ├── SrmsApplication.java        # main() entry point
│   ├── model/Student.java          # JPA entity + validation annotations
│   ├── repository/StudentRepository.java   # Spring Data JPA interface
│   ├── service/StudentService.java # business logic (uniqueness checks, etc.)
│   ├── controller/StudentController.java   # REST endpoints
│   ├── exception/                  # custom exceptions + @RestControllerAdvice
│   └── config/
│       ├── CorsConfig.java         # allows the static frontend to call the API
│       └── DataSeeder.java         # seeds 8 sample students on startup
├── src/main/resources/application.properties
└── frontend/index.html             # the whole frontend (HTML+CSS+JS, Bootstrap via CDN)
```

## Prerequisites

- Java 17+ (`java -version`)
- Maven 3.6+ (`mvn -version`) — or use your IDE's built-in Maven support

No database installation needed: the app uses H2 in-memory by default, so
data resets every time you restart the app (which also means it re-seeds
the 8 sample students automatically). See `application.properties` for a
commented-out MySQL configuration if you want persistent storage instead.

## Running the backend

From the `srms/` directory:

```bash
mvn spring-boot:run
```

Or build a jar and run it directly:

```bash
mvn clean package
java -jar target/srms.jar
```

The API will be live at **http://localhost:8080/api/students**.
The H2 web console (to poke at the data directly) is at
**http://localhost:8080/h2-console** — JDBC URL `jdbc:h2:mem:srmsdb`, user
`sa`, empty password.

### Quick API test

```bash
curl http://localhost:8080/api/students
curl http://localhost:8080/api/students/1
curl "http://localhost:8080/api/students/search?name=an"

curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","rollNumber":"R100","email":"test@example.com","department":"CS","year":2,"gpa":8.0}'

curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Aarav Sharma","rollNumber":"R001","email":"aarav@example.com","department":"CS","year":3,"gpa":9.0}'

curl -X DELETE http://localhost:8080/api/students/1
```

### API summary

| Method | Endpoint                        | Description                     | Success | Errors           |
|--------|----------------------------------|----------------------------------|---------|-------------------|
| GET    | `/api/students`                 | List all students               | 200     | —                 |
| GET    | `/api/students/{id}`            | Get one student                 | 200     | 404               |
| GET    | `/api/students/search?name=`    | Search by name (contains, case-insensitive) | 200 | — |
| POST   | `/api/students`                 | Create a student                | 201     | 400 (validation/duplicate roll number) |
| PUT    | `/api/students/{id}`            | Update a student                | 200     | 404, 400          |
| DELETE | `/api/students/{id}`            | Delete a student                | 204     | 404               |

Validation rules (enforced via Bean Validation on the `Student` entity):
name, rollNumber, email, department required; email must be a valid format;
year between 1–6; GPA between 0–10; rollNumber must be unique.

## Running the frontend

The frontend is a single static file — no build step, no server required.

**Option A — just open it:**
Double-click `frontend/index.html` (or open it via `File > Open` in your
browser). It's hardcoded to call the backend at `http://localhost:8080`,
and CORS is configured on the backend to accept requests from `file://`
origins, so this works out of the box.

**Option B — serve it (optional, e.g. VS Code "Live Server"):**
```bash
cd frontend
python3 -m http.server 5500
# then open http://localhost:5500
```
This works too since the backend's CORS config allows any origin.

Make sure the backend (`mvn spring-boot:run`) is running first, then load
the page — the student table populates automatically via `GET /api/students`.

## Notes / things you may want to change for production

- `spring.jpa.hibernate.ddl-auto=update` and the wide-open CORS policy
  (`allowedOriginPatterns("*")`) are convenient for local development but
  are not appropriate for production — lock CORS down to your actual
  frontend origin and use proper migrations (e.g. Flyway) instead of
  `ddl-auto`.
- To switch to MySQL: uncomment the MySQL block in `application.properties`,
  comment out the H2 block, and add the `mysql-connector-j` dependency to
  `pom.xml`.
