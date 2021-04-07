package sda.group4.appointmentguru;

import java.sql.*;

public class OperationFile {

    //to get connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/she_goes_tech", "root", "password");
    }

    //to delete some table, if we need
    public static void deleteTable(Connection connection, String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

}