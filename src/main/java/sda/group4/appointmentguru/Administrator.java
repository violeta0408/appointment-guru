package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {

    public static void administratorRequest (Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("What would you like to do? Please type (1-4):");
            System.out.println("1 - to insert a new information about a doctor in the database;");
            System.out.println("2 - to make changes in the information about a doctor in the database;");
            System.out.println("3 - to delete information about a doctor in database;");
            System.out.println("4 - to create a record in the database about available appointment time.");

            // choose what you will do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int selectedChoose = scanner.nextInt();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    // 1 - to put new information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    //to insert information about Doctor
                    Doctors.insertDoctorDetails(connection);
                    break;

                case 2:
                    //2 - to make changes in information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    //to update information about Doctor
                    Doctors.updateDoctorDetails(connection);
                    break;

                case 3:
                    //3 - to delete information about a doctor in database

                    //to get all information about Doctors, who work in our hospital
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);

                    //to delete information from Doctor and Appointment table, if Doctor doesn't work more
                    System.out.println("To delete records about the doctor, please enter doctor's ID code");
                    int idDoctorCodeToDelete = scanner.nextInt();
                    Doctors.deleteRecordDoctor(connection, idDoctorCodeToDelete);

                    //to get all information about Doctors, who work in our hospital, after deleting
                    System.out.println("All records in the database");
                    Doctors.printAllRecordDoctor(connection);
                    break;

                case 4:
                    // 4 - to create an available appointment records in the appointment table
                    Appointment.insertInfoIntoAppointment(connection);
                    break;

                default:
                    //all other choose - perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number - 1, 2, 3 or 4.");
            }
            System.out.println("If you would like to do more operations - press 1");
            runApplication = scanner.nextInt();
        }
    }

}
