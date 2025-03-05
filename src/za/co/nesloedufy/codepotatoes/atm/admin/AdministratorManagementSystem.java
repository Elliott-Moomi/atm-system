package za.co.nesloedufy.codepotatoes.atm.admin;

import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidLoginException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.PinMismatchException;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.ATM;
import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdministratorManagementSystem implements AdministratorInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<Integer, Employee> employeeMap;

    private static List<ATM> atmList = new ArrayList<>();//Everything related to this only exists for presentation purposes due to lack of time
                                                         //to implement the desired ones and waiting for other resources from other members.
    private int employeeId = 0;

    public AdministratorManagementSystem() {
        employeeMap = new HashMap<>();
        atmList.add(new ATM(1, "CP Bank",175000,"Midrand,South Africa","Active"));
        atmList.add(new ATM(2, "CP Bank",175000,"Pretoria,South Africa","Inactive"));
        atmList.add(new ATM(3, "CP Bank",175000,"Midrand,South Africa","Active"));
        readFromDatabase();
    }

    public void storeToDatabase(){
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/resources/EmployeeDatabase.dat"))){
            os.writeObject(employeeMap);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public void readFromDatabase(){
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream("src/resources/EmployeeDatabase.dat"))){
           int maxValue = 0;
           Map<Integer,Employee> storedEmployees = (Map<Integer, Employee>) oi.readObject();
           for (Map.Entry<Integer,Employee> entry:storedEmployees.entrySet()){
               if(entry.getKey() > maxValue){
                   maxValue = entry.getKey();
               }
               employeeMap.put(entry.getKey(),entry.getValue());
           }

            employeeId = maxValue;
        }catch (EOFException e){
            System.err.println("Error: End of file");
        }catch (IOException |ClassNotFoundException | ClassCastException e){
            System.err.println(e.getMessage());
        }
    }
    public void employeeLogin(){
        long cardNumber;
        long defaultCardNumber = 1111;
        int defaultPin = 2244;

        while (true) {
            try {
                System.out.println("Enter card number or press -1 to exit");
                cardNumber = scanner.nextLong();

                if(cardNumber == -1){
                    System.out.println("System logged you out");
                    System.exit(0);

                }else if(cardNumber == defaultCardNumber){
                    break;

                }else if (!(AdminValidators.verifyCardNumber(cardNumber))){
                    throw new InvalidLoginException("Error: Invalid card Number!!!Please try again");

                }else if(AdminValidators.checkCardStatus(cardNumber)){
                    throw new InvalidLoginException("Error: Card is frozen!!!Visit bank for assistance");

                }else {
                    break;
                }

            }catch (InvalidLoginException e){
                System.err.println(e.getMessage());
            }catch (InputMismatchException e){
                scanner.next();
                System.err.println("Error: Invalid type!!!Please try again");
            }

        }


        int userPin;
        int attempt = 3;

        while (true) {
            try {
                System.out.println("Enter pin");
                userPin = scanner.nextInt();


                if(cardNumber == defaultCardNumber && userPin == defaultPin){
                    break;
                }else if(AdminValidators.verifyPin(cardNumber,userPin)) {
                    System.out.println("Login successful");
                    break;

                }else if(attempt == 1){
                    AdministratorManagementSystem.freezeCard(cardNumber);
                    System.out.println("Card frozen,visit bank for further assistance!!");
                    storeToDatabase();
                    System.exit(0);
                }else{
                    attempt--;
                    System.out.println("Invalid pin,please try again");
                    System.out.println("Attempts left:"+attempt);
                }
            }catch (InputMismatchException e) {
                scanner.next();
                System.err.println("Error: Invalid type!!!Please try again");
            }

        }
    }
    public void adminLogin(){
        long cardNumber;
        long defaultCardNumber = 1111;
        int defaultPin = 2244;

        while (true) {
            try {
                System.out.println("Enter card number or press -1 to exit");
                cardNumber = scanner.nextLong();

                if(cardNumber == -1){
                    System.out.println("System logged you out");
                    System.exit(0);

                }else if(cardNumber == defaultCardNumber){
                    break;

                }else if (!(AdminValidators.verifyCardNumber(cardNumber))){
                    throw new InvalidLoginException("Error: Invalid card Number!!!Please try again");

                }else if(AdminValidators.checkCardStatus(cardNumber)){
                    throw new InvalidLoginException("Error: Card is frozen!!!Visit bank for assistance");

                } else if (!(AdminValidators.verifyRole(cardNumber))) {
                    throw new InvalidLoginException("Error: Access denied!!!Only administrators are allowed to access this part of the system.");
                } else {
                    break;
                }

            }catch (InvalidLoginException e){
                System.err.println(e.getMessage());
            }catch (InputMismatchException e){
                scanner.next();
                System.err.println("Error: Invalid type!!!Please try again");
            }

        }


        int userPin;
        int attempt = 3;

        while (true) {
            try {
                System.out.println("Enter pin");
                userPin = scanner.nextInt();


                if(cardNumber == defaultCardNumber && userPin == defaultPin){
                    break;
                }else if(AdminValidators.verifyPin(cardNumber,userPin)) {
                    System.out.println("Login successful");
                    break;

                }else if(attempt == 1){
                    AdministratorManagementSystem.freezeCard(cardNumber);
                    System.out.println("Card frozen,visit bank for further assistance!!");
                    storeToDatabase();
                    System.exit(0);
                }else{
                    attempt--;
                    System.out.println("Invalid pin,please try again");
                    System.out.println("Attempts left:"+attempt);
                }
            }catch (InputMismatchException e) {
                scanner.next();
                System.err.println("Error: Invalid type!!!Please try again");
            }

        }
    }



    public static void freezeCard(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                entry.getValue().getCard().setCardStatus(CardStatus.FROZEN);
            }
        }


    }

    public void unfreezeCard(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                entry.getValue().getCard().setCardStatus(CardStatus.UNFROZEN);
                System.out.println("Card status set to unfrozen successfully");
            }
        }
        storeToDatabase();

    }


    public void addEmployee(Employee emp){
        employeeId++;
        emp.setId(employeeId);
        employeeMap.put(employeeId,emp);
        storeToDatabase();
        System.out.println("Employee added successfully to the system.\nEmployee Card Details");
        System.out.println(emp.getCard());
    }
    public void editEmployee(int id){
        boolean breakLoop = true;

        Employee employee = employeeMap.get(id);

                while (breakLoop) {
                try {
                    fieldSelectionMenu();
                    System.out.println("Press the number of the field you want to edit or press -1 to navigate back");
                    int ch = scanner.nextInt();
                    scanner.nextLine();

                    switch (ch) {

                        case 1:
                             System.out.println("Enter employee names:");
                             String name = scanner.nextLine();

                             employee.setName(name);
                             storeToDatabase();
                             System.out.println("Names edited successfully");


                        break;

                        case 2:

                              System.out.println("Enter employee Email address:");
                              String email = scanner.nextLine();

                              employee.setEmail(email);
                              storeToDatabase();
                            System.out.println("Email edited successfully");



                        break;

                        case 3:

                             System.out.println("Enter employee Phone number:");
                             long phoneNumber = scanner.nextLong();
                             scanner.nextLine();

                            employee.setPhoneNumber(phoneNumber);
                            storeToDatabase();
                            System.out.println("Phone number edited successfully");


                        break;

                        case 4:

                             System.out.println("Enter employee Home address:");
                             String address = scanner.nextLine();

                            employee.setAddress(address);
                            storeToDatabase();
                            System.out.println("Home Address edited successfully");

                        break;

                        case 5:
                            boolean validType = false;
                            UserRoles role = null;
                            while (!validType) {
                                try {

                                    System.out.println("Select employee role:\n1.Admin\n2.Teller");
                                    int roleSelection = scanner.nextInt();
                                    scanner.nextLine();



                                    if (roleSelection == 1) {
                                        role = UserRoles.ADMIN;
                                        validType = true;
                                    } else if (roleSelection == 2) {
                                        role = UserRoles.TELLER;
                                        validType = true;
                                    } else {
                                        throw new IllegalArgumentException("Error: Invalid selection!!!please try again");
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.err.println(e.getMessage());
                                } catch (InputMismatchException e) {
                                    scanner.next();
                                    System.err.println("Error: Invalid type!!!please try again");
                                }
                            }
                            employee.setRole(role);
                            storeToDatabase();
                            System.out.println("Role edited successfully");

                        break;
                        case -1:
                            breakLoop= false;
                            break;

                        default:
                            System.err.println("Error: Invalid menu selection,please try again");
                    }
                }catch (InputMismatchException e){
                    scanner.next();
                    System.err.println("Error: Invalid type!!!Please enter digits");
                }

            }
    }
    public void removeEmployee(int id){
        employeeMap.remove(id);
                storeToDatabase();
                System.out.println("Employee removed from system successfully");
            }



    public void viewEmployeeInfo(int id){
        System.out.println("Employee Information:");
        System.out.println(employeeMap.get(id));
    }

    public void updateATMStatus(){//for presentation purposes only
        for (ATM atm: atmList){
            System.out.println(atm.toString());
            System.out.println("-----------------------");
        }
        System.out.println("Enter id of ATM to change status");
        int atmId = scanner.nextInt();

        System.out.println("Select status\n1.Activate\n2.Deactivate");
        int ch = scanner.nextInt();


        for (ATM atm: atmList){
            if (atm.getAtmId() == atmId) {
                if (ch == 1){
                    atm.setStatus("Active");
                }else if(ch ==2) {
                    atm.setStatus("Inactive");
                }else {
                    System.out.println("Invalid selection,please try again");
                }


            }
        }



    }

    public void viewAuditTrail(long atmId){}

    public void employeeList(){
        System.out.println("--------------------------");
        System.out.println("Employee List");
        System.out.println("--------------------------");
        for (Map.Entry<Integer,Employee> entry: employeeMap.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue().getName());
        }

    }
    public Employee getEmployee(int id){
       if (AdminValidators.checkUser(id)){
           return employeeMap.get(id);
       }
        return null;
    }

    public void resetPin(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
               boolean validType = false;
                int newPin = 0;
                while (!validType) {
                    try {
                        System.out.println("Enter new PIN");
                        newPin = scanner.nextInt();

                        System.out.println("Confirm new PIN");
                        int confirmPin = scanner.nextInt();

                        if(newPin != confirmPin){
                            throw new PinMismatchException("Error:Confirmed PIN does not match with new PIN,please try again");
                        }
                        scanner.nextLine();
                        validType = true;

                    }catch (PinMismatchException e){
                        System.err.println(e.getMessage());

                    }catch (InputMismatchException e){
                        scanner.next();
                        System.err.println("Error: Invalid Type!!!Please try again");
                    }
                }
                entry.getValue().getCard().setPin(newPin);
            }
        }
        storeToDatabase();
        System.out.println("PIN reset successfully");

    }


    public void fieldSelectionMenu(){
        System.out.println("---------------------");
        System.out.println("||1.Names          ||");
        System.out.println("---------------------");
        System.out.println("||2.Email Address  ||");
        System.out.println("---------------------");
        System.out.println("||3.Phone Number   ||");
        System.out.println("---------------------");
        System.out.println("||4.Home Address   ||");
        System.out.println("---------------------");
        System.out.println("||5.Role           ||");
        System.out.println("---------------------");
        System.out.println("||-1.Back          ||");
        System.out.println("---------------------");
    }

    public static Map<Integer,Employee> getEmployeeMap(){
        return employeeMap;
    }

}
