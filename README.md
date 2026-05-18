# Task Manager API

## Tabla de contenido
- [Descripción del proyecto](#descripción-del-proyecto)
- [Stack tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)
- [Requisitos para correr la API](#requisitos-para-correr-la-api)
- [Configuración](#configuración)
- [Cómo levantar el proyecto](#cómo-levantar-el-proyecto)
- [Documentación de la API](#documentación-de-la-api)
- [Cómo correr los tests](#cómo-correr-los-tests)

## Descripción del proyecto

API REST para la administración de tareas personales con autenticación JWT.

## Stack Tecnológico

La API usa las siguientes tecnologías:

- Java 21
- Spring Boot 4
- PostgreSQL
- Docker
- JWT
- Swagger
- Springdoc OpenAPI
- JUnit 5 + Mockito

## Arquitectura

El proyecto está construido bajo **Arquitectura Limpia**, organizada en cuatro capas:

- **domain**: contiene los modelos, repositorios e interfaces del negocio. No depende de ningún framework ni implementación externa.
- **application**: contiene los casos de uso que orquestan la lógica del negocio.
- **infrastructure**: contiene las implementaciones concretas: persistencia con Spring Data JPA y PostgreSQL, y encriptación de contraseñas con BCrypt.
- **web**: contiene los controladores REST, configuración de seguridad con Spring Security y documentación con Swagger.

Los detalles de implementación apuntan hacia el dominio, no al revés, manteniendo el núcleo del negocio independiente de cualquier framework.

## Requisitos para correr la API

- Docker
- Docker Compose

> Docker Desktop incluye ambos servicios

## Configuración

Dentro de la carpeta `deployment/` encontrarás los archivos `.env.db.example` y `.env.app.example`. Crea una copia de cada uno sin el `.example` y completa los valores:

- `DB_URL`: URL de conexión a PostgreSQL. Ejemplo: `jdbc:postgresql://db:5432/taskmanager`
- `DB_USERNAME` / `DB_PASSWORD`: credenciales de la base de datos
- `JWT_SECRET`: string codificado en Base64 de al menos 32 bytes. Puedes generarlo con: `openssl rand -base64 64`
- `JWT_EXPIRATION`: tiempo de expiración del token en milisegundos. Ejemplo: `86400000` (24 horas)

## Cómo levantar el proyecto

1. Clona el repositorio
2. Configura los archivos `.env` según la sección anterior
3. Desde la carpeta `deployment/`, ejecuta:

```bash
docker compose up --build
```

La API quedará disponible en `http://localhost:80`

## Documentación de la API

La API está documentada con Swagger UI. Una vez levantado el proyecto, puedes acceder en:

`http://localhost:80/swagger-ui.html`

Para probar los endpoints protegidos, primero inicia sesión en `/api/auth/sign-in`, copia el token retornado y pégalo en el candado de Swagger **sin** el prefijo `Bearer `.

## Cómo correr los tests

Desde la raíz del proyecto:

```bash
./gradlew test
```