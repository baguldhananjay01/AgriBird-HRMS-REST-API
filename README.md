# 🌱 AgriBird HRMS REST API

### Human Resource Management System — Backend REST API

AgriBird HRMS REST API is a backend application developed using **Java and Spring Boot** for the AgriBird Human Resource Management System.

The backend provides REST APIs for the AgriBird HRMS Android application and manages employee information, attendance, leave management, leave balances, authentication, security, and database operations.

---

## 📱 Related Android Application

This REST API is designed to work with the **AgriBird HRMS Android Application**.

### Android Repository

🔗 **AgriBird HRMS Android**

https://github.com/baguldhananjay01/AgriBird-HRMS-Android

---

# ✨ Features

The AgriBird HRMS REST API provides the following features:

- 🔐 User Authentication
- 🔑 JWT-based Authentication
- 🔒 Password Encryption
- 👤 Employee Management
- ⏱️ Attendance Management
- 🏖️ Leave Management
- 📊 Leave Balance Management
- 📍 Attendance Location Support
- 🗄️ MySQL Database Integration
- 🔄 REST API Communication
- 📦 JSON Data Exchange
- ❤️ Database Health Check
- 🛡️ Spring Security Integration
- 🧩 Layered Backend Architecture

---

# 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java** | Backend Programming Language |
| **Spring Boot** | Backend Framework |
| **Spring Web** | REST API Development |
| **Spring Data JPA** | Database Operations |
| **Hibernate** | ORM / Database Mapping |
| **MySQL** | Relational Database |
| **Maven** | Dependency & Build Management |
| **Spring Security** | Application Security |
| **JWT** | Authentication |
| **JSON** | API Data Exchange |
| **Postman** | API Testing |
| **Git & GitHub** | Version Control |

---

# 🏗️ Project Architecture

The application follows a **layered architecture** to keep the backend organized, maintainable, and scalable.

```text
                    ┌──────────────────────────┐
                    │   Android HRMS App       │
                    └────────────┬─────────────┘
                                 │
                                 │ HTTP / JSON
                                 ▼
                    ┌──────────────────────────┐
                    │   REST Controller Layer  │
                    │                          │
                    │ AttendanceController     │
                    │ EmployeeController       │
                    │ LeavesController         │
                    │ LeaveBalanceController   │
                    │ DatabaseHealthController │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │      Service Layer       │
                    │                          │
                    │ Business Logic           │
                    │                          │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │    Repository Layer      │
                    │                          │
                    │ Spring Data JPA          │
                    │                          │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │     Hibernate / JPA      │
                    └────────────┬─────────────┘
                                 │
                                 ▼
                    ┌──────────────────────────┐
                    │      MySQL Database      │
                    └──────────────────────────┘
```

---

# 📂 Project Structure

```text
AgriBird-HRMS-REST-API/
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   │
│   │   │   └── com/
│   │   │       │
│   │   │       └── agribird_hrms/
│   │   │           │
│   │   │           ├── config/
│   │   │           │   ├── PasswordConfig.java
│   │   │           │   └── SecurityConfig.java
│   │   │           │
│   │   │           ├── controller/
│   │   │           │   ├── AttendanceController.java
│   │   │           │   ├── DatabaseHealthController.java
│   │   │           │   ├── EmployeeController.java
│   │   │           │   ├── LeaveBalanceController.java
│   │   │           │   └── LeavesController.java
│   │   │           │
│   │   │           ├── dto/
│   │   │           │   ├── ApiResponse.java
│   │   │           │   └── LoginResponse.java
│   │   │           │
│   │   │           ├── entity/
│   │   │           │   ├── Attendance.java
│   │   │           │   ├── Employee.java
│   │   │           │   ├── LeaveBalance.java
│   │   │           │   ├── User.java
│   │   │           │   └── leaves.java
│   │   │           │
│   │   │           ├── Repository/
│   │   │           │   ├── AttendanceRepository.java
│   │   │           │   ├── EmployeeRepository.java
│   │   │           │   ├── LeaveBalanceRepository.java
│   │   │           │   └── LeavesRepository.java
│   │   │           │
│   │   │           ├── security/
│   │   │           │   ├── JwtAuthenticationFilter.java
│   │   │           │   └── JwtService.java
│   │   │           │
│   │   │           ├── service/
│   │   │           │   ├── AttendanceService.java
│   │   │           │   ├── AttendanceServiceImpl.java
│   │   │           │   ├── EmployeeService.java
│   │   │           │   ├── LeaveBalanceService.java
│   │   │           │   ├── LeaveBalanceServiceImpl.java
│   │   │           │   ├── LeavesService.java
│   │   │           │   └── LeavesServiceImpl.java
│   │   │           │
│   │   │           └── AgribirdHrmsBackendApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── .gitattributes
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

# 👤 Employee Management

The Employee Management module handles employee-related information and operations.

### Components

```text
EmployeeController
EmployeeService
EmployeeRepository
Employee
```

### Responsibilities

- Employee information management
- Employee data retrieval
- Employee data modification
- Employee database operations

---

# ⏱️ Attendance Management

The Attendance Management module handles employee attendance-related operations.

### Components

```text
AttendanceController
AttendanceService
AttendanceServiceImpl
AttendanceRepository
Attendance
```

### Responsibilities

- Attendance records
- Attendance-related API operations
- Employee attendance data
- Attendance database operations
- Attendance location support

---

# 🏖️ Leave Management

The Leave Management module handles employee leave-related operations.

### Components

```text
LeavesController
LeavesService
LeavesServiceImpl
LeavesRepository
leaves
```

### Responsibilities

- Apply for leave
- Manage leave records
- Retrieve leave information
- Update leave information
- Delete leave records
- Leave-related database operations

---

# 📊 Leave Balance Management

The Leave Balance module manages employee leave balances.

### Components

```text
LeaveBalanceController
LeaveBalanceService
LeaveBalanceServiceImpl
LeaveBalanceRepository
LeaveBalance
```

### Responsibilities

- Employee leave balance management
- Leave balance retrieval
- Leave balance updates
- Database operations for leave balances

---

# 🔐 Authentication & Security

The project implements authentication and security using **Spring Security and JWT**.

### Security Components

```text
SecurityConfig.java
PasswordConfig.java
JwtService.java
JwtAuthenticationFilter.java
```

### Security Features

- User authentication
- JWT token generation
- JWT token validation
- JWT authentication filter
- Password encryption
- Protected API requests
- Security configuration

---

# 🔑 JWT Authentication Flow

The authentication flow works approximately as follows:

```text
Android Application
        │
        │ Login Request
        ▼
Spring Boot REST API
        │
        ▼
Authentication
        │
        ▼
Validate User Credentials
        │
        ▼
Generate JWT Token
        │
        ▼
Return Token
        │
        ▼
Android Application
        │
        │ JWT Token
        ▼
Protected API Request
        │
        ▼
JwtAuthenticationFilter
        │
        ▼
Validate JWT
        │
        ▼
Allow Request
```

---

# 🗄️ Database

The application uses **MySQL** as its relational database.

### Database Technologies

- MySQL
- Spring Data JPA
- Hibernate

Hibernate is used as the ORM layer to map Java entities with database tables.

### Database Configuration

The application uses the following configuration:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/agribird_hrms?createDatabaseIfNotExist=true

spring.datasource.username=${DB_USERNAME}

spring.datasource.password=${DB_PASSWORD}
```

---

# 🔐 Environment Variables

Sensitive database credentials are **not stored directly in the source code**.

The application uses environment variables.

### Required Environment Variables

```text
DB_USERNAME
DB_PASSWORD
```

### Example

```text
DB_USERNAME=root
DB_PASSWORD=YOUR_DATABASE_PASSWORD
```

> ⚠️ Never commit your actual database password to GitHub.

---

# ⚙️ Application Configuration

The application uses Spring Boot configuration through:

```text
src/main/resources/application.properties
```

### Server Port

```properties
server.port=1010
```

Therefore, the local server runs at:

```text
http://localhost:1010
```

### Hibernate Configuration

```properties
spring.jpa.hibernate.ddl-auto=update
```

### SQL Logging

```properties
spring.jpa.show-sql=true
```

### Hibernate Dialect

```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

# 📡 REST API

The backend communicates with the Android application using HTTP requests.

### Supported HTTP Methods

```text
GET
POST
PUT
DELETE
```

### Data Format

Requests and responses use:

```text
JSON
```

### Base URL

When running locally:

```text
http://localhost:1010
```

The available API endpoints are implemented inside the controller classes.

```text
src/main/java/com/agribird_hrms/controller/
```

---

# 📋 API Modules

The REST API contains controllers for the following modules:

```text
AttendanceController
EmployeeController
LeaveBalanceController
LeavesController
DatabaseHealthController
```

These controllers expose REST endpoints used by the Android application.

---

# ❤️ Database Health Check

The project contains a database health controller:

```text
DatabaseHealthController.java
```

This functionality is used to check database connectivity and backend database health.

---

# 📱 Android Integration

The AgriBird HRMS Android application communicates with this Spring Boot REST API.

### Communication Flow

```text
┌───────────────────────────┐
│   AgriBird HRMS Android   │
│        Application        │
└─────────────┬─────────────┘
              │
              │ HTTP / JSON
              ▼
┌───────────────────────────┐
│   Spring Boot REST API    │
│                           │
│   Controllers             │
│   Services                │
│   Repositories            │
│   Security                │
└─────────────┬─────────────┘
              │
              │ JPA / Hibernate
              ▼
┌───────────────────────────┐
│      MySQL Database       │
└───────────────────────────┘
```

---

# 🧪 API Testing

The REST API can be tested using **Postman**.

Postman can be used to test:

- Authentication APIs
- Employee APIs
- Attendance APIs
- Leave APIs
- Leave Balance APIs
- Database Health APIs

### Base URL

```text
http://localhost:1010
```

The exact API endpoints can be found in the controller classes.

---

# 🧪 Testing

The project contains Spring Boot test files inside:

```text
src/test/
```

Tests can be executed using Maven.

```bash
mvn test
```

---

# 📦 Maven

The project uses **Apache Maven** for dependency management and project building.

### Install Dependencies

```bash
mvn clean install
```

### Run Tests

```bash
mvn test
```

### Build Project

```bash
mvn clean package
```

### Run Application

```bash
mvn spring-boot:run
```

---

# 🚀 Getting Started

## Prerequisites

Before running the project, install:

- ☕ Java JDK
- 📦 Maven
- 🗄️ MySQL
- 🔧 Git
- 🧪 Postman
- 💻 VS Code / IntelliJ IDEA / Eclipse

---

## 1️⃣ Clone Repository

```bash
git clone https://github.com/baguldhananjay01/AgriBird-HRMS-REST-API.git
```

---

## 2️⃣ Navigate to Project

```bash
cd AgriBird-HRMS-REST-API
```

---

## 3️⃣ Configure MySQL

Make sure MySQL Server is running.

The application uses:

```text
Database: agribird_hrms
```

Configure your database credentials using environment variables:

```text
DB_USERNAME
DB_PASSWORD
```

Example:

```text
DB_USERNAME=root
DB_PASSWORD=YOUR_DATABASE_PASSWORD
```

---

## 4️⃣ Start the Application

Run:

```bash
mvn spring-boot:run
```

Or run the main application class:

```text
AgribirdHrmsBackendApplication.java
```

from your IDE.

---

## 5️⃣ Access the Backend

After successful startup:

```text
http://localhost:1010
```

---

# 🔄 Application Workflow

The overall application workflow:

```text
        Android Application
                │
                ▼
         HTTP Request
                │
                ▼
        REST Controller
                │
                ▼
         Service Layer
                │
                ▼
       Repository Layer
                │
                ▼
        JPA / Hibernate
                │
                ▼
         MySQL Database
                │
                ▼
       JSON Response
                │
                ▼
        Android Application
```

---

# 🔒 Security Best Practices

The following sensitive information should never be committed to GitHub:

- ❌ Database Password
- ❌ JWT Secret
- ❌ API Keys
- ❌ Private Credentials
- ❌ Environment-specific Secrets
- ❌ Authentication Secrets

This project uses environment variables for sensitive database credentials.

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

---

# 📌 Project Status

🚧 **Under Development**

The AgriBird HRMS REST API is actively developed as the backend service for the AgriBird HRMS Android application.

---

# 🔮 Future Improvements

Possible future improvements include:

- 📧 Email Notifications
- 🔔 Push Notifications
- 📊 Advanced HR Reports
- 📈 Analytics Dashboard
- 🧑‍💼 Admin Management
- 📝 Audit Logs
- 🔐 Role-Based Access Control
- ☁️ Cloud Deployment
- 🐳 Docker Support
- 🚀 Production Deployment

---

# 📁 Repository Information

### Repository Name

```text
AgriBird-HRMS-REST-API
```

### Repository Type

```text
Spring Boot REST API
```

### Backend

```text
Java + Spring Boot
```

### Database

```text
MySQL
```

### API Communication

```text
REST + JSON
```

### Authentication

```text
JWT
```

---

# 👨‍💻 Developer

## Dhananjay Bagul

**Java | Spring Boot | Android Developer**

---

# 🌱 AgriBird HRMS

### Simplifying Employee Management Through Technology.

---

## 📄 License

This project is developed for **educational and professional project purposes**.