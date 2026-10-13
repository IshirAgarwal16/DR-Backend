# Diabetic Retinopathy Screening — Backend

This repository contains the Spring Boot backend for our **Explainable AI for Diabetic Retinopathy Screening in Rural India** project.

The backend connects the frontend with the AI service and PostgreSQL database. It handles patient records, stores screening results, and generates PDF reports that can be used to review screening outcomes.

## What This Backend Does

- **Patient Management:** Register patients and view or delete their records.
- **Screening Records:** Save screening results and retrieve previous screenings.
- **AI Integration:** Send retinal images to the FastAPI service for analysis.
- **Database:** Store patient details and screening results in PostgreSQL.
- **PDF Reports:** Generate reports for individual screening records.
- **REST APIs:** Allow the frontend to communicate with the backend.

## Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA and Hibernate
- PostgreSQL
- Maven
- OpenPDF

## How the Project Works

```text
Frontend
   |
   v
Spring Boot Backend (Port 8084)
   |
   +---- PostgreSQL (Port 5432)
   |
   +---- FastAPI AI Service (Port 8000)
                  |
                  v
           Retinal Image Analysis
```

When a user submits a retinal image, the backend communicates with the AI service to get the analysis results. It then manages the screening record and stores the relevant information in PostgreSQL.

The AI service handles image validation, image-quality checks, DR severity classification, referable-DR assessment, and Grad-CAM visualization.

## Prerequisites

Before running the project, make sure you have:

- Java 21
- Maven or the Maven Wrapper included in the project
- PostgreSQL
- Python 3.11 for the separate AI service

The backend, AI service, and database can be run locally without Docker.

## Configuration

The backend needs a PostgreSQL connection and a URL for the AI service.

For local development, the database URL should be:

```text
jdbc:postgresql://localhost:5432/dr_screening
```

The AI service URL should be:

```text
http://localhost:8000/analyze
```

The backend runs on port `8084` by default.

Database credentials should be configured locally using environment variables or an appropriate local configuration file. Avoid committing passwords, API keys, or other secrets to GitHub.

Make sure the URLs match the configuration used by the application.

## Running the Backend Locally

### 1. Start PostgreSQL

Make sure PostgreSQL is running and that a database named `dr_screening` exists.

Configure the database username and password in your local environment.

### 2. Start the Spring Boot Backend

Open PowerShell in the backend project directory:

```powershell
cd C:\Ishircode\DR-Backend\drbackend
```

If the project includes the Maven Wrapper, run:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```powershell
mvn spring-boot:run
```

Once the application starts successfully, the backend should be available at:

`http://localhost:8084`

### 3. Start the AI Service

Open another PowerShell terminal and navigate to the AI project:

```powershell
cd C:\Ishircode\DR-Screening
```

Start the FastAPI service:

```powershell
.\venv\Scripts\python.exe -m uvicorn ai_api:app --host 127.0.0.1 --port 8000
```

You can check whether the service is running by opening:

- Health check: `http://localhost:8000/health`
- API documentation: `http://localhost:8000/docs`

Keep both the backend and AI service running while testing the application.

### 4. Connect the Frontend

Open the frontend using your local development setup.

Make sure its API requests point to the Spring Boot backend at `http://localhost:8084`. If the frontend runs on a different origin, the backend may need an appropriate CORS configuration.

## Service Ports

| Service | Port |
|---|---:|
| Spring Boot Backend | 8084 |
| FastAPI AI Service | 8000 |
| PostgreSQL | 5432 |

These are the default ports used by the current local setup.

## API Functionality

The backend provides functionality for:

- Registering and retrieving patients
- Managing screening records and history
- Sending retinal images to the AI service
- Saving and retrieving screening results
- Generating PDF screening reports

For the exact API routes and request formats, refer to the controller classes in the source code.

## PDF Screening Reports

The report endpoint is:

`GET /api/reports/screening/{screeningId}`

Replace `{screeningId}` with the ID of the screening record you want to retrieve.

A PDF report is returned when the requested record is available and the endpoint completes successfully.

## Security and Patient Data

Since this project handles patient information, data protection is important.

- Keep database passwords and API keys out of the repository.
- Avoid uploading real patient images or identifiable patient information to a public repository.
- Validate uploaded files and API inputs.
- Add suitable authentication and authorization before using the application with real patient records.
- Protect screening reports and patient data from unauthorized access.

## Limitations

This project is an educational and development prototype. It has not been clinically validated for real-world diagnosis.

AI predictions and Grad-CAM visualizations should not be treated as a replacement for evaluation by a qualified eye-care professional. Further testing, clinical validation, and appropriate privacy and security measures are needed before real-world clinical use.

## Project Status

The backend is part of an ongoing diabetic retinopathy screening project that brings together a web interface, an AI-based analysis service, and a PostgreSQL database.

The current setup is intended for local development and project demonstrations. Further testing and improvements are needed before production deployment.
