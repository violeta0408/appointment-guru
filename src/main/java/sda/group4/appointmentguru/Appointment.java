package sda.group4.appointmentguru;

import java.sql.*;
import java.util.Scanner;

public class Appointment {

    //to create table with Appointments
    public static void createTableAppointments(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS appointment(" +
                "id_appointment INT AUTO_INCREMENT PRIMARY KEY," +
                "id_doctor_code INT," +
                "visit_date DATE," +
                "visit_time TIME," +
                "date_time_busy INT DEFAULT 0," +
                "patient_person_code VARCHAR(200)" +
                ")";
        //Try with resources
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    //to put information in the Appointment table
    public static void insertInfoIntoTableAppointment(Connection connection,
                                                      Integer id_doctor_code,
                                                      Date visit_date,
                                                      Time visit_time) throws SQLException {
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

    //to enter information in Appointment Table and later to put information in the Appointment table
    //Possible improvement: make this automated
    //Possible improvement: to check if inserted Time is within Doctor's working hours
    public static void insertInfoIntoAppointment(Connection connection) throws SQLException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Please enter the doctor's ID code");
        Integer id_doctor_code = scanner1.nextInt();
        System.out.println("Please enter the date (YYYY-MM-DD) of the appointment");
        Date visit_date = Date.valueOf(scanner1.next());
        System.out.println("Please enter the time (HH:MM:SS) of the appointment");
        Time visit_time = Time.valueOf(scanner1.next());
        //to insert information about the available appointment times in the Appointment table
        System.out.println("Information about the available appointment times is loaded in the database");
        Appointment.insertInfoIntoTableAppointment(connection, id_doctor_code, visit_date, visit_time);
    }

    //to update table Appointments with date_time_busy=1
    public static void updateAppointmentsWithPatientBusy(Connection connection, int id_appointment, String patient_person_code) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE id_appointment=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            statement.setString(2, patient_person_code);
            statement.setInt(3, id_appointment);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("You have successfully booked your appointment!");
            } else {
                System.out.println("It was not possible to book your appointment!");
            }
        }
    }

    //to update table Appointments with date_time_busy=0 if patient deletes appointment
    public static void updateAppointmentsWithNotBusy(Connection connection, String patient_person_code, int id_appointment) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE patient_person_code=? AND id_appointment=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setString(2, null);
            statement.setString(3, patient_person_code);
            statement.setInt(4, id_appointment);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Your appointment has been canceled!");
            } else {
                System.out.println("You entered incorrect appointment ID. ");
            }
        }
    }

    //to update table Appointments with date_time_busy=0 if patient deletes himself from DB
    public static void updateAppointmentsWithNotBusyPatientDelete(Connection connection, String patient_person_code) throws SQLException {
        String sql = "UPDATE appointment SET date_time_busy=?, patient_person_code=? WHERE patient_person_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setString(2, null);
            statement.setString(3, patient_person_code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Information about your appointments have been deleted from our system.");
            } else {
                System.out.println("We don't have records about your appointments to delete.");
            }
        }
    }

    //to get all records with available appointments for Patient (only future)
    public static void printAllRecordDateTimeAvailableInFuture(Connection connection, int selected_id_doctor_code, Date currentDay, Time currentTime) throws SQLException {
        String sql = "SELECT appointment.id_appointment, " +
                "doctor.doctor_medical_speciality, doctor.doctor_name, doctor.doctor_surname, " +
                "appointment.visit_date, appointment.visit_time FROM appointment INNER JOIN doctor " +
                "ON appointment.id_doctor_code=doctor.id_doctor_code " +
                "WHERE appointment.date_time_busy=0 and appointment.id_doctor_code=? " +
                " AND ((appointment.visit_date = '" + currentDay + "' AND appointment.visit_time >= '" + currentTime + "') OR (appointment.visit_date> '" + currentDay +
                "')) ORDER BY appointment.visit_date ASC, appointment.visit_time ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, selected_id_doctor_code);
            ResultSet resultSet = statement.executeQuery();
            System.out.print("\033[4;1;255m");
            System.out.println("Appointment ID  |  Doctor's speciality  |  Doctor's name and surname  | Appointment date  |  Appointment time  ");
            System.out.print("\033[0m");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                System.out.printf("  \t %-14s %-24s %-9s %-18s %-20s %-20s\n", id_appointment, doctor_medical_speciality, doctor_name, doctor_surname, visit_date, visit_time);
            }
        }
    }

    //to get all records with appointment for Doctor (only future)
    public static void viewMyAppointmentDoctorInFuture(Connection connection, int id_doctor_code, Date currentDay, Time currentTime) throws SQLException {
        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = " + id_doctor_code + " AND date_time_busy = '1'" +
                " AND ((appointment.visit_date = '" + currentDay + "' AND appointment.visit_time >= '" + currentTime + "') OR (appointment.visit_date> '" + currentDay +
                "')) ORDER BY visit_date ASC, visit_time ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("List of all appointments for doctor with ID %s: \n", id_doctor_code);
            System.out.print("\033[4;1;255m");
            System.out.println("\t   Date    |    Time    |    Patient's name and surname      ");
            System.out.print("\033[0m");
            while (resultSet.next()) {
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                //String patient_person_code = resultSet.getString("patient_person_code");
                String patient_name = resultSet.getString("patient_name");
                String patient_surname = resultSet.getString("patient_surname");
                System.out.printf("\t%-14s %-15s %-10s %-14s\n", visit_date, visit_time, patient_name, patient_surname);
            }
        }
    }

    //to get all records with appointment for Doctor for one day - today or selected day (past and future)
    public static void viewAppointmentForOneDayDoctor(Connection connection, int id_doctor_code, Date dateDay) throws SQLException {
        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = " + id_doctor_code + " AND visit_date = '" + dateDay + "' AND date_time_busy = '1'" +
                "ORDER BY visit_time ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            boolean notEmptyRecord = resultSet.next();
            if (!notEmptyRecord) {
                System.out.printf("There is no appointment on %s\n", dateDay);
            } else {
                System.out.print("\033[4;1;255m");
                System.out.println("    Time    |    Patient's name and surname    ");
                System.out.print("\033[0m");
                while (notEmptyRecord) {
                    Time visit_time = resultSet.getTime("visit_time");
                    //String patient_person_code = resultSet.getString("patient_person_code");
                    String patient_name = resultSet.getString("patient_name");
                    String patient_surname = resultSet.getString("patient_surname");
                    System.out.printf("  %-14s %-10s %-15s\n", visit_time, patient_name, patient_surname);
                    notEmptyRecord = resultSet.next();
                }
            }
        }
    }


    //to get the next Appointment details for the Doctor
    public static void viewNextAppointmentForDoctor(Connection connection, int id_doctor_code, Date currentDay, Time currentTime) throws SQLException {
        String query = "SELECT visit_date, visit_time, patient_name, patient_surname " +
                "FROM appointment " +
                "INNER JOIN patient " +
                "ON appointment.patient_person_code = patient.patient_person_code " +
                "WHERE id_doctor_code = " + id_doctor_code +
                " AND ((visit_date = '" + currentDay + "' AND visit_time >= '" + currentTime + "') OR (visit_date> '" + currentDay +
                "')) AND date_time_busy = '1' " +
                "ORDER BY visit_date ASC, visit_time ASC " +
                "LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            boolean notEmptyRecord = resultSet.next();
            if (!notEmptyRecord) {
                System.out.printf("You don't have any next appointment on %s after %s\n", currentDay, currentTime);
            } else {
                System.out.print("\033[4;1;255m");
                System.out.println("    Date      |    Time    |    Patient's name and surname    ");
                System.out.print("\033[0m");
                while (notEmptyRecord) {
                    Date visit_date = resultSet.getDate("visit_date");
                    Time visit_time = resultSet.getTime("visit_time");
                    String patient_name = resultSet.getString("patient_name");
                    String patient_surname = resultSet.getString("patient_surname");
                    System.out.printf("  %-14s %-15s %-10s %-14s\n", visit_date, visit_time, patient_name, patient_surname);
                    notEmptyRecord = resultSet.next();
                }
            }
        }
    }

    //to get all appointment records for the Patient (future)
    public static void viewMyAppointmentPatientFuture(Connection connection, String patient_person_code, Date currentDay, Time currentTime) throws SQLException {
        String query = "SELECT id_appointment, visit_date,visit_time, " +
                "patient_name, patient_surname, " +
                "doctor_medical_speciality, doctor_name, doctor_surname, doctor_room_number, doctor_visit_price " +
                "FROM appointment " +
                "INNER JOIN patient USING (patient_person_code) " +
                "INNER JOIN doctor USING (id_doctor_code) " +
                "WHERE patient_person_code = '" + patient_person_code +
                "' AND ((visit_date = '" + currentDay + "' AND visit_time >= '" + currentTime + "') OR (visit_date> '" + currentDay +
                "')) AND date_time_busy = '1' " +
                "ORDER BY visit_date ASC, visit_time ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Your future appointment(s): ");
            System.out.print("\033[4;1;255m");
            System.out.println("Appointment ID  |  Date of the appointment  |  Time of the appointment   |   Doctor's name and surname  |  Doctor's speciality  |  Room number |  Price for the appointment");
            System.out.print("\033[0m");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Integer doctor_room_number = resultSet.getInt("doctor_room_number");
                Double doctor_visit_price = resultSet.getDouble("doctor_visit_price");
                System.out.printf(" \t %-20s %-25s %-25s %-10s %-19s %-24s %-18s %s EUR\n", id_appointment, visit_date, visit_time, doctor_name, doctor_surname,
                        doctor_medical_speciality, doctor_room_number, doctor_visit_price);
            }
        }
    }

    //to get all appointment records for the Patient (past)
    public static void viewMyAppointmentPatientPast(Connection connection, String patient_person_code, Date currentDay, Time currentTime) throws SQLException {
        String query = "SELECT id_appointment, visit_date,visit_time, " +
                "patient_name, patient_surname, " +
                "doctor_medical_speciality, doctor_name, doctor_surname, doctor_room_number, doctor_visit_price " +
                "FROM appointment " +
                "INNER JOIN patient USING (patient_person_code) " +
                "INNER JOIN doctor USING (id_doctor_code) " +
                "WHERE patient_person_code = '" + patient_person_code +
                "' AND ((visit_date = '" + currentDay + "' AND visit_time < '" + currentTime + "') OR (visit_date< '" + currentDay +
                "')) AND date_time_busy = '1' " +
                "ORDER BY visit_date ASC, visit_time ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Your past appointment(s): ");
            System.out.print("\033[4;1;255m");
            System.out.println("Appointment ID  |  Date of the appointment  |  Time of the appointment   |   Doctor's name and surname  |  Doctor's speciality  |  Room number |  Price for the appointment");
            System.out.print("\033[0m");
            while (resultSet.next()) {
                Integer id_appointment = resultSet.getInt("id_appointment");
                Date visit_date = resultSet.getDate("visit_date");
                Time visit_time = resultSet.getTime("visit_time");
                String doctor_medical_speciality = resultSet.getString("doctor_medical_speciality");
                String doctor_name = resultSet.getString("doctor_name");
                String doctor_surname = resultSet.getString("doctor_surname");
                Integer doctor_room_number = resultSet.getInt("doctor_room_number");
                Double doctor_visit_price = resultSet.getDouble("doctor_visit_price");
                System.out.printf(" \t %-20s %-25s %-25s %-10s %-19s %-24s %-18s %s EUR\n ", id_appointment, visit_date, visit_time, doctor_name, doctor_surname,
                        doctor_medical_speciality, doctor_room_number, doctor_visit_price);
            }
        }
    }

    //to get information: is date_time_busy or not (if busy=1; if not =0)
    public static int isDateTimeBusyOrNot(Connection connection, int id_appointment) throws SQLException {
        String sql = "SELECT date_time_busy from appointment where id_appointment=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_appointment);
            ResultSet resultSet = statement.executeQuery();
            int date_time_busy_result = 0;
            while (resultSet.next()) {
                int date_time_busy = resultSet.getInt("date_time_busy");
                if (date_time_busy == 1) {
                    date_time_busy_result = 1;
                } else {
                    date_time_busy_result = date_time_busy_result;
                }
            }
            return date_time_busy_result;
        }
    }

    //to get information: is appointment for selected doctor or not (if is=1, if not=0)
    public static int isAppointmentForSelectedDoctor(Connection connection, int id_appointment, int id_doctor_code_selected) throws SQLException {
        String sql = "SELECT id_doctor_code from appointment where id_appointment=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_appointment);
            ResultSet resultSet = statement.executeQuery();
            int id_doctor_code_result = 0;
            while (resultSet.next()) {
                int id_doctor_code = resultSet.getInt("id_doctor_code");
                if (id_doctor_code == id_doctor_code_selected) {
                    id_doctor_code_result = 1;
                } else {
                    id_doctor_code_result = id_doctor_code_result;
                }
            }
            return id_doctor_code_result;
        }
    }

}