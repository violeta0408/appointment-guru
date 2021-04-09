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
                case 1: //Patient - can see his/her appointments, book an appointment, cancel his/her appointment and personal information from DB;
                    Patients.patientRequest(connection);
                    break;
                case 2: //Doctor - can see his/her all appointments, appointments for today, for any selected day and next appointment
                    Doctors.doctorRequest(connection);
                    break;
                case 3: //Database administrator - can insert, update and delete info in DB about the doctors and enter in the system info about the available appointments
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