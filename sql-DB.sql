DROP DATABASE IF EXISTS restaurant_db;
CREATE DATABASE restaurant_db;
USE restaurant_db;

CREATE TABLE tables (
    table_id INT PRIMARY KEY,
    size INT NOT NULL,
    is_reserved BOOLEAN DEFAULT FALSE
);

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_no VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    reservation_date DATE NOT NULL,
    reservation_time TIME NOT NULL,
    number_of_guests INT NOT NULL,
    table_id INT NULL, 
    notes TEXT,
    FOREIGN KEY (table_id) REFERENCES tables(table_id)
);

INSERT INTO tables (table_id, size, is_reserved) VALUES 
(1, 2, false),
(2, 4, false),
(3, 4, false),
(4, 6, false),
(5, 6, false); 
