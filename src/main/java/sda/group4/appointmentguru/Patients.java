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
                System.out.printf("Record about Patient with id %s was successfully deleted \n", patient_person_code);
            }
        }
    }

    public static void insertPatientDetails (Connection connection) throws SQLException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter patient person code: ");
        String patient_person_code = scanner1.nextLine();
        System.out.println("Enter patient name: ");
        String patient_name = scanner1.nextLine();
        System.out.println("Enter patient surname: ");
        String patient_surname = scanner1.nextLine();
        System.out.println("Enter patient phone number: ");
        String patient_phone_number = scanner1.nextLine();
        Patients.insertIntoPatientTable(connection, patient_person_code, patient_name, patient_surname, patient_phone_number);
    }

    public static void appointmentRequest(Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("Please type (1-2) what would you like to do: ");
            System.out.println("  1 - to apply for an appointment;");
            System.out.println("  2 - to delete an appointment.");

            //enter choose what you will to do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int selectedChoose = scanner.nextInt();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    //1 - to apply visit to a doctor

                    // daktera, dienas un laika izvele - jaizveido
                    // !!! te jadarbojas, ka veidot izveli
                    // padaidam ta, bet jaliek parbaude
                    // ? pielikt par dakteri

                    System.out.println("Enter date (YYYY-MM-DD): ");
                    Date visit_date_toPutPatient = Date.valueOf(scanner.next());
                    System.out.println("Enter start time (HH:MM:SS) :");
                    Time visit_start_time_toPutPatient = Time.valueOf(scanner.next());

                    //to get new information from console about patient
                    Patients.insertPatientDetails(connection);

                    //pec ievadisanas, informaciju par pacientu jaieliek liela tabula
                    //var ievest 1,0 - laiks pieejams, aiznemts un mainot info saja lauka - so jaskaas ka sanaca realizet

                    //OperationFile.updateAppointmentPatient(connection, patient_person_code_toPutPatient, patient_name_toPutPatient, patient_surname_toPutPatient, patient_phone_number_toPutPatient, visit_date_toPutPatient, visit_start_time_toPutPatient);

                    break;

                case 2:
                    //2 - to delete visit to a doctor

                    // prasit ievadit kadu informaciju un balstoties uz to meklet info liela tabula un izdzest info kados laukos, kas ir par pacientu un nomainit (1 uz 0) -laiks pieejams

                    // !!! te jadarbojas un japadoma ka veidot izveli
                    //dienas un laika norade - jaizveido
                    //info par pacientu, par arstu - vai vajag, vai likt ievadit kaut-ko lai parbaudit
                    // ? pielikt par dakteri
                    // padaidam ta

                    System.out.println("Please enter the date of the visit which you would like to cancel (YYYY-MM-DD): ");
                    Date visit_date_toDeletePatient = Date.valueOf(scanner.next());
                    System.out.println("Please enter the time of the visit which you would like to cancel (HH:MM:SS): ");
                    Time visit_start_time_toDeletePatient = Time.valueOf(scanner.next());

                    //? vai vajag un ko no ta vajag - vai so izmantot parbaudei
                    //to get new information from console about patient

                    System.out.println("Please enter yours person code: ");
                    String patient_person_code = scanner.nextLine();
                    //te butu jaizdzes pierakstu deleteRecordAppointment
                    Patients.deleteRecordPatient(connection,patient_person_code);

                    //pec ievadisanas, informaciju par pacientu jaieliek liela tabula
                    //var ievest 1,0 - laiks pieejams, aiznemts un mainot info saja lauka - so jaskaas ka sanaca realizet

                    //OperationFile.updateAppointmentPatientDelete(connection, patient_person_code_toDeletePatient, patient_name_toDeletePatient, patient_surname_toDeletePatient, patient_phone_number_toDeletePatient, visit_date_toDeletePatient, visit_start_time_toDeletePatient);

                    break;
                default:
                    //all other choose
                    // perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number - 1 or 2.");
            }
            System.out.println("Will you do some more operation. If yes - press 1");
            runApplication = scanner.nextInt();
        }
    }

}