# Employee Resource Management System (ERMS)

## Table of Contents
- [Overview](#overview)
- [Quick Start](#quick-start)
- [Detailed Setup](#detailed-setup)
- [Default Accounts](#default-accounts)
- [API Documentation](#api-documentation)
- [Security](#security)

## Overview
ERMS is a light Employee Resource Management System with a Spring Boot backend and a Java Swing desktop client. The system is designed as a two-tier application:

Backend: REST APIs built with Spring Boot and Oracle Database
Frontend: Desktop application built with Java Swing [visit project repository](https://github.com/GHamza-Dev/erms-client)

## Quick Start

1. Clone the repository and navigate to the project directory:
```bash
git clone https://github.com/GHamza-Dev/erms.git
cd erms
```

2. Run the setup script:
```bash
chmod +x setup.sh
./setup.sh
```

The setup script will:
- Stop and remove any existing containers
- Clean up Oracle data volumes
- Create necessary initialization scripts
- Start the Oracle database
- Configure the database user and permissions
- Start the Spring Boot application

## Detailed Setup

### Docker Configuration

The application uses Docker Compose for container orchestration. Two services are defined:

1. Oracle Database (oracle-db):
    - Uses Oracle Free edition
    - Exposes port 1521
    - Includes health checks
    - Configured with initial setup scripts

2. ERMS Application (erms-app):
    - Built using multi-stage Dockerfile
    - Depends on oracle-db service
    - Exposes port 8080
    - Configured with environment variables

To manually start the services:
```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Default Accounts

The application automatically creates the following accounts on startup (it clears database whenever you rerun the app, it keeps only auditing information):

| Username    | Password    | Role    | Employee         | Email               |
|------------|-------------|---------|------------------|---------------------|
| admin      | admin       | ADMIN   | John Admin       | admin@erms.com      |
| hrmanager  | hr         | HR      | Sarah Johnson    | hr.manager@erms.com |
| itmanager  | it         | MANAGER | Michael Tech     | it.manager@erms.com |
| mrkmanager | mrkmanager | MANAGER | Hamza Tijani    | mrk.manager@erms.com|
| developer1 | dev        | EMPLOYEE| David Smith      | david.smith@erms.com|

### Initial Data
The system also creates:
- Departments: IT, Human Resources, Finance, Marketing
- Job Titles: CEO, CTO, HR Manager, Software Developer, UX Designer, Accountant, Marketing Manager

## API Documentation

### Base URL
```
http://localhost:8080/api/erms
```

### Swagger UI
```
http://localhost:8080/api/erms/swagger-ui.html
```

### Authentication
```http
POST /auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin"
}
```

### Employee Endpoints

#### Create Employee (HR, Admin only)
```http
POST /employees
Authorization: Bearer {token}
Content-Type: application/json

{
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "phone": "string",
    "hireDate": "YYYY-MM-DD",
    "contractType": "string",
    "employmentStatus": "ACTIVE|INACTIVE|ON_LEAVE",
    "departmentId": 0,
    "jobTitleId": 0,
    "address": "string",
    "isAssignedToProject": boolean
}
```

#### Update Employee
```http
PUT /employees
Authorization: Bearer {token}
Content-Type: application/json

{
    "id": 0,
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "phone": "string",
    "employmentStatus": "ACTIVE|INACTIVE|ON_LEAVE",
    "isAssignedToProject": boolean
}
```

#### Get Employee by ID
```http
GET /employees/{id}
Authorization: Bearer {token}
```

#### Find Employee by Employee ID
```http
GET /employees/find-by-employee-id/{employeeId}
Authorization: Bearer {token}
```

#### Delete Employee (HR, Admin only)
```http
DELETE /employees/{id}
Authorization: Bearer {token}
```

#### Search Employees
```http
POST /employees/search
Authorization: Bearer {token}
Content-Type: application/json

{
    "name": "string",
    "employeeId": "string",
    "departmentId": 0,
    "jobTitleId": 0,
    "employmentStatus": "string",
    "hireDateFrom": "YYYY-MM-DD",
    "hireDateTo": "YYYY-MM-DD",
    "contractType": "string",
    "page": 0,
    "size": 20,
    "sortBy": "id|employeeId",
    "sortDirection": "desc|asc"
}
```

### Department Endpoints

#### Get All Departments
```http
GET /departments
Authorization: Bearer {token}
```

### Job Title Endpoints

#### Get All Job Titles
```http
GET /job-titles
Authorization: Bearer {token}
```

## Security

### Role-Based Access Control
- **ADMIN**: Full system access including auditing info
- **HR**: Full employee management access
- **MANAGER**: Limited access to employees in their department
    - Can view employee details
    - Can update limited fields (email, phone, isAssignedToProject)

## Testing the Application

1. Start the application using the setup script:
   ```bash
   ./setup.sh
   ```

2. Wait for the services to start (about 2-3 minutes)

3. Access Swagger UI:
   ```
   http://localhost:8080/api/erms/swagger-ui.html
   ```

4. Authenticate using any of the default accounts (e.g., admin/admin)

5. Copy the JWT token and click "Authorize" in Swagger UI

6. Paste the token with format: `Bearer your-jwt-token`

7. Test the endpoints according to your role permissions