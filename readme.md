# Tenpo Challenge - Backend API

Este proyecto es una API REST desarrollada en Spring Boot 3 (Java 21) como parte del challenge técnico de backend.  

---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Swagger (OpenAPI 3)
- JUnit 5 + Mockito

---

## 📦 Levantar el proyecto con Docker

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

### 1. Clonar el repositorio

```bash
git clone https://github.com/tuusuario/tenpo-challenge.git
cd tenpo-challenge
```

### 2. Levantar el contenedor

```bash
docker-compose up -d
```
Esto levantará los siguientes servicios:
- **PostgreSQL**: Base de datos.

### 3. Acceder a la API

La API estará disponible en `http://localhost:8080`.