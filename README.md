
# Blackjack Reactive API – Dockerized Version

## 📄 Description
This project is a reactive Blackjack game API built with **Spring Boot WebFlux**, **R2DBC (MySQL)**, and **MongoDB Reactive**.  
The goal of the exercise is to implement a fully reactive API, integrate two databases, implement exception handling, write tests, and finally **dockerize the entire application**.

---

## 💻 Technologies Used
- Java 21
- Spring Boot 3 (WebFlux, R2DBC, Reactive MongoDB)
- MySQL 8 (Docker)
- MongoDB Atlas
- Docker & Docker Compose
- Maven
- Swagger / OpenAPI
- JUnit 5 & Mockito
- Lombok

---

## 📋 Requirements
To run the project locally you must have:

- **Java 21**
- **Maven 3.9+**
- **Docker & Docker Compose**
- A **MySQL server** (local via XAMPP or Docker)
- A **MongoDB connection string** (Atlas or local)

---

## 🛠️ Installation

Clone the project:

```
git clone https://github.com/luriguso/TaskS501AdvancedSpringFrameworkWithWebFlux.git
cd TaskS501AdvancedSpringFrameworkWithWebFlux
```

Build the application:

```
mvn clean package -DskipTests
```

---

## ▶️ Running the Project Locally

Use your local MySQL (XAMPP):

1. Start MySQL
2. Create database manually:
```
CREATE DATABASE blackjack;
```
3. Run the Spring Boot app:
```
mvn spring-boot:run
```

---

## 🌐 Deployment with Docker

### Build the Docker image:

```
docker build -t luriguso/s05t01n01:latest .
```

### Upload to Docker Hub:

```
docker login
docker push luriguso/s05t01n01:latest
```

### Run using Docker Compose:

```
docker compose up --build
```

This will:
✅ Start MySQL in Docker  
✅ Start the Blackjack API  
✅ Automatically create the `player` table (via init.sql)  
✅ Expose the API at port 8080

---

## 🤝 Contributing
Pull requests are welcome.  
Please open an issue first to discuss changes before submitting PRs.

