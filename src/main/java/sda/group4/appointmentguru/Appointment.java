package sda.group4.appointmentguru;

import java.sql.*;
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

    // to put information in the Appointment table
    public static void insertInfoIntoTableAppointment(Connection connection,
                                                      Integer id_doctor_code,
                                                      Date visit_date,
                                                      Time visit_time ) throws SQLException {
        String sql = "INSERT INTO appointment (id_doctor_code, " +
                "visit_date, " +
                "visit_time) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_doctor_code);
            statement.setDate(2, visit_date);
            statement.setTime(3, visit_time);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("You have successfully entered information into the Appointment Table");
            }
        }
    }

    //to create information for Appointment Table
    public static void insertInfoIntoAppointment(Connection connection) throws SQLException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Please enter the doctor id");
        Integer id_doctor_code=scanner1.nextInt();
        System.out.println("Please enter the date (yyyy-mm-dd) of the appointment");
        Date visit_date = Date.valueOf(scanner1.next());
        System.out.println("Please enter the time (hh:mm:ss) of the appointment");
        Time visit_time = Time.valueOf(scanner1.next());
        //to load information about the available appointment times in the Schedule
        System.out.println("Information about the available appointment times is loaded in the database");
        Appointment.insertInfoIntoTableAppointment(connection, id_doctor_code, visit_date, visit_time); //to insert information in table
    }


    // to update table Appointments with date_time_busy=1
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

    // to update table Appointments with date_time_busy=0 if patient delete appointment
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

    // to update table Appointments with date_time_busy=0 if patient delete him salve from DB
    public static void updateAppointmentsWithNotBusyPatientDelete(Connection connection, String patient_person_code) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE patient_person_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setString(2, null);
            statement.setString(3, patient_person_code);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Appointment is updated with - not busy");
            }
        }
    }

    // to get all records with appointment available for Patient - for Patient choice
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

    // to get all records with appointment for Doctor
    // var skatities velidejas Melnraksti.printAllRecordDateTimeToDoctor
    public static void viewMyAppointmentDoctor(Connection connection, int id_doctor_code) throws SQLException {
        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = " + id_doctor_code + " AND date_time_busy = '1'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            //System.out.printf("List of all appointments for doctor with id %s: \n", id_doctor_code);
            while (resultSet.next()) {
                //int id_doctor_code = resultSet.getInt("id_doctor_code");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                //String patient_person_code = resultSet.getString("patient_person_code");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");

                System.out.printf("Date: %s, time: %s, patient: %s %s \n", visit_date, visit_time, patient_name, patient_surname);
            }
        }
    }

    // to get all records with appointment for Doctor for one day - today or selected day
    public static void viewAppointmentForOneDay(Connection connection, int id_doctor_code, Date dateDay) throws SQLException {
        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = " + id_doctor_code + " AND visit_date = '" + dateDay + "' AND date_time_busy = '1'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            //System.out.printf("On %d you have the following appointments", dateDay);
            while (resultSet.next()) {
                //int id_doctor_code = resultSet.getInt("id_doctor_code");
                //Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                //String patient_person_code = resultSet.getString("patient_person_code");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");
                System.out.printf("time: %s, patient: %s %s \n", visit_time, patient_name, patient_surname);
            }
        }
    }

    // to get all records with appointment Patient
    // vel japadarbojas ar:
    //  neizdevās arī sakārtot hronoloģiski, mainīju order by mainīgo secību, bet neizdevās
    public static void viewMyAppointmentPatient(Connection connection, String patient_person_code) throws SQLException {
        String query = "SELECT id_appointment, visit_date,visit_time, " +
                "patient_name, patient_surname, " +
                "doctor_medical_speciality, doctor_name, doctor_surname, doctor_room_number, doctor_visit_price " +
                "FROM appointment " +
                "INNER JOIN patient USING (patient_person_code) " +
                "INNER JOIN doctor USING (id_doctor_code) " +
                "WHERE patient_person_code = '" + patient_person_code + "' AND date_time_busy = '1' " +
                "ORDER BY id_appointment AND visit_date AND visit_time";
        //"ORDER BY visit_date AND visit_time DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("List of your appointment (s): \n");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Integer doctor_room_number = resultSet.getInt("doctor_room_number");
                Double doctor_visit_price = resultSet.getDouble("doctor_visit_price");

                System.out.printf("Appointment id: %s, Date and time of appointment: %s at %s, \n" +
                                "Patient: %s %s, \n" +
                                "Doctor (speciality): %s, %s (%s) \n" +
                                "Room: %s, \n" +
                                "Price of the appointment: %s eur \n" +
                                "----------------------------------\n",
                        id_appointment, visit_date, visit_time, patient_name, patient_surname,
                        doctor_name, doctor_surname, doctor_medical_speciality,
                        doctor_room_number, doctor_visit_price);
            }
        }
    }

}