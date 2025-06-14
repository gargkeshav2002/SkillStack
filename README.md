# 🛠️ SkillTracker

A secure, extensible Spring Boot backend for managing users and their skills with JWT authentication, role-based access control, and modular architecture.

## 🚀 Features

- ✅ **User-Skill CRUD APIs** – Users can create, view, update, and delete their own skills.
- 🔐 **JWT Authentication & Refresh Token Flow** – Secure sessions with token renewal support.
- 🧑‍💻 **Role-Based Authorization** – Admins have full access; users manage their own data.
- 🧱 **Layered Architecture** – Entity-DTO separation using **MapStruct**, clean service/repo layers.
- 📊 **API Documentation** – Integrated **Swagger/OpenAPI** for seamless testing and visibility.
- 🧪 **Unit & Integration Testing** – JUnit, Mockito used for robust backend verification.
- 📜 **Structured Logging** – Configured with `logback.xml` for actionable logs.
- 🧰 **Exception Handling** – Centralized error management with custom exceptions.

## 📦 Tech Stack

- **Java 17**, **Spring Boot**
- **Spring Security**, **JWT**, **RBAC**
- **PostgreSQL**, **JPA/Hibernate**
- **MapStruct**, **Swagger/OpenAPI**
- **JUnit**, **Mockito**, **Lombok**
- **Maven**, **Logback**
