# ERP System API 🏢

A comprehensive **ERP System API** built with **Java Spring Boot**, designed to manage and streamline core business operations.  
The system is modular, secure, and scalable, providing enterprise-grade solutions for business management.  

---

## 🚀 Features

### 🔐 User Management
- Role-based access control (ADMIN / ACCOUNTANT / STORE_MANAGER / HR / SALES_EMPLOYEE / …).
- User profiles with authentication & authorization (JWT-based).
- Secure password handling & validation.

### 📦 Product & Category Management
- Create, update, delete, and manage products.
- Categories with parent-child hierarchy.
- Entity-Attribute-Value (EAV) support for product attributes.

### 💼 Business Modules
- **Sales Management**: Track sales, invoices, and customers.
- **Purchase Management**: Supplier & purchase order handling.
- **Payroll Management**: Salary, deductions, and bonuses.
- **Leave Requests**: Submit, approve, and track employee leaves.
- **Attendance Tracking**: Record and monitor employee attendance.

### 🛠️ System Essentials
- **Validation**: Strong data validation for all entities.
- **Logging & Monitoring**: Centralized logs for debugging and performance.
- **Exception Handling**: Custom error handling with meaningful responses.
- **Testing**: Unit & integration tests for reliability.
- **Caching**: Optimized performance using caching strategies.
- **Pagination & Filtering**: Efficient data handling in APIs.
- **Audit & Tracking**: Keep track of changes and user activity.

### 📡 Integrations
- **Mail Service**: For notifications and communication.
- **RabbitMQ**: Asynchronous messaging and event-driven processes.
- **Cloudinary**: Image & file storage solution.

---

## 🏗️ Tech Stack
- **Java 17**
- **Spring Boot (Web, Security, Data JPA, Validation)**
- **MySQL** (Relational Database)
- **Redis** (Caching)
- **RabbitMQ** (Message Broker)
- **Cloudinary API** (Media Management)
- **Maven** (Build Tool)

---

## 📂 Project Structure

src/
├── main/
│ ├── java/com/erp/
│ │ ├── config/ # Security & App Config
│ │ ├── controller/ # REST Controllers
│ │ ├── dto/ # Data Transfer Objects
│ │ ├── model/ # JPA Entities
│ │ ├── exception/ # Exception Handling
│ │ ├── repository/ # Spring Data JPA Repos
│ │ ├── security/ # JWT & RBAC
│ │ ├── service/ # Business Logic
│ │ └── util/ # Utility Classes
│ └── resources/
│ ├── application.yml
│ └── static/
└── test/
└── java/com/erp/ # Unit & Integration Tests


---

## 📖 API Documentation
API endpoints are documented using **Swagger / OpenAPI**.  
👉 After running the project, visit:  http://localhost:8080/swagger-ui.html

## ⚙️ Installation & Setup

1. **Clone the repo**
   ```bash
   git clone https://github.com/your-username/erp-system-api.git
   cd erp-system-api

Configure the database

Update application.yml with your MySQL credentials.

Run the application
mvn spring-boot:run

👨‍💻 Author
Abdulrahman Ahmed
