
# Mini App – User Registration & Authentication System

## Project Overview

This project demonstrates how system analysis and design artifacts (ERD, Use Case, Activity, Class, and Sequence Diagrams) translate into a working authentication system. The application provides secure user registration, login, profile management, and logout functionality.

**GitHub Repository:** https://github.com/kyraikkonen/IT342_G4_Arong_Lab1

## Current Implementation
- ✅ ReactJS Web Application
- ✅ Spring Boot REST API Backend
- ✅ MySQL Database (HeidiSQL)
- ⏳ Mobile Application (Planned for future)

---

## Technologies Used

**Frontend:** React (Vite), React Router, JavaScript, HTML/CSS  
**Backend:** Spring Boot, Spring Security, JWT (JJWT 0.12.3), JPA/Hibernate, BCrypt  
**Database:** MySQL 8.0+, HeidiSQL  
**Tools:** IntelliJ IDEA, VS Code, Git/GitHub

---

## Project Structure



IT342_G4_Arong_Lab1/
├── web/        → React frontend
├── mobile/       → Android Studio
├── backend/    → Spring Boot API
├── docs/       → FRS documentation
└── README.md


---

## Features

✅ **User Registration** - Create account  
✅ **User Login** - JWT-based authentication  
✅ **Dashboard** - Protected page with user info  
✅ **Profile** - View account details  
✅ **Logout** - Session termination  
✅ **Route Protection** - Redirect unauthenticated users  

---

## API Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/auth/register` | Register new user | No |
| POST | `/auth/login` | Login and get JWT | No |
| GET | `/auth/me` | Get current user | Yes |

---

## Security Features

- BCrypt password hashing
- JWT token authentication
- Protected endpoints with Spring Security
- CORS configuration for frontend access
- 24-hour token expiration
- Route guards on frontend

---

## Setup & Installation

### Database Setup (HeidiSQL)
```sql
CREATE DATABASE auth_db;


Backend Configuration
Update backend/src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

jwt.secret=your-secret-key-minimum-32-characters
jwt.expiration=86400000
server.port=8080


Run Backend

cd backend
mvn clean install
mvn spring-boot:run


Backend runs on: http://localhost:8080
Run Frontend

cd web
npm install
npm run dev


Frontend runs on: http://localhost:3000

Documentation
The /docs folder contains the FRS PDF with:
	∙	Entity Relationship Diagram (ERD)
	∙	Use Case Diagram
	∙	Activity Diagram
	∙	Class Diagram
	∙	Sequence Diagram
	∙	UI Screenshots (Register, Login, Dashboard, Profile)

Testing
	1.	Register: Go to /register, create account
	2.	Login: Go to /login, enter credentials
	3.	Dashboard: View protected page with user info
	4.	Profile: Check account details at /profile
	5.	Logout: Click logout, verify redirect and session clear


Implementation Status
Completed:
	∙	Web frontend (React)
	∙	Backend API (Spring Boot)
	∙	MySQL database integration
	∙	JWT authentication
	∙	Protected routes
	∙	Complete documentation
Not Implemented:
	∙	Mobile application (future)
  ∙ Major UI Improvements
