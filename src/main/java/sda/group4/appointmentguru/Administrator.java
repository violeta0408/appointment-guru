package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {

    public static void updateInformation(Connection connection) throws SQLException {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("Please type (1-4) what would you like to do: ");
            System.out.println("1 - to put a new information about a doctor in the database;");
            System.out.println("2 - to make changes in the information about a doctor in the database;");
            System.out.println("3 - to delete information about a doctor in database;");
            System.out.println("4 - to create an available appointment time in the schedule");

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

                case 4:
                    // 4 - to create a schedule with available appointment times
                    Appointment.insertInfoIntoSchedule(connection);


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
