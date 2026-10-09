# Diabetic Retinopathy Screening — Backend

Spring Boot backend for the **Explainable AI for Diabetic Retinopathy Screening in Rural India** project. This service manages patient records, communicates with the AI screening service, stores screening results in PostgreSQL, and generates screening reports in PDF format.

## Features

- **Patient Management:** Register, retrieve, and delete patient records.
- **Screening Management:** Store and retrieve retinal screening results and screening history.
- **AI Integration:** Send retinal images to the FastAPI-based AI service for analysis.
- **PostgreSQL Database:** Persist patient details and screening records.
- **PDF Reports:** Generate downloadable PDF reports for screening results.
- **REST APIs:** Provide HTTP endpoints for frontend integration.
- **Docker Support:** Containerize the Spring Boot backend.

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- OpenPDF
- Docker

## Architecture

```text
Frontend
   |
   v
Spring Boot Backend :8084
   |
   +---- PostgreSQL :5432
   |
   +---- FastAPI AI Service :8000
              |
              v
       Retinal Image Analysis
```

The AI service performs image validation, image-quality checks, diabetic retinopathy grading, referable-DR classification, and Grad-CAM generation.

## Prerequisites

For local development:

- Java 21
- Maven (or the Maven Wrapper included in the project)
- PostgreSQL
- Python 3.11 for the separate AI service

For Docker-based execution:

- Docker Desktop

## Configuration

Configure the following environment variables for the backend:

| Variable | Description | Example |
|---|---|---|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/dr_screening` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | Set your own value |
| `SERVER_PORT` | Backend port | `8084` |

**Important:** Do not commit database passwords, API keys, or other secrets to GitHub.

For local development, the database URL can use `localhost`. When the backend runs in Docker alongside PostgreSQL, use the PostgreSQL container or service name, for example:

`jdbc:postgresql://postgres-db:5432/dr_screening`

When running the backend in Docker and the AI service directly on Windows, configure the AI service URL as:

`http://host.docker.internal:8000/analyze`

The AI service URL must match the configuration used by the backend's AI integration.

## Run Locally

### 1. Start PostgreSQL

Create a database named:

`dr_screening`

Configure the database credentials in your local environment.

### 2. Configure the backend

Set the datasource URL, username, password, and server port.

### 3. Start the Spring Boot application

Using the Maven Wrapper on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```powershell
mvn spring-boot:run
```

The backend should be available at:

`http://localhost:8084`

## Run with Docker

Build the Docker image from the repository root:

```powershell
docker build -t backend .
```

Create the shared Docker network if it does not already exist:

```powershell
docker network create dr-project
```

Start the backend:

```powershell
docker run -d --name backend --network dr-project -p 8084:8084 backend
```

If PostgreSQL is running in a separate container, ensure it is also connected to `dr-project` and that the datasource URL uses its container name.

**Note:** The backend requires a reachable PostgreSQL database and a reachable FastAPI service for image-analysis requests.

## API Modules

The backend includes API functionality for:

- Patient registration and retrieval
- Patient deletion and screening-history management
- Retinal image analysis through the AI service
- Screening result persistence and retrieval
- PDF screening report generation

The exact endpoints and request/response formats should be verified against the current controller implementations.

## PDF Reports

Screening reports can be requested using:

`GET /api/reports/screening/{screeningId}`

Replace `{screeningId}` with the ID of the screening record. The endpoint returns a PDF report when the requested record is available.

## Medical Disclaimer

This project is an AI-assisted screening prototype for educational and demonstration purposes. Its model outputs and Grad-CAM visualizations are not a substitute for professional ophthalmic evaluation. The system has not been clinically validated for real-world diagnosis.

## Project Status

This repository contains the Spring Boot backend for the diabetic retinopathy screening prototype. Production deployment requires appropriate security controls, clinical validation, privacy safeguards, and operational testing.