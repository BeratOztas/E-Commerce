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

http
POST /auth/register
POST /auth/login
POST /auth/refresh
POST /auth/logout
 
## 📦 Setup
1.Clone the repo:

bash
Kopyala
Düzenle
git clone  https://github.com/BeratOztas/E-Commerce.git
cd E-Commerce


2.Setup application.properties:

properties
Kopyala
Düzenle
spring.datasource.url=jdbc:postgresql://localhost:5432/e_commerce
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASS

spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD

spring.data.redis.host=localhost
spring.rabbitmq.host=localhost

3.Run the app:

./mvnw spring-boot:run

## 🧠 Architecture Highlights


src/
├── controller
├── dto
├── entity
├── service / serviceimpl
├── repository
├── config
├── security
├── message
└── util

## 👨‍💻 Author
Berat Öztaş
GitHub: @BeratOztas
LinkedIn: linkedin.com/in/beratoztas

## 🚀 What's Next?

Frontend with React (in progress)

Dockerize Full Stack

CI/CD Pipeline (GitHub Actions or Jenkins)

If you like this project, feel free to ⭐ star it and follow me on LinkedIn!