package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = OperationFile.getConnection();

        if (connection == null) {
            System.out.println("You are not yet in our hospital application!");
            System.out.println("It is some problem and we can't get connection to the database!");
        } else {
            //We have get connection with the database!!! Let's start!
            System.out.println("Hello in our hospital application!");

            //create table for information about Doctors, who work in our hospital
            Doctors.createTableDoctor(connection);

            //create table for information about Appointments
            Appointment.createTableAppointments(connection);

            //create table for information about Patient
            Patients.createTablePatient(connection);

            // create table called Schedule
            Appointment.createTableSchedule(connection);
//            Appointment.printAllRecordDateTimeAvailable(connection,1);
//            Appointment.printAllRecordDateTimeToDoctor(connection,1);

//            To see doctor's appointments
//            Appointment.viewMyAppointmentDoctor(connection);
//            Appointment.viewAppointmentForToday(connection);
            /*
            //create table for information about Doctors, appointment Date, Time (start, end), information is DateTime busy(1) or not(null,0) and information about Patient
            OperationFile.createTableAppointment(connection);
            */

            //to check - what is in DB about Doctors
            //System.out.println("All records about Doctors in the database");
            //Doctors.printAllRecordDoctor(connection);

            /*
            //to check - what is in DB about Appointment
            System.out.println("All records about Appointment in the database");
            OperationFile.printAllRecordAppointment(connection);
            */

            System.out.println("Who are you: 1 - Patient; 2 - Doctor; 3 - Database administrator");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            int role = scanner.nextInt();

            switch (role) {
                case 1:
                    Patients.appointmentRequest(connection);
                    break;
                case 2:
                    Doctors.DoctorRequest(connection);
                    break;
                case 3:
                    Administrator.updateInformation(connection);
                    break;
                default:
                    //all other choose - perform if and only if none of the above conditions are met
                    System.out.println("Please enter a valid number - 1, 2 or 3.");
            }

            //to check - what is in DB about Doctor
            //System.out.println("All records about Doctor in the database");
            //Doctors.printAllRecordDoctor(connection);

            /*
            //to check - what is in DB about Appointment
            System.out.println("All records about Appointment in the database");
            OperationFile.printAllRecordAppointment(connection);
            */

            //delete table "doctor"
            //OperationFile.deleteTable(connection, "doctor");

            //delete table "patient"
            //OperationFile.deleteTable(connection, "patient");

            //delete table "appointment"
            //OperationFile.deleteTable(connection, "appointment");

            //delete table "schedule"
            //OperationFile.deleteTable(connection, "schedule");
        }
    }
}