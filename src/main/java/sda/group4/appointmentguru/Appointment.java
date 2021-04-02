package sda.group4.appointmentguru;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

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

    public static void updateAppointmentsWithPatientBusy(Connection connection, int id_appointment, String patient_person_code) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE id_appointment=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            statement.setString(2, patient_person_code);
            statement.setInt(3, id_appointment);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Appointment is updated");
            }
        }
    }

    public static void updateAppointmentsWithNotBusy(Connection connection, String patient_person_code, Date visit_date, Time visit_time) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE patient_person_code=? AND visit_date=? AND visit_time=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setString(2, null);
            statement.setString(3, patient_person_code);
            statement.setDate(4, visit_date);
            statement.setTime(5, visit_time);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Appointment is updated with - not busy");
            }
        }
    }

    // to get all records with appointment available for Patient
    public static void printAllRecordDateTimeAvailable(Connection connection, int selected_id_doctor_code) throws SQLException {
        String sql = "SELECT appointment.id_appointment, " +
                "doctor.doctor_medical_speciality, doctor.doctor_name, doctor.doctor_surname, " +
                "appointment.visit_date, appointment.visit_time from appointment inner join doctor " +
                "on appointment.id_doctor_code=doctor.id_doctor_code where appointment.date_time_busy=0 and appointment.id_doctor_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, selected_id_doctor_code);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("id_appointment | doctor_medical_speciality | doctor_name | doctor_surname  | visit_date | visit_time");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                System.out.println(id_appointment + " | " + doctor_medical_speciality + " | "
                        + doctor_name + " | " + doctor_surname + " | "
                        + visit_date + " | " + visit_time);

            }
        }
    }

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
        Appointment.insertInfoIntoTableSchedule(connection, doctor_name, doctor_surname, doctor_medical_speciality, visit_date, visit_time, date_time_busy, patient_person_code); //to insert information in table


    }
    public static void viewMyAppointmentDoctor(Connection connection) throws SQLException {
        System.out.println("To see all appointments, enter doctor's id code: ");
        Scanner scanner = new Scanner(System.in);
        int identification = scanner.nextInt();

            String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                    "FROM appointment " +
                    "INNER JOIN patient " +
                    "ON appointment.patient_person_code = patient.patient_person_code " +
                    "WHERE id_doctor_code = " + identification + " AND date_time_busy = '1'";
//te vajadzētu ievietot loop, lai pārbaudītu vai  tāds id eksistē
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.printf("List of all appointments for doctor with id %s: \n", identification);
            while (resultSet.next()) {

                //int id_doctor_code = resultSet.getInt("id_doctor_code");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                //String patient_person_code = resultSet.getString("patient_person_code");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");

                System.out.printf("Date: %s, time: %s, patient: %s %s \n", visit_date, visit_time, patient_name, patient_surname);
            }
            statement.close();

    }

    public static void viewAppointmentForToday(Connection connection) throws SQLException {
        LocalDate date = LocalDate.now();
        System.out.println("Current Date is " + date);
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter date using format yyyy-mm-dd");

        System.out.println("To see all appointments, enter doctor's id code: ");
        int identification = scanner.nextInt();

        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = "+ identification +" AND visit_date = " + date +" AND date_time_busy = '1'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        System.out.printf("On %d you have the following appointments", date);

        while (resultSet.next()) {
            int id_doctor_code = resultSet.getInt("id_doctor_code");
            //Date visit_date = resultSet.getDate("visit_date");
            Time visit_time = resultSet.getTime("visit_time");
            //String patient_person_code = resultSet.getString("patient_person_code");
            String patient_name = resultSet.getString("patient_name");
            String patient_surname = resultSet.getString("patient_surname");
            System.out.printf("time: %s, patient: %s %s \n",visit_time, patient_name, patient_surname);
        }
        statement.close();
    }


}
