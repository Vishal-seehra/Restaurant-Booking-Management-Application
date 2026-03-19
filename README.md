# Restaurant-Booking-Management-Application
A robust, full-stack web application designed to manage restaurant bookings. This project demonstrates a complete data lifecycle: from User Input (Frontend) to Business Logic (Java Backend) and Persistent Storage (MySQL).

##🚀 Key Features
Real-time Table Allocation: Automatically assigns the smallest available table that fits the number of guests.

Full-Stack Connectivity: Uses Java's HttpServer to create a custom REST API (CORS enabled).

Database Persistence: Full CRUD operations with MySQL for managing reservations and table states.

Responsive UI: Clean, CSS-styled reservation form with real-time user notifications.

##🏗️ System Architecture
The project follows a N-Tier Architecture to ensure separation of concerns:

Presentation Layer: HTML5, CSS3, and JavaScript (Fetch API).

Logic Layer (API): Java-based handlers for request parsing and reservation processing.

Data Access Layer: JDBC (Java Database Connectivity) with a DatabaseManager singleton.

Storage Layer: MySQL Relational Database.

##📊 Database Schema
The system utilizes two primary tables with a One-to-Many relationship:

tables: Stores physical table data (table_id, size, is_reserved).

reservations: Stores customer data with a foreign key linking to the assigned table.

```
📂 Project Structure
Plaintext
├── lib/               # JDBC Driver (MySQL Connector)
├── models/            # Data Objects (POJOs)
├── services/          # Business Logic & API Handlers
├── sql-DB.sql         # Database Initialization Script
├── reservation.html   # Frontend Reservation Form
└── Main.java          # Application Entry Point
```
