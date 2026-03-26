🚀 Lunchpad – Job Application Platform

A full-stack job application platform where candidates can apply for jobs, recruiters can manage postings, and admins control the system through a role-based architecture.

📌 Overview

Lunchpad is designed to simplify the hiring process by providing a centralized system for job applications and recruitment management.

It supports three roles:

👤 Candidate
🧑‍💼 Recruiter
🛠️ Admin

Each role has its own dashboard and permissions, ensuring secure and efficient workflows.

🛠️ Tech Stack
Backend
Java
Spring Boot
Spring Security
Hibernate / JPA
REST APIs
Frontend
React
Tailwind CSS
Database
PostgreSQL
✨ Features
🔐 Authentication & Authorization
Secure login & registration
Role-based access control using Spring Security
👤 Candidate Features
Apply for jobs
Upload resume
Track application status
Personalized dashboard
🧑‍💼 Recruiter Features
Post new job listings
View applicants
Manage job applications
🛠️ Admin Features
Manage users (candidates & recruiters)
Monitor platform activity
System-level control
📊 General Features
RESTful API architecture
Scalable backend design
Clean UI with Tailwind CSS
🧱 Architecture
Frontend: React SPA consuming REST APIs
Backend: Spring Boot REST API
Database: PostgreSQL with JPA/Hibernate ORM
Security: Spring Security (Role-Based Access)
📁 Project Structure (Simplified)

Lunchpad/
│
├── backend/
│ ├── controller/
│ ├── service/
│ ├── repository/
│ ├── entity/
│ └── security/
│
├── frontend/
│ ├── components/
│ ├── pages/
│ ├── context/
│ └── services/
│
└── README.md

⚙️ Setup Instructions
🔧 Backend Setup

cd backend
./mvnw spring-boot:run

Configure database in application.properties
Default port: http://localhost:8080
💻 Frontend Setup

cd frontend
npm install
npm run dev

Runs on: http://localhost:5173
