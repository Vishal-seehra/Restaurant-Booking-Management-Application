package services;

import models.Reservation;
import models.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReservationManager {
    private Connection conn;

public ReservationManager() {
    try {
        conn = DriverManager.getConnection(
            "jdbc:mysql://restaurant-db.cl80c82c8jen.eu-north-1.rds.amazonaws.com:3306/restaurant_db",
            "admin",
            "Seehra04"
        );
        System.out.println("Connected to RDS!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private List<Reservation> reservations;
    private Queue<Reservation> walkInQueue;
    private TableManager tableManager;

    public ReservationManager(TableManager tableManager) {
        this.tableManager = tableManager;
        this.reservations = new ArrayList<>();
        this.walkInQueue = new LinkedList<>();
    }

    //Reserve Table
    public boolean reserveTable(String firstName, String lastName, String contact, String email, 
                                String date, String time, int guests, String notes) {

        Table table = tableManager.allocateTable(guests);

        if (table == null) {
            walkInQueue.add(new Reservation(firstName, lastName, guests, time, -1));
            return false;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO reservations (first_name, last_name, contact_no, email, " +
                         "reservation_date, reservation_time, number_of_guests, table_id, notes) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, contact);
            ps.setString(4, email);
            ps.setString(5, date);
            ps.setString(6, time);
            ps.setInt(7, guests);
            ps.setInt(8, table.getTableId());
            ps.setString(9, notes);
            ps.executeUpdate();

            //Table Status
            String updateTableSql = "UPDATE tables SET is_reserved = true WHERE table_id = ?";
            PreparedStatement updatePs = conn.prepareStatement(updateTableSql);
            updatePs.setInt(1, table.getTableId());
            updatePs.executeUpdate();

            table.setReserved(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Find Reservation by First Name
    public Reservation findReservationByName(String firstName) {
        String sql = "SELECT * FROM reservations WHERE first_name = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, firstName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reservation(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("number_of_guests"),
                    rs.getString("reservation_time"),
                    rs.getInt("table_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Cancel Reservation
    public boolean cancelReservation(String firstName) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "DELETE FROM reservations WHERE first_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Queue<Reservation> getWalkInQueue() { return walkInQueue; }
    public List<Reservation> getAllReservations() { return reservations; }
}
