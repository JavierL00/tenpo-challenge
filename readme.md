# Tenpo Challenge - API REST con Spring Boot

Este proyecto implementa una API REST utilizando **Spring Boot 3**, **Java 21**, **PostgreSQL**, y **Docker**, como parte de un challenge técnico.

---

## 🚀 Funcionalidades

### 1. 📈 Cálculo con Porcentaje Dinámico
- Endpoint `/calculate` que recibe `num1` y `num2`, realiza una suma y aplica un **porcentaje adicional** obtenido desde un servicio externo.
- El cálculo es validado y el resultado se entrega redondeado.

### 2. 🧠 Caché del Porcentaje
- El porcentaje obtenido se almacena en caché por **30 minutos** con **Caffeine**.
- Si el servicio externo falla y hay valor en caché, se usa ese valor.
- Si no hay valor en caché y el servicio externo falla, se lanza un error.

### 3. 🕒 Historial de Llamadas
- Cada request se guarda en la base de datos con:
  - Fecha
  - Endpoint
  - Parámetros
  - Respuesta o error
- Registro asíncrono con `@Async` y thread pool configurado.

### 4. ⚠️ Rate Limiting
- Se limita a **2 peticiones por IP por minuto**.
- Si se supera el límite, se lanza una repuesta (HTTP 429).

---

## 📦 Tecnologías Utilizadas

- Java 21
- Spring Boot 3
- Spring Cache (Caffeine)
- Spring Retry
- PostgreSQL (Docker)
- Swagger/OpenAPI
- Docker & Docker Compose
- JUnit + Mockito + JaCoCo

---

## 🔧 Configuración

### `application.properties` (local por defecto)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/challenge_db
spring.datasource.username=challenge_user
spring.datasource.password=challenge_pass
```

Puedes sobreescribir estas propiedades con variables de entorno (usado por Docker).

---

## 🐳 Docker

### Requisitos
- Java 21
- Docker y Docker Compose

### Construcción y ejecución (modo local)

```bash
./mvnw clean package -DskipTests
docker-compose up --build
```

Esto levantará:

- PostgreSQL en puerto `5432`
- API en puerto `8080`

---

## 🧪 Tests

Se incluyen pruebas unitarias para:
- Cálculo decimal preciso (`BigDecimalUtils`)
- Servicio de porcentaje externo con simulación de errores (`ExternalPercentageService`)
- Historial de llamadas
- Excepciones personalizadas

Cobertura medida con **JaCoCo**.

---

## 🔗 Documentación Swagger

Una vez levantado el contenedor:
- API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)