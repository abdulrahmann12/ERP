# Library Management System (Spring Boot)

## Project Overview
The Library Management System is a comprehensive solution for managing a library's operations.  
It allows the administration of books, publishers, and members, with advanced features such as role-based access control, user activity logging, automated email notifications, and integration with external services like RabbitMQ and cloud storage for images.  

The system ensures smooth library management, keeps track of all user activities, and automates processes such as overdue book reminders.

---

## Key Features

### User Management
- Manage users with different roles (Admin, Librarian, Member).  
- Update user profiles and profile images.  
- Role-based access control for secured operations.  

### Member Management
- Create, update, delete, and view library members.  
- Search members by name.  

### Book & Publisher Management
- Manage books, publishers, and multiple authors per book.  
- Search for books and publishers.  
- Category/genre classification with hierarchical structure.  
- Support for CRUD operations with validation.  

### Role Management
- Admin can create roles and assign them to users.  
- Role-based access to APIs.  

### User Activity Logging
- Automatic logging of create, update, and delete actions.  
- Admin can view logs of all users or specific users.  

### Email Notifications
- Automatic email reminders for overdue books using scheduled tasks.  
- Send welcome emails to new users.  
- Generate and send codes for "Forgot Password" functionality.  
- Configurable email content and triggers.  

### RabbitMQ Integration
- Handles asynchronous messaging and background tasks.  
- Ensures smooth handling of email notifications and other queued tasks.  

### Image Upload & Cloud Storage
- Upload user profile images or book cover images.  
- Integrates with cloud storage for file management.  

### Validation & Exception Handling
- Global validation for request payloads.  
- Centralized exception handling with meaningful error messages.  

### Swagger Documentation
- Complete API documentation for easy testing and integration.  

### AOP Logging
- Tracks execution time and logs important events in services.  
- Captures exceptions and performance metrics for debugging.  

---

## Technologies Used
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA  
- **Database:** MySQL  
- **Messaging Queue:** RabbitMQ  
- **Authentication:** JWT (JSON Web Tokens)  
- **Validation & Exception Handling:** Spring Validation, Controller Advice  
- **Scheduling:** Spring `@Scheduled` for automated tasks (e.g., overdue email notifications)  
- **Email Service:** JavaMailSender for sending emails (welcome email, forgot password, overdue reminders)  
- **File Storage:** MultipartFile + Cloud integration for profile and book cover images  
- **Documentation:** Swagger / OpenAPI for API documentation  
- **Logging:** SLF4J + AOP for execution time and exception logging  

---

## How It Works
1. Users can register or be created by Admin/Librarian.  
2. Members can borrow books.  
3. The system automatically tracks borrowings and overdue books.  
4. Scheduled tasks send email notifications to members with overdue books.  
5. Admins can view activity logs, manage users, roles, books, and publishers.  
6. All operations are secured by role-based access control.  

---

## API Endpoints (Examples)
- `/api/users` – Manage users  
- `/api/members` – Manage library members  
- `/api/books` – Manage books  
- `/api/publishers` – Manage publishers  
- `/api/roles` – Manage roles  
- `/api/logs` – View activity logs  

---

## Architecture
- **Controllers:** Handle API requests (`UserController`, `MemberController`, `PublisherController`, `RoleController`, `UserActivityLogController`)  
- **Services:** Business logic for all entities  
- **Repositories:** JPA repositories for database access  
- **DTOs:** Data Transfer Objects for request/response  
- **Entities:** JPA entities representing database tables  
- **AOP:** `LoggingAspect` (execution time logging), `ActivityLoggingAspect` (create/update/delete logs)  
- **Scheduler:** Sends overdue book reminders automatically  
- **Validation:** DTO field validation using annotations  
- **Global Exception Handling:** Handles exceptions and returns structured responses  

---

## Setup & Run

1. Clone the repository:  
```bash
git clone <repo-url>
