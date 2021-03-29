package sda.group4.appointmentguru;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class Patients {

    public static void appointmentRequest(Connection connection) {
        //start with while - to can run application many times
        int runApplication = 1;
        while (runApplication == 1) {
            //information about what is possible to do in our application
            System.out.println("Please type (1-2) what would you like to do: ");
            System.out.println("  1 - to apply for an appointment;");
            System.out.println("  2 - to delete an appointment.");

            //enter choose what you will to do in our application
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choose: ");
            int selectedChoose = scanner.nextInt();

            //operation after selectedChoose depend of choose - start process
            switch (selectedChoose) {
                case 1:
                    //1 - to apply visit to a doctor

                    // daktera, dienas un laika izvele - jaizveido
                    // !!! te jadarbojas, ka veidot izveli
                    // padaidam ta, bet jaliek parbaude
                    // ? pielikt par dakteri

                    System.out.println("Enter date: ");
                    Date visit_date_toPutPatient = Date.valueOf(scanner.next());
                    System.out.println("Enter start time: ");
                    Time visit_start_time_toPutPatient = Time.valueOf(scanner.next());

                    //to get new information from console about patient
                    System.out.println("Enter patient person code: ");
                    String patient_person_code_toPutPatient = scanner.next();
                    System.out.println("Enter patient name: ");
                    String patient_name_toPutPatient = scanner.next();
                    System.out.println("Enter patient surname: ");
                    String patient_surname_toPutPatient = scanner.next();
                    System.out.println("Enter patient phone number: ");
                    String patient_phone_number_toPutPatient = scanner.next();

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

                    System.out.println("Enter date when visit was planed: ");
                    Date visit_date_toDeletePatient = Date.valueOf(scanner.next());
                    System.out.println("Enter visit start time: ");
                    Time visit_start_time_toDeletePatient = Time.valueOf(scanner.next());

                    //? vai vajag un ko no ta vajag - vai so izmantot parbaudei
                    //to get new information from console about patient
                    System.out.println("Enter patient person code: ");
                    String patient_person_code_toDeletePatient = scanner.next();
                    System.out.println("Enter patient name: ");
                    String patient_name_toDeletePatient = scanner.next();
                    System.out.println("Enter patient surname: ");
                    String patient_surname_toDeletePatient = scanner.next();
                    System.out.println("Enter patient phone number: ");
                    String patient_phone_number_toDeletePatient = scanner.next();

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