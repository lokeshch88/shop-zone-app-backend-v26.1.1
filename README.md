# 🏨 ShopZone App – Spring Boot Backend

This is a backend project for a Shopping App built using Spring Boot. It includes secure authentication using JWT, role-based access control, async email notifications, and REST APIs for managing orders.

## 🚀 Tech Stack
- **Java 8** – Core programming language  
- **Spring Boot** – Backend framework  
- **Spring Security with JWT** – Authentication & authorization  
- **Spring Data JPA** – ORM & database access  
- **jBPM Workflow Engine** – Workflow automation  
- **MySQL** – Relational database  
- **Maven** – Dependency management  
- **Redis** – In-memory caching for fast data access  
- **Spring Async** – For running background tasks and improving performance during long-running operations  
- **Global Exception Handling** – Clean error responses

## ✅ Features
- User registration & login with JWT authentication
- Multi merchant registration with unique slug
- Validation based on slug and merchant id users
- Role-based access (Admin/User)
- Async booking confirmation via email
- CRUD APIs for Order
- Global error handling
- Products CRUD operations

## 📁 Project Structure
- `controller` – REST API endpoints
- `service` – Business logic
- `repository` – JPA Repositories
- `entity` – Entity classes
- `config` – JWT & Security Config
- `exception` – Custom exceptions

## 🔐 Authentication
- JWT-based login
- Access token returned on login
- Role-based protected routes (e.g., only admin can delete orders)

## 🛠️ Setup Instructions

1. Clone the repo:
   git clone https://github.com/lokeshch88/shopzone-app-backend.git
2.
