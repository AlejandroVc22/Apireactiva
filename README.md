# API Reactiva de Productos (WebFlux + MySQL)

API REST reactiva con arquitectura en capas, usando R2DBC para MySQL.

## Requisitos

- Java 17+
- Docker Desktop (para ejecutar con MySQL en contenedores)

## Configuracion

La configuracion esta en `src/main/resources/application.yaml`.

- Perfil `default`: apunta a `localhost:3306`.
- Perfil `docker`: apunta al servicio `mysql` del `docker-compose.yml`.

## Endpoints

Base URL: `http://localhost:8080/api/products`

- `GET /api/products` lista todos
- `GET /api/products/{id}` buscar por id
- `GET /api/products/search?name=abc` buscar por nombre
- `POST /api/products` crear
- `PUT /api/products/{id}` actualizar
- `DELETE /api/products/{id}` eliminar

Ejemplo body para crear/actualizar:

```json
{
  "name": "Teclado",
  "description": "Mecanico",
  "price": 150.50
}
```

## Levantar con Docker (paso a paso)

1) Construir y levantar

```zsh
docker compose up --build
```

2) Probar un endpoint

```zsh
curl http://localhost:8080/api/products
```

3) Detener servicios

```zsh
docker compose down
```

4) (Opcional) Borrar tambien el volumen de datos

```zsh
docker compose down -v
```

## Levantar local sin Docker (paso a paso)

1) Asegura MySQL corriendo en `localhost:3306` con:

- DB: `apireactiva`
- Usuario: `root`
- Password: `root`

2) Ejecutar la app

```zsh
./mvnw spring-boot:run
```

3) Probar un endpoint

```zsh
curl http://localhost:8080/api/products
```

## Notas

- El esquema se crea automaticamente desde `src/main/resources/schema.sql`.

