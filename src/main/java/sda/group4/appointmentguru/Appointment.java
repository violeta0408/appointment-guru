package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Appointment {

    // to create table with Appointments
    public static void createTableAppointments(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS appointment(" +
                "id_appointment INT AUTO_INCREMENT PRIMARY KEY," +
                "id_doctor_code INT,"+
                "visit_date DATE," +
                "visit_time TIME," +
                "date_time_busy INT," +
                "patient_person_code VARCHAR(200)" +
                ")";
        //Try with resources
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }


}
