package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class Administrator {

    public static void updateInformation(Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("Please type (1-3) what would you like to do: ");
            System.out.println("  1 - to put new information about a doctor in database;");
            System.out.println("  2 - to make changes in information about a doctor in database;");
            System.out.println("  3 - to delete information about a doctor in database;");

            //enter choose what you will to do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choose: ");
            int selectedChoose = scanner.nextInt();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    // 1 - to put new information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

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

                    break;
                case 2:
                    //2 - to make changes in information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    //to update information in Doctor table, if it was some mistake before
                    System.out.println("Please enter doctor id code, to update records about doctor: ");
                    int doctorIdToUpdate = scanner.nextInt();

                    //parbaudit vai pieejams ar tadu id, vai ari prasit vadit ne id, bet ko citu - ko?, id, laikam ok

                    //te var būt izvēle par to, kuru informāciju vadisim pa jaunu
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

                    //pec tam, kad ieraksts par arstu atjaunots, to jaatjauno ari lielas tabula, kur glabajas viss ar pierakstiem

                    break;
                case 3:
                    //3 - to delete information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    /*
                    //to get all information about Appointment
                    System.out.println("All records in the database");
                    OperationFile.printAllRecordAppointment(connection);
                    */

                    //to delete information from Doctor and Appointment table, if Doctor doesn't work more
                    System.out.println("Please enter doctor id code, to delete records about doctor: ");
                    int idDoctorCodeToDelete = scanner.nextInt();
                    Doctors.deleteRecordDoctor(connection, idDoctorCodeToDelete);
                    /*
                    OperationFile.deleteRecordAppointment(connection, idDoctorCodeToDelete);
                     */

                    //to get all information about Doctors, who work in our hospital, after deleting
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    /*
                    //to get all information about Appointment, after deleting
                    System.out.println("All records in the database");
                    OperationFile.printAllRecordAppointment(connection);
                    */
                    break;
                default:
                    //all other choose
                    // perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number - 1, 2 or 3.");
            }
            System.out.println("Will you do some more operation. If yes - press 1");
            runApplication = scanner.nextInt();

        }
    }

}
