package sda.group4.appointmentguru;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;

public class Appointment {

    // to create table with Appointments
    public static void createTableAppointments(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS appointment(" +
                "id_appointment INT AUTO_INCREMENT PRIMARY KEY," +
                "id_doctor_code INT," +
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

    // to create table called Schedule
    public static void createTableSchedule(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS schedule (" +
                "id_appointment INT AUTO_INCREMENT PRIMARY KEY," +
                "doctor_name VARCHAR(200)," +
                "doctor_surname VARCHAR(200)," +
                "doctor_medical_speciality VARCHAR(200)," +
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

    // to insert information in the Schedule table
    public static void insertInfoIntoTableSchedule(Connection connection,
                                               String doctor_name,
                                               String doctor_surname,
                                               String doctor_medical_speciality,
                                               Date visit_date,
                                               Time visit_time,
                                               Integer date_time_busy,
                                               String patient_person_code) throws SQLException {
        String sql = "INSERT INTO schedule (doctor_name, " +
                "doctor_surname, " +
                "doctor_medical_speciality, " +
                "visit_date, " +
                "visit_time, " +
                "date_time_busy, " +
                "patient_person_code) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor_name);
            statement.setString(2, doctor_surname);
            statement.setString(3, doctor_medical_speciality);
            statement.setDate(4, visit_date);
            statement.setTime(5, visit_time);
            statement.setInt(6, date_time_busy);
            statement.setString(7, patient_person_code);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("You have successfully entered information into the Schedule");
            }
        }
    }

    public static void insertInfoIntoSchedule(Connection connection) throws SQLException {

        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Please enter doctor's name");
        String doctor_name = scanner1.nextLine();
        System.out.println("Please enter doctor's surname");
        String doctor_surname = scanner1.nextLine();
        System.out.println("Please enter doctor's speciality");
        String doctor_medical_speciality = scanner1.nextLine();
        System.out.println("Please enter the date (yy:mm:dd) of the appointment");
        Date visit_date = Date.valueOf(scanner1.next());
        System.out.println("Please enter the time (hh:mm:ss) of the appointment");
        Time visit_time = Time.valueOf(scanner1.next());
        System.out.println("If the time is available, please enter '0', if busy - '1'.");
        Integer date_time_busy = scanner1.nextInt();
        System.out.println(" ");
        String patient_person_code = scanner1.nextLine();

        //to load information about the available appointment times in the Schedule
        System.out.println("Information about the available appointment times is loaded in the database");
        Appointment.insertInfoIntoTableSchedule(connection, doctor_name, doctor_surname, doctor_medical_speciality, visit_date, visit_time, date_time_busy, patient_person_code); //to insert information in table


    }


}
