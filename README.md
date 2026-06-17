# Finance Manager API

REST API for the Finance Manager app built with **Java 21 + Spring Boot 3.5 + PostgreSQL**.

## Features

- JWT authentication (register / login)
- Accounts management (checking, savings, credit card, etc.)
- Transactions with automatic balance updates
- Categories (system defaults + user-defined)

## Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL 15+

## Getting started

```bash
# 1. Create database
createdb finance_manager

# 2. Copy and fill env vars
cp .env.example .env

# 3. Run
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## API endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/auth/register` | ❌ | Register |
| POST | `/api/auth/login` | ❌ | Login |
| GET | `/api/accounts` | ✅ | List accounts |
| POST | `/api/accounts` | ✅ | Create account |
| DELETE | `/api/accounts/{id}` | ✅ | Delete account |
| GET | `/api/transactions` | ✅ | List transactions |
| POST | `/api/transactions` | ✅ | Create transaction |
| DELETE | `/api/transactions/{id}` | ✅ | Delete transaction |
| GET | `/api/categories` | ✅ | List categories |

## Docker

```bash
docker build -t finance-manager-api .
docker run -p 8080:8080 --env-file .env finance-manager-api
```
