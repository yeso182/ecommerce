# Prices Service

REST service that returns the applicable price for a product and brand at a given point in time.
When multiple price entries overlap the one with the **highest priority** is returned.

## Architecture

The project follows **Hexagonal Architecture** (Ports & Adapters):

```
com.arqdem.ecommerce
├── domain/                     Pure domain — no framework dependencies
│   ├── model/Price.java        Immutable value object (record)
│   └── exception/
├── application/                Use cases and port definitions
│   ├── port/in/                Input ports (driven by the outside world)
│   ├── port/out/               Output ports (driven by the application)
│   └── service/PriceService    Single use-case implementation
└── infrastructure/             Framework-specific adapters
    ├── adapter/in/web/         REST adapter — drives the application
    └── adapter/out/persistence JPA adapter — driven by the application
```

## Tech stack

| Concern       | Choice                           |
|---------------|----------------------------------|
| Framework     | Spring Boot 4                    |
| Persistence   | Spring Data JPA + H2 (in-memory) |
| API docs      | springdoc-openapi (Swagger UI)   |
| Build         | Gradle                           |
| Java          | 21                               |

## Running the application

```bash
./gradlew bootRun
```

Once started:

| Resource     | URL                                      |
|--------------|------------------------------------------|
| Swagger UI   | http://localhost:8080/swagger-ui.html    |
| OpenAPI JSON | http://localhost:8080/api-docs           |
| H2 Console   | http://localhost:8080/h2-console         |

## API

### GET /api/v1/prices

| Parameter         | Type            | Required | Description                        |
|-------------------|-----------------|----------|------------------------------------|
| `applicationDate` | ISO 8601 string | yes      | Point in time to evaluate          |
| `productId`       | Long            | yes      | Product identifier                 |
| `brandId`         | Integer         | yes      | Brand identifier                   |

**Example request**

```
GET /api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
```

**Example response (200)**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

**Error responses**

| Status | Condition                                      |
|--------|------------------------------------------------|
| 400    | Missing or malformed query parameter           |
| 404    | No price found for the given product/brand/date|

## Running tests

```bash
./gradlew test
```

The test suite includes:

- **Unit tests** (`PriceServiceTest`) — service layer in isolation via Mockito
- **Integration tests** (`PriceControllerIntegrationTest`) — full Spring context with H2, covering the five scenarios from the problem statement:

| Test | Date              | Product | Brand | Expected price list | Expected price |
|------|-------------------|---------|-------|---------------------|----------------|
| 1    | 2020-06-14 10:00  | 35455   | 1     | 1                   | 35.50 EUR      |
| 2    | 2020-06-14 16:00  | 35455   | 1     | 2                   | 25.45 EUR      |
| 3    | 2020-06-14 21:00  | 35455   | 1     | 1                   | 35.50 EUR      |
| 4    | 2020-06-15 10:00  | 35455   | 1     | 3                   | 30.50 EUR      |
| 5    | 2020-06-16 21:00  | 35455   | 1     | 4                   | 38.95 EUR      |
