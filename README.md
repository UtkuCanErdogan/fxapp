# 💱 FXApp – Foreign Exchange Application

FXApp is an application that performs currency conversion operations between different currencies. It supports bulk conversion through CSV file uploads and is built using Spring Boot with Clean Architecture principles.

# 🚀 Features

- Real-time currency conversion
- Bulk conversion using CSV file uploads
- Retrieve conversion history with filters

# 🛠️ Technologies

- Java 17
- Spring Boot 3.3
- Docker
- PostgresSQL
- Redis

# ⚙️ Getting Started

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (if running with containers)

## Run with Docker Compose

Make sure Docker is running, then execute:
`docker-compose up --build`

## Run Locally
Before running locally, make sure that all dependencies defined in the docker-compose.yml (e.g., PostgreSQL, Redis) are up and running. You can do this by starting them separately using Docker Compose:
`docker-compose up -d postgres redis`

If you want to run it manually without Docker:
`./mvnw clean install`
`java -jar target/fxapp-0.0.1-SNAPSHOT.jar`

Make sure your PostgreSQL and Redis services are up and configured as expected in application.yml.

# 📄 API Documentation

Swagger UI: `http://yourhost:yourtport/swagger-ui.html`

# 🔮 Testing
The project includes unit and integration tests. Test environments (PostgreSQL, Redis) are automatically spun up inside Docker containers using Testcontainers.

`./mvnw test`
