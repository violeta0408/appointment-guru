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

            System.out.println("To continue if you are a Patient, please press --> '1'\nIf you are a Doctor, please press --> '2' \nIf you are a Database administrator - press --> '3'!");
            Scanner scanner = new Scanner(System.in);
            int role = scanner.nextInt();

            switch (role) {
                case 1:
                    Patients.patientRequest(connection);
                    break;
                case 2:
                    Doctors.doctorRequest(connection);
                    break;
                case 3:
                    Administrator.administratorRequest(connection);
                    break;
                default:
                    //all other choose - perform if and only if none of the above conditions are met
                    System.out.println("Unfortunately, you entered invalid number! \nIf you are a Patient, please press --> '1'\nIf you are a Doctor, please press --> '2' \nIf you are a Database administrator - press --> '3'! \nWe apologise for the inconvenience.");
            }

            //delete table "doctor"; "patient"; "appointment":
            //OperationFile.deleteTable(connection, "doctor");
            //OperationFile.deleteTable(connection, "patient");
            //OperationFile.deleteTable(connection, "appointment");

        }
    }
}