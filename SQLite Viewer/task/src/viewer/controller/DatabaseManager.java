package viewer.controller;

import viewer.model.Database;
import viewer.model.Table;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:";
    private static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();
    private static Database currentDatabase;

    private DatabaseManager() {
    }

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public Database getDatabase(String filename) {

        if (currentDatabase != null && isSameDatabaseFile(filename)) {
            return currentDatabase;
        } else {
            try (Connection connection = DriverManager.getConnection(DB_URL + filename)) {
                try (Statement statement = connection.createStatement()) {
                    ArrayList<String> tableNames = new ArrayList<>();
                    String query = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        tableNames.add(resultSet.getString(1));
                    }

                    //refactor this maybe. its ok to have empty db i think?
                    currentDatabase = tableNames.isEmpty() ? null : new Database(filename, tableNames.toArray(new String[0]));
                    return currentDatabase;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new Frame(), "INVALID DB FILE");
                return null;
            }
        }
    }
    public int executeStatement(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL + currentDatabase.fileName())) {
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate(query);
            } catch (SQLException e) {
                //generate SQL Exception
                return -1;
            }
        } catch (SQLException e) {
            //generate invalid file exception
            return -404;
        }
    }

    public Optional<Table> getTable(String query) throws SQLException {
        if (currentDatabase != null) {
            try (Connection connection = DriverManager.getConnection(DB_URL + currentDatabase.fileName())) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(query);
                    return Optional.of(new Table(resultSet));
                }
            }
        } else {
            System.out.println("ERROR NO DATABASE LOADED");
            return Optional.empty();
        }
    }

    public static DatabaseManager getDatabaseManager() {
        return DATABASE_MANAGER;
    }

    private boolean isSameDatabaseFile(String filename) {
        return currentDatabase.fileName().equals(filename);
    }
}
