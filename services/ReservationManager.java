package services;

import models.Reservation;
import models.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReservationManager {

    private Queue<Reservation> walkInQueue;
    private TableManager tableManager;

    public ReservationManager(TableManager tableManager) {
        this.tableManager = tableManager;
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

        String insertSql = "INSERT INTO reservations (first_name, last_name, contact_no, email, " +
                "reservation_date, reservation_time, number_of_guests, table_id, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateTableSql = "UPDATE tables SET is_reserved = true WHERE table_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateTableSql)) {

            insertStmt.setString(1, firstName);
            insertStmt.setString(2, lastName);
            insertStmt.setString(3, contact);
            insertStmt.setString(4, email);
            insertStmt.setDate(5, java.sql.Date.valueOf(date));
            insertStmt.setTime(6, java.sql.Time.valueOf(time));
            insertStmt.setInt(7, guests);
            insertStmt.setInt(8, table.getTableId());
            insertStmt.setString(9, notes);

            insertStmt.executeUpdate();

            // Update table status
            updateStmt.setInt(1, table.getTableId());
            updateStmt.executeUpdate();

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

    //Cancel Reservation
    public boolean cancelReservation(String firstName) {

        String sql = "DELETE FROM reservations WHERE first_name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Get All Reservations
    public List<Reservation> getAllReservations() {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Reservation(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("number_of_guests"),
                        rs.getString("reservation_time"),
                        rs.getInt("table_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Queue<Reservation> getWalkInQueue() {
        return walkInQueue;
    }
}
