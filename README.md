# 🛒 E-Commerce Backend API

A complete e-commerce backend project built with **Spring Boot**, designed for real-world scalability and modularity.

## ⚙️ Technologies Used

- **Java 17**, **Spring Boot**
- **Spring Security** (JWT & Cookie-based auth)
- **PostgreSQL**
- **Redis** (Refresh Tokens)
- **RabbitMQ** (Event-driven messaging)
- **Jakarta Mail** (Email notifications)
- **Swagger / OpenAPI**

## ✅ Features

- JWT & Cookie-based Authentication
- CSRF Protection
- Role-Based Authorization (USER / ADMIN)
- Product & Category Management
- Shopping Cart (multi-cart support)
- Order Management (with status tracking)
- Pagination & Sorting (custom utility)
- Asynchronous messaging with RabbitMQ
- Email notification on order events
- Swagger UI for API testing

## 🧪 API Testing (Swagger)

Run the app and access:
> http://localhost:8080/swagger-ui.html

## 🔐 Auth Endpoints

```http
POST /auth/register
POST /auth/login
POST /auth/refresh
POST /auth/logout
 
