package sda.group4.appointmentguru;

import java.sql.*;
import java.util.Scanner;

public class Patients {

    // to create table with Patients
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

    // to put information in Patient table
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

    // to delete information in Patient table
    public static void deleteRecordPatient(Connection connection, String patient_person_code) throws SQLException {
        String sql = "DELETE from patient where patient_person_code=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient_person_code);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.printf("Record about Patient with ID %s was successfully deleted \n", patient_person_code);
            }
        }
    }

    public static void insertPatientDetails(Connection connection, String patient_person_code) throws SQLException {
        Scanner scanner1 = new Scanner(System.in);
        //System.out.println("Enter patient person code: ");
        //String patient_person_code = scanner1.nextLine();
        System.out.println("Enter patient's name: ");
        String patient_name = scanner1.nextLine();
        System.out.println("Enter patient's surname: ");
        String patient_surname = scanner1.nextLine();
        System.out.println("Enter patient's phone number: ");
        String patient_phone_number = scanner1.nextLine();
        Patients.insertIntoPatientTable(connection, patient_person_code, patient_name, patient_surname, patient_phone_number);
    }

    public static void appointmentRequest(Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("What would you like to do? Please type (1-4): ");
            System.out.println("  1 - to see the details of all your appointments");
            // japadoma vai tikai tie, kas nakotne (1) ;? vai pavisu laiku visi - ari pagatne (5)
            System.out.println("  2 - to apply for an appointment;");
            System.out.println("  3 - to cancel an appointment;");
            System.out.println("  4 - to delete your data from the system.");
            //? 5 - to history of all yours appointments

            //enter choose what you will to do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int selectedChoose = scanner.nextInt();

            System.out.println("Please enter your personal code: ");
            String patient_person_code = scanner.next();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    //1 - to see all yours reserved appointments
                    Appointment.viewMyAppointmentPatient(connection, patient_person_code);
                    break;

                case 2:
                    //2 - to apply visit to a doctor
                    System.out.println("You can book an appointment by choosing a doctor and doctor's speciality from the list below: ");
                    Doctors.printAllRecordDoctor(connection);
                    //var darboties- mainit, tad veidot metodi. lai butu specialitate
                    System.out.println("To book an appointment please enter the doctor's ID code from a list: ");
                    int selected_id_doctor_code = scanner.nextInt();
                    //parbaude vai ir no saraksta
                    System.out.println("Please choose the appointment time from the list below: ");
                    //japadarbojas pie zemak minetas metodes, lai korektak izdot informciju
                    Appointment.printAllRecordDateTimeAvailable(connection, selected_id_doctor_code);
                    System.out.println("To book an appointment, please enter the ID code of the appointment from a list: ");
                    int selected_id_appointment = scanner.nextInt();
                    //te vajadzētu ievietot loop, lai pārbaudītu vai  tāds id eksistē
                    //parbaudi par ievaditais selected_id_appointment ir no tiem, kas pieejams,
                    //!!! jo paslaik raksta ari pa virsu, kr aiznemts

                    if (Melnraksti.isAppointmentFromSelectedDoctor(connection,selected_id_appointment,selected_id_doctor_code)!=1){
                        System.out.println("Sadly, in our system doesn't exist any appointment with this ID. Please type the ID code of the appointment from the list. ");
                    }else {
                        if (Melnraksti.isDateTimeBusyOrNot(connection, selected_id_appointment) == 1) {
                            System.out.println("Sadly, at the moment there is no available appointment with this ID. Please make sure to enter ID code from the list of available appointment times. Please type the ID code of the available appointment.");
                        } else {

                            //System.out.println("Enter date (YYYY-MM-DD): ");
                            //Date visit_date_toPutPatient = Date.valueOf(scanner.next());
                            //System.out.println("Enter start time (HH:MM:SS) :");
                            //Time visit_start_time_toPutPatient = Time.valueOf(scanner.next());

                            //to get new information from console about patient
                            Patients.insertPatientDetails(connection, patient_person_code);

                            //to update information in Appointment table
                            Appointment.updateAppointmentsWithPatientBusy(connection, selected_id_appointment, patient_person_code);
                            System.out.println("Thank you! You have successfully booked an appointment!");

                        }
                    }
                    break;

                case 3:
                    //3 - to delete visit to a doctor

                    //te vajadzētu ievietot loop, lai pārbaudītu vai  tāds patient_person_code eksistē appointment tabula
                    //daleji metode zemak atbild uz jautajumu augstak
                    Appointment.viewMyAppointmentPatient(connection, patient_person_code);

                    //var taisit izdzesanu uz appointment_id, bet jataisa parbaudi:
                    //parbaude vai pie appoinntment_id, kuru grib dzest ir vina personas kods
                    System.out.println("Please enter the information to cancel your visit. ");
                    System.out.println("Please enter the date of the visit which you would like to cancel (YYYY-MM-DD): ");
                    Date visit_date_toDeleteVisit = Date.valueOf(scanner.next());
                    System.out.println("Please enter the time of the visit which you would like to cancel (HH:MM:SS): ");
                    Time visit_time_toDeleteVisit = Time.valueOf(scanner.next());
                    //?+info par arstu - vai vajag, vai likt ievadit kaut-ko lai parbaudit

                    Appointment.updateAppointmentsWithNotBusy(connection, patient_person_code, visit_date_toDeleteVisit, visit_time_toDeleteVisit);
                    break;

                case 4:
                    //4 - to delete person from database;
                    Appointment.updateAppointmentsWithNotBusyPatientDelete(connection, patient_person_code);
                    Patients.deleteRecordPatient(connection, patient_person_code);
                    break;

                default:
                    // all other choose: perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number depending on what you would like to do - 1, 2, 3 or 4.");
            }
            System.out.println("If you would like to do more operations - press 1");
            runApplication = scanner.nextInt();
        }
    }


}