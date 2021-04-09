package sda.group4.appointmentguru;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Patients {

    //to create table with information about Patients
    public static void createTablePatient(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS patient(" +
                "patient_person_code VARCHAR(200) PRIMARY KEY," +
                "patient_name VARCHAR(200)," +
                "patient_surname VARCHAR(200)," +
                "patient_phone_number VARCHAR(200)" +
                ")";
        //Try with resources
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    //to put information in Patient table
    public static void insertIntoPatientTable(Connection connection,
                                              String patient_person_code,
                                              String patient_name,
                                              String patient_surname,
                                              String patient_phone_number) throws SQLException {
        String sql = "INSERT INTO patient(patient_person_code, " +
                "patient_name, patient_surname, patient_phone_number) VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient_person_code);
            statement.setString(2, patient_name);
            statement.setString(3, patient_surname);
            statement.setString(4, patient_phone_number);
            boolean isSuccessful = statement.execute();
            if (isSuccessful) {
                System.out.println("Record about Patient was successfully inserted");
            }
        }
    }

    //to delete information in Patient table
    public static void deleteRecordPatient(Connection connection, String patient_person_code) throws SQLException {
        String sql = "DELETE from patient where patient_person_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient_person_code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Your personal information has been successfully deleted from our system.");
            } else {
                System.out.println("We don't have your personal information in our system.");
            }
        }
    }

    //to enter information about Patient, and later to put information in Patient table
    public static void insertPatientDetails(Connection connection, String patient_person_code) throws SQLException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String patient_name = scanner1.nextLine();
        System.out.println("Please enter your surname: ");
        String patient_surname = scanner1.nextLine();
        System.out.println("Please enter your phone number: ");
        String patient_phone_number = scanner1.nextLine();
        Patients.insertIntoPatientTable(connection, patient_person_code, patient_name, patient_surname, patient_phone_number);
    }

    //to check information about Patient: is Patient in DB (1) or not (0)
    public static int isPersonalCodeInDB(Connection connection, String patient_person_code_selected) throws SQLException {
        String sql = "SELECT patient_person_code from patient where patient_person_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient_person_code_selected);
            ResultSet resultSet = statement.executeQuery();
            int id_patient_result = 0;
            while (resultSet.next()) {
                String patient_person_code = resultSet.getString("patient_person_code");
                if (patient_person_code.equals(patient_person_code_selected)) {
                    id_patient_result = 1;
                } else {
                    id_patient_result=id_patient_result;
                }
            }
            return id_patient_result;
        }
    }


    //Patient can see his/her appointments, book an appointment, cancel his/her appointment and personal information from DB
    public static void patientRequest(Connection connection) throws SQLException {
        //to identify person
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your personal code: ");
        String patient_person_code = scanner.next();

        //to get today's date and time
        Date currentDate = Date.valueOf(LocalDate.now());
        Time currentTime = Time.valueOf(LocalTime.now());
        System.out.println(currentDate+"   "+currentTime);

        //start with while - to run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("What would you like to do? Please type (1-4): ");
            System.out.println("  1 - to see the details of all your appointment(s)");
            System.out.println("  2 - to apply for an appointment;");
            System.out.println("  3 - to cancel an appointment;");
            System.out.println("  4 - to delete your data from the system.");

            //enter choice what you would like to do in our application
            System.out.println("Enter your choice: ");
            int selectedChoice = scanner.nextInt();

            //switch case depends on the selectedChoice
            switch (selectedChoice) {
                case 1: //1 - to see all yours reserved appointments
                    Appointment.viewMyAppointmentPatientFuture(connection, patient_person_code, currentDate, currentTime);
                    System.out.println(" ");
                    Appointment.viewMyAppointmentPatientPast(connection, patient_person_code, currentDate, currentTime);
                    break;

                case 2: //2 - to book an appointment

                    //to get all records about Doctors, who work in our hospital
                    System.out.println("You can book an appointment by choosing a doctor from the list below: ");
                    Doctors.printAllRecordDoctor(connection);
                    //Possible improvement: start with Doctor's speciality

                    //to select the doctor from the list
                    System.out.println("To book an appointment please enter the doctor's ID code from a list: ");
                    int selected_id_doctor_code = scanner.nextInt();

                    //to check information about Doctor: is Doctor in DB (1) or not (0)
                    if (Doctors.isDoctorIDInDB(connection, selected_id_doctor_code) == 1) {

                        //to get all records with available appointments for Patient
                        System.out.println("Please choose the appointment from the list below: ");
                        Appointment.printAllRecordDateTimeAvailableInFuture(connection, selected_id_doctor_code,currentDate,currentTime);

                        //to select the appointment from the list
                        System.out.println("To book an appointment, please enter the ID code of the appointment from a list: ");
                        int selected_id_appointment = scanner.nextInt();

                        //to get information is appointment for selected doctor or not (if is=1, if not=0)
                        if (Appointment.isAppointmentForSelectedDoctor(connection, selected_id_appointment, selected_id_doctor_code) != 1) {
                            System.out.println("Sadly, in our system doesn't exist any appointment with this ID. Please type the ID code of the appointment from the list. ");
                        } else {
                            //to get information is date_time_busy or not (if busy=1; if not =0)
                            if (Appointment.isDateTimeBusyOrNot(connection, selected_id_appointment) == 1) {
                                System.out.println("Sadly, at the moment there is no available appointment with this ID. Please make sure to enter ID code from the list of available appointment times. Please type the ID code of the available appointment.");
                            } else {
                                //to get information about Patient: it exists in DB or not
                                //to get new information from console about patient if we don't have it in DB
                                if (Patients.isPersonalCodeInDB(connection, patient_person_code) != 1) {
                                    Patients.insertPatientDetails(connection, patient_person_code);
                                }
                                //to update information in Appointment table
                                Appointment.updateAppointmentsWithPatientBusy(connection, selected_id_appointment, patient_person_code);
                            }
                        }
                    } else {
                        System.out.println("Doctor with this ID is not in our Database!");
                    }
                    break;

                case 3: //3 - to delete an appointment

                    //to see all appointments reserved by the Patient
                    Appointment.viewMyAppointmentPatientFuture(connection, patient_person_code, currentDate, currentTime);

                    //to select the visit to cancel
                    System.out.println("Please enter the ID of the appointment which you would like to cancel: ");
                    int appointment_id_toDeleteVisit = scanner.nextInt();

                    //to update table Appointments with date_time_busy=0 if patient deletes appointment
                    Appointment.updateAppointmentsWithNotBusy(connection, patient_person_code, appointment_id_toDeleteVisit);
                    break;

                case 4: //4 - to delete person from database;

                    //to update table Appointments with date_time_busy=0 if patient delete himself from DB
                    Appointment.updateAppointmentsWithNotBusyPatientDelete(connection, patient_person_code);

                    //to delete information in Patient table
                    Patients.deleteRecordPatient(connection, patient_person_code);
                    break;

                default: //all other cases: perform if and only if none of the above conditions are met
                    System.out.println("Sadly, you entered incorrect number. Please enter a valid number depending on what you would like to do - 1, 2, 3 or 4.");
            }
            System.out.println("If you would like to do more operations - press '1'");
            runApplication = scanner.nextInt();
        }
    }

}