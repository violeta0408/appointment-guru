package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        //to get connection
        Connection connection = OperationFile.getConnection();

        if (connection == null) {
            System.out.println("You are not yet in our hospital application!");
            System.out.println("There is a problem and we can't get connection to the database!");
        } else {
            //We have connection with the database!!! Let's start!
            System.out.println("Welcome in our hospital application!");

            //create table for information about Doctors, who work in our hospital
            Doctors.createTableDoctor(connection);

            //create table for information about Appointments
            Appointment.createTableAppointments(connection);

            //create table for information about Patient
            Patients.createTablePatient(connection);

            System.out.println("To continue \n\tIf you are a Patient, please press --> '1'\n" +
                    "\tIf you are a Doctor, please press --> '2' \n" +
                    "\tIf you are a Database administrator - press --> '3'!");
            Scanner scanner = new Scanner(System.in);
            int role = scanner.nextInt();

            switch (role) {
                case 1: //Patient
                    Patients.patientRequest(connection);
                    break;
                case 2: //Doctor
                    Doctors.doctorRequest(connection);
                    break;
                case 3: //Database administrator
                    Administrator.administratorRequest(connection);
                    break;
                default: //all other cases - perform if and only if none of the above conditions are met
                    System.out.println("Unfortunately, you entered invalid number! \nI" +
                            "f you are a Patient, please press --> '1'\n" +
                            "If you are a Doctor, please press --> '2' \n" +
                            "If you are a Database administrator - press --> '3'! \n" +
                            "We apologise for the inconvenience.");
            }
            //delete table "doctor"; "patient"; "appointment":
            //OperationFile.deleteTable(connection, "doctor");
            //OperationFile.deleteTable(connection, "patient");
            //OperationFile.deleteTable(connection, "appointment");
        }

    }
}