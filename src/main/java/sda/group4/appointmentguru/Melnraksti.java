package sda.group4.appointmentguru;

import java.sql.*;
import java.util.Scanner;

public class Melnraksti {



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

    //? Uz Melnrakstiem, jo ir viewMyAppointmentDoctor
    // to get all records with appointment available with visit to a Doctor
    public static void printAllRecordDateTimeToDoctor(Connection connection, int selected_id_doctor_code) throws SQLException {
        String sql = "SELECT appointment.id_appointment," +
                //" doctor.doctor_medical_speciality, doctor.doctor_name, doctor.doctor_surname, " +
                "appointment.visit_date, appointment.visit_time from appointment inner join doctor " +
                "on appointment.id_doctor_code=doctor.id_doctor_code where appointment.date_time_busy=1 and appointment.id_doctor_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, selected_id_doctor_code);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("id_appointment | visit_date | visit_time");
            //System.out.println("id_appointment | doctor_medical_speciality | doctor_name | doctor_surname  | visit_date | visit_time");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                //String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                //String doctor_name = resultSet.getString("doctor_name");
                //String doctor_surname = resultSet.getString("doctor_surname");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                //System.out.println(id_appointment + " | " + doctor_medical_speciality + " | "
                //        + doctor_name + " | " + doctor_surname + " | "
                //        + visit_date + " | " + visit_time);
                System.out.println(id_appointment + " | "
                        + visit_date + " | " + visit_time);

            }
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
        Melnraksti.insertInfoIntoTableSchedule(connection, doctor_name, doctor_surname, doctor_medical_speciality, visit_date, visit_time, date_time_busy, patient_person_code); //to insert information in table
    }




}
