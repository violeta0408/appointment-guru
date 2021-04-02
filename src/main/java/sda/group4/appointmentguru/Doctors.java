package sda.group4.appointmentguru;

import java.sql.*;
import java.util.Scanner;

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

    // to get all records about Doctors, who work in our hospital
    public static void printAllRecordDoctor(Connection connection) throws SQLException {
        String sql = "SELECT * from doctor order by doctor_medical_speciality";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("id_doctor_code  |  doctor_medical_speciality  |  "
                    + "doctor_name  |  doctor_surname  |  doctor_room_number  |  "
                    + "doctor_work_start_time  |  doctor_work_end_time  |  doctor_visit_price");
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


    public static void insertDoctorDetails(Connection connection) throws SQLException {
        //to get information from console about doctor
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter doctor's medical speciality:");
        String doctor_medical_speciality = scanner1.nextLine();
        System.out.println("Enter doctor's name:");
        String doctor_name = scanner1.nextLine();
        System.out.println("Enter doctor's surname:");
        String doctor_surname = scanner1.nextLine();
        System.out.println("Enter doctor's room number:");
        Integer doctor_room_number = scanner1.nextInt();
        System.out.println("Enter the start time of the doctor's working hours (hh:mm:ss): ");
        Time doctor_work_start_time = Time.valueOf(scanner1.next());
        System.out.println("Enter the end time of the doctor's working hours (hh:mm:ss): ");
        Time doctor_work_end_time = Time.valueOf(scanner1.next());
        System.out.println("Enter the price of appointment:");
        Double doctor_visit_price = scanner1.nextDouble();

        //to load information about a doctor to Doctor table
        System.out.println("Information about doctor is loading in database");
        Doctors.insertIntoDoctorTable(connection,
                doctor_medical_speciality,
                doctor_name, doctor_surname,
                doctor_room_number,
                doctor_work_start_time,
                doctor_work_end_time,
                doctor_visit_price); //insert information in table

    }

    public static void updateDoctorDetails(Connection connection) throws SQLException {                    //te var būt izvēle par to, kuru informāciju vadisim pa jaunu
        //to update information in Doctor table, if it was some mistake before
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter doctor id code, to update records about doctor: ");
        int doctorIdToUpdate = scanner.nextInt();

        //parbaudit vai pieejams ar tadu id, vai ari prasit vadit ne id, bet ko citu - ko?, id, laikam ok

        //to get new information from console about doctor
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter doctor's medical speciality:");
        String doctor_medical_speciality_Update = scanner2.nextLine();
        System.out.println("Enter doctor's name:");
        String doctor_name_Update = scanner2.nextLine();
        System.out.println("Enter doctor's surname:");
        String doctor_surname_Update = scanner2.nextLine();
        System.out.println("Enter doctor's room number:");
        Integer doctor_room_number_Update = scanner2.nextInt();
        System.out.println("Enter the start time of the doctor's working hours (hh:mm:ss): ");
        Time doctor_work_start_time_Update = Time.valueOf(scanner2.next());
        System.out.println("Enter the end time of the doctor's working hours (hh:mm:ss): ");
        Time doctor_work_end_time_Update = Time.valueOf(scanner2.next());
        System.out.println("Enter the price of appointment:");
        Double doctor_visit_price_Update = scanner2.nextDouble();

        // to update information in Doctor table, if it was some mistake before
        Doctors.updateRecordDoctor(connection,
                doctor_medical_speciality_Update,
                doctor_name_Update,
                doctor_surname_Update,
                doctor_room_number_Update,
                doctor_work_start_time_Update,
                doctor_work_end_time_Update,
                doctor_visit_price_Update,
                doctorIdToUpdate);
    }

    public static void DoctorRequest(Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("Please type (1-2) what would you like to do: ");
            System.out.println("  1 - to see all appointment as a doctor;");
            System.out.println("  2 - to see next appointment details as a doctor.");

            //enter choose what you will to do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int selectedChoose = scanner.nextInt();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    //1 - to see all appointment as a doctor
                    System.out.println("In our hospital work the doctors from a list: ");
                    Doctors.printAllRecordDoctor(connection);
                    System.out.println("Please enter yours id_doctor_code (from a list): ");
                    int selected_id_doctor_code = scanner.nextInt();
                    System.out.println("Yours all appointment as a doctor: ");
                    Appointment.printAllRecordDateTimeToDoctor(connection, selected_id_doctor_code);
                    break;

                case 2:
                    //2 - to see next appointment details as a doctor
                    System.out.println("In our hospital work the doctors from a list: ");
                    Doctors.printAllRecordDoctor(connection);
                    System.out.println("Please enter yours id_doctor_code (from a list): ");
                    int selected_id_doctor_code2 = scanner.nextInt();
                    System.out.println("Yours next appointment as a doctor: ");
                    //te, jāsataisa, lai būtu next appointment
                    //Appointment.printAllRecordDateTimeToDoctor(connection, selected_id_doctor_code2);
                    break;

                default:
                    // all other choose: perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number - 1 or 2.");
            }
            System.out.println("Will you do some more operation. If yes - press 1");
            runApplication = scanner.nextInt();
        }
    }

}
