package services;

import models.Table;
import java.sql.*;
import java.util.*;

public class TableManager {
    private List<Table> tables;

    public TableManager() {
        tables = new ArrayList<>();
        loadTablesFromDb();
    }

    private void loadTablesFromDb() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM tables";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Table t = new Table(rs.getInt("table_id"), rs.getInt("size"));
                t.setReserved(rs.getBoolean("is_reserved"));
                tables.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Table allocateTable(int numberofguests) {
        return tables.stream()
                .filter(t -> !t.isReserved() && t.getSize() >= numberofguests)
                .min(Comparator.comparingInt(Table::getSize))
                .orElse(null);
    }

    public void releaseTable(int tableId) {
        for (Table t : tables) {
            if (t.getTableId() == tableId) {
                t.setReserved(false);
                break;
            }
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}
