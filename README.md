# 🍽️ Restaurant Booking Management Application

![Java](https://img.shields.io/badge/Java-Backend-blue)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange)
![HTML](https://img.shields.io/badge/HTML5-Frontend-red)
![CSS](https://img.shields.io/badge/CSS3-Styling-blueviolet)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6-yellow)
![Status](https://img.shields.io/badge/Project-Active-brightgreen)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A robust, full-stack web application designed to manage restaurant bookings. This project demonstrates a complete data lifecycle: from **User Input (Frontend)** to **Business Logic (Java Backend)** and **Persistent Storage (MySQL)**.

---

## 🚀 **Key Features**

* **Real-time Table Allocation:** Automatically assigns the smallest available table that fits the number of guests.
* **Full-Stack Connectivity:** Uses Java's `HttpServer` to create a custom REST API (CORS enabled).
* **Database Persistence:** Full CRUD operations with MySQL for managing reservations and table states.
* **Responsive UI:** Clean, CSS-styled reservation form with real-time user notifications.

---

## 🏗️ **System Architecture**

The project follows an **N-Tier Architecture** to ensure separation of concerns:

* **Presentation Layer:** HTML5, CSS3, JavaScript (Fetch API)
* **Logic Layer (API):** Java-based handlers for request parsing and reservation processing
* **Data Access Layer:** JDBC (Java Database Connectivity) with a `DatabaseManager` Singleton
* **Storage Layer:** MySQL Relational Database

---

## 📊 **Database Schema**

The system utilizes two primary tables with a **one-to-many relationship**:

* **`tables`**

  * `table_id`
  * `size`
  * `is_reserved`

* **`reservations`**

  * `reservation_id`
  * `customer_name`
  * `guests`
  * `table_id` *(Foreign Key)*

---

## 📂 **Project Structure**

```plaintext id="1i0i6u"
├── lib/               # JDBC Driver (MySQL Connector)
├── models/            # Data Objects (POJOs)
├── services/          # Business Logic & API Handlers
├── sql-DB.sql         # Database Initialization Script
├── reservation.html   # Frontend Reservation Form
└── Main.java          # Application Entry Point
```

---

## ▶️ **How to Run**

1. Clone the repository
2. Import into your Java IDE
3. Set up MySQL using `sql-DB.sql`
4. Run `Main.java`
5. Open `reservation.html` in browser

---

## 📌 **Future Improvements**

* Add user authentication & login system
* Improve UI/UX with modern frameworks
* Deploy backend on cloud (AWS / Render)
* Add admin dashboard for table management

---
