package sda.group4.appointmentguru;

import java.sql.*;

public class OperationFile {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/she_goes_tech", "root", "tasite7!L");
    }

    // to delete some table, if we need
    public static void deleteTable(Connection connection, String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }


    // to create table for information about Doctor, appointment Date, Time (start, end), information is DateTime busy(1) or not(null,0) and information about Patient
    public static void createTableAppointment(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS appointment(" +
                "id_appointment INT AUTO_INCREMENT PRIMARY KEY," +

                "id_doctor_code INT," +
                "doctor_name VARCHAR(200)," +
                "doctor_surname VARCHAR(200)," +
                "doctor_work_time VARCHAR(200)," +
                "doctor_work_start_time TIME," +
                "doctor_work_end_time TIME," +
                "doctor_visit_price DOUBLE," +

                //id_visit, id_doctor_code, visit_date, visit_start_time, visit_end_time, date_time_busy, patient_person_code

                "visit_date DATE," +
                "visit_start_time TIME," +
                "visit_end_time TIME," +

                "date_time_busy INT," +

                //id_patient=patient_person_code, patient_name, patient_surname, patient_phone_number, id_visit, id_doctor_code,
                "patient_person_code VARCHAR(200)," +
                "patient_name VARCHAR(200)," +
                "patient_surname VARCHAR(200)," +
                "patient_phone_number VARCHAR(200)" +
                ")";
        //Try with resources
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    // to get all records about Doctors, who work in our hospital, appointment Date, Time (start, end), info about is DateTime busy(1) or not(null,0) and Patient
    public static void printAllRecordAppointment(Connection connection) throws SQLException {
        String sql = "SELECT * from appointment";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int id_appointment = resultSet.getInt("id_appointment");

                int id_doctor_code = resultSet.getInt("id_doctor_code");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                String doctor_work_time = resultSet.getString("doctor_work_time");//?vai ka time: sadalot start un end time
                Time doctor_work_start_time = resultSet.getTime("doctor_work_start_time");//?vai ka time: sadalot start un end time
                Time doctor_work_end_time = resultSet.getTime("doctor_work_end_time");//?vai ka time: sadalot start un end time
                double doctor_visit_price = resultSet.getDouble("doctor_visit_price"); //te japadoma, lai butu divas zimes aiz komata

                Date visit_date = resultSet.getDate("visit_date");
                Time visit_start_time = resultSet.getTime("visit_start_time");
                Time visit_end_time = resultSet.getTime("visit_end_time");

                int date_time_busy = resultSet.getInt("date_time_busy");

                String patient_person_code = resultSet.getString("patient_person_code");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");
                String patient_phone_number = resultSet.getString("patient_phone_number");

                System.out.println(id_appointment + " | " + id_doctor_code + " | " + doctor_name + " | " + doctor_surname + " | "
                        + doctor_work_time + " | " + doctor_work_start_time + " | " + doctor_work_end_time + " | " + doctor_visit_price + " | "
                        + visit_date + " | " + visit_start_time + " | " + visit_end_time + " | " + date_time_busy + " | "
                        + patient_person_code + " | " + patient_name + " | " + patient_surname + " | " + patient_phone_number);
            }
        }
    }

    // to put information in Appointment table about Patient. Patient choose date and start time
    public static void updateAppointmentPatient(Connection connection, String patient_person_code, String patient_name, String patient_surname, String patient_phone_number, Date visit_date, Time visit_start_time) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=?, patient_name=?, patient_surname=?,patient_phone_number=? WHERE visit_date=? AND visit_start_time=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            statement.setString(2, patient_person_code);
            statement.setString(3, patient_name);
            statement.setString(4, patient_surname);
            statement.setString(5, patient_phone_number);
            statement.setDate(6, visit_date);
            statement.setTime(7, visit_start_time);

            //?+information about doctor: id_doctor_code; doctor_name; doctor_surname
            //statement.setString(8, id_doctor_code);
            //statement.setString(9, doctor_name);
            //statement.setString(10, doctor_surname);

            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Patient was successfully inserted");
            }
        }
    }


    // so vel japadoma, lai samekletu atbilstosu ierakstu ne tikai pec datuma un laika, bet ari parbauditu par pacientu
    // vai parbaudi veikt main un te tikai darboties
    // to put information in Appointment table about Patient. Patient choose date and start time
    public static void updateAppointmentPatientDelete(Connection connection, String patient_person_code, String patient_name, String patient_surname, String patient_phone_number, Date visit_date, Time visit_start_time) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=?, patient_name=?, patient_surname=?,patient_phone_number=? WHERE visit_date=? AND visit_start_time=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setString(2, null);
            statement.setString(3, null);
            statement.setString(4, null);
            statement.setString(5, null);
            statement.setDate(6, visit_date);
            statement.setTime(7, visit_start_time);

            //?+information about doctor: id_doctor_code; doctor_name; doctor_surname
            //statement.setString(8, id_doctor_code);
            //statement.setString(9, doctor_name);
            //statement.setString(10, doctor_surname);

            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Patient was successfully inserted");
            }
        }
    }




    // to delete information in Appointment table, if Doctor doesn't work more
    public static void deleteRecordAppointment(Connection connection, int id_doctor_code) throws SQLException {
        String sql = "DELETE from appointment where id_doctor_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_doctor_code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.printf("Record about Doctor with id %s was successfully deleted \n", id_doctor_code);
            }
        }
    }



}