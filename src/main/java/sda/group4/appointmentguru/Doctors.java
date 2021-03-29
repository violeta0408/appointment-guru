package sda.group4.appointmentguru;

import java.sql.*;

public class Doctors {

    // to create table with Doctors, who work in our hospital
    public static void createTableDoctor(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS doctor(" +
                "id_doctor_code INT AUTO_INCREMENT PRIMARY KEY," +
                "doctor_medical_speciality VARCHAR(200)," +
                "doctor_name VARCHAR(200)," +
                "doctor_surname VARCHAR(200)," +
                "doctor_room_number INT," +
                "doctor_work_start_time TIME," +
                "doctor_work_end_time TIME," +
                "doctor_visit_price DOUBLE" +
                ")";
        //Try with resources
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    // to get all records about Doctors, who work in our hospital
    public static void printAllRecordDoctor(Connection connection) throws SQLException {
        String sql = "SELECT * from doctor";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id_doctor_code = resultSet.getInt("id_doctor_code");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Integer doctor_room_number = resultSet.getInt("doctor_room_number");
                Time doctor_work_start_time = resultSet.getTime("doctor_work_start_time");
                Time doctor_work_end_time = resultSet.getTime("doctor_work_end_time");
                Double doctor_visit_price = resultSet.getDouble("doctor_visit_price");

                System.out.println(id_doctor_code + " | " + doctor_medical_speciality + " | "
                        + doctor_name + " | " + doctor_surname + " | " + doctor_room_number + " | "
                        + doctor_work_start_time + " | " + doctor_work_end_time + " | " + doctor_visit_price);
            }
        }
    }

    // to put information in Doctor table, if start work new Doctor
    public static void insertIntoDoctorTable(Connection connection,
                                             String doctor_medical_speciality,
                                             String doctor_name,
                                             String doctor_surname,
                                             Integer doctor_room_number,
                                             Time doctor_work_start_time,
                                             Time doctor_work_end_time,
                                             Double doctor_visit_price) throws SQLException {
        String sql = "INSERT INTO doctor(doctor_medical_speciality, " +
                "doctor_name, doctor_surname, doctor_room_number, " +
                "doctor_work_start_time, doctor_work_end_time, doctor_visit_price) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor_medical_speciality);
            statement.setString(2, doctor_name);
            statement.setString(3, doctor_surname);
            statement.setInt(4, doctor_room_number);
            statement.setTime(5, doctor_work_start_time);
            statement.setTime(6, doctor_work_end_time);
            statement.setDouble(7, doctor_visit_price);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Doctor was successfully inserted");
            }
        }
    }

    // to delete information in Doctor table, if Doctor doesn't work more
    public static void deleteRecordDoctor(Connection connection, int id_doctor_code) throws SQLException {
        String sql = "DELETE from doctor where id_doctor_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_doctor_code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.printf("Record about Doctor with id %s was successfully deleted \n", id_doctor_code);
            }
        }
    }

    // to update information in Doctor table, if it was some mistake before
    public static void updateRecordDoctor(Connection connection,
                                          String doctor_medical_speciality,
                                          String doctor_name,
                                          String doctor_surname,
                                          Integer doctor_room_number,
                                          Time doctor_work_start_time,
                                          Time doctor_work_end_time,
                                          Double doctor_visit_price,
                                          int id_doctor_code) throws SQLException {
        String sql = "UPDATE doctor SET doctor_medical_speciality=?, " +
                "doctor_name=?, doctor_surname=?, doctor_room_number=?," +
                "doctor_work_start_time=?,doctor_work_end_time=?,doctor_visit_price=? " +
                "WHERE id_doctor_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor_medical_speciality);
            statement.setString(2, doctor_name);
            statement.setString(3, doctor_surname);
            statement.setInt(4, doctor_room_number);
            statement.setTime(5, doctor_work_start_time);
            statement.setTime(6, doctor_work_end_time);
            statement.setDouble(7, doctor_visit_price);
            statement.setInt(8, id_doctor_code);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.printf("Record about Doctor with id %s was successfully updated \n", id_doctor_code);
            }
        }
    }
}
