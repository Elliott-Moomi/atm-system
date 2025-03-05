package za.co.nesloedufy.codepotatoes.atm.admin;

import za.co.nesloedufy.codepotatoes.atm.CardManagement;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidMenuOptionException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.UserNotFoundException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.YesOrNoException;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Card;
import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static AdministratorManagementSystem ams = new AdministratorManagementSystem();
  //  public static void main(String[] args) {
    public static void adminRunner(){


        ams.adminLogin();

        boolean validMenuInput = false;

        while(!validMenuInput) {
            try {
                adminMenu();
                int ch = scanner.nextInt();
                scanner.nextLine();
                validateMenuOption(ch);
                boolean backToMenu = true;

                String name;
                int id;
                boolean validType = false;

                switch (ch) {
                    case 1:

                        //-----------------------------add employee---------------------------------------
                        System.out.println("Enter employee names:");
                        name = scanner.nextLine();

                        System.out.println("Enter employee Email address:");
                        String email = scanner.nextLine();

                        long phoneNumber = 0;
                        while(!validType){
                            try {
                                System.out.println("Enter employee Phone number:");
                                phoneNumber = scanner.nextLong();
                                scanner.nextLine();
                                validType =true;
                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid Type!!!Please try again");
                            }

                    }


                        System.out.println("Enter employee Home address:");
                        String address = scanner.nextLine();

                        validType = false;
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

                        System.out.println("Enter Bank Name:");
                        String bankName = scanner.nextLine();

                        validType = false;
                        int pin = 0;
                        while (!validType) {
                            try {
                            System.out.println("Enter PIN");
                            pin = scanner.nextInt();
                            scanner.nextLine();
                            validType = true;

                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid Type!!!Please try again");
                            }
                        }


                        Card card = CardManagement.generateCard(pin, bankName);

                        Employee employee = new Employee(name, email, phoneNumber, address, role, card);
                        ams.addEmployee(employee);


                        break;

                    case 2:
                        //-----------------------------edit employee---------------------------------------
                        while(backToMenu) {
                            try{
                            ams.employeeList();
                            System.out.println("Enter the ID of the employee to edit or press -1 to navigate back");
                            id = scanner.nextInt();

                            if (id != -1){
                                validateUser(id);
                                ams.editEmployee(id);
                            }else {
                                backToMenu = false;
                            }
                        }catch (UserNotFoundException e){
                                scanner.nextLine();
                                System.err.println(e.getMessage());
                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid type!!!Please enter digits");
                            }

                        }
                        break;

                    case 3:
                        //-----------------------------remove employee---------------------------------------
                        while (backToMenu) {
                            try{
                            ams.employeeList();
                            System.out.println("Enter the ID of the employee to remove or press -1 to navigate back");
                                id = scanner.nextInt();
                                scanner.nextLine();

                            if (id != -1) {

                                validateUser(id);
                                try {

                                    System.out.println("Are you sure you want to remove " + ams.getEmployee(id).getName() + " from the system? Enter yes or no");
                                    String response = scanner.nextLine().toLowerCase();
                                    if (response.equals("no")){
                                        backToMenu = false;
                                    }else if(response.equals("yes")){
                                        ams.removeEmployee(id);
                                    }else {
                                      throw new YesOrNoException("Error: Invalid response!!!Please enter yes or no");
                                    }


                                }catch (YesOrNoException e){
                                    scanner.next();
                                    System.err.println(e.getMessage());
                                }
                            }else {
                                backToMenu = false;
                            }
                            }catch (UserNotFoundException e){
                                scanner.next();
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid type!!!Please enter digits");
                    }
                        }

                        break;
                    case 4:
                        //-----------------------------view employee---------------------------------------

                       while (backToMenu) {
                           try {
                               ams.employeeList();
                               System.out.println("Enter the ID of the employee you want to view or press -1 to navigate back");
                               id = scanner.nextInt();
                               if (id != -1) {
                                   validateUser(id);
                                   ams.viewEmployeeInfo(id);
                               }else {
                                   backToMenu = false;
                               }
                           }catch (UserNotFoundException e){
                        scanner.nextLine();
                        System.err.println(e.getMessage());

                    }catch (InputMismatchException e){
                        scanner.next();
                        System.err.println("Error: Invalid type!!!Please enter digits");
                    }
                       }
                        break;
                    case 5:

                        while (backToMenu) {
                            try {
                                System.out.println("Enter card number or press -1 to navigate back");
                                long cardNumber = scanner.nextLong();
                                if (cardNumber != -1) {
                                    validateCardNumber(cardNumber);
                                    ams.unfreezeCard(cardNumber);
                                }else {
                                    backToMenu = false;
                                }
                            }catch (UserNotFoundException e){
                                scanner.nextLine();
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid type!!!Please enter digits");
                            }
                        }


                        break;
                    case 6:
                        //----------------reset PIN---------------------------------------------------

                        while (backToMenu) {
                            try {
                                System.out.println("Enter card number or press -1 to navigate back");
                                long cardNumber = scanner.nextLong();
                                if (cardNumber != -1) {
                                    validateCardNumber(cardNumber);
                                    ams.resetPin(cardNumber);
                                }else {
                                    backToMenu = false;
                                }
                            }catch (UserNotFoundException e){
                                scanner.nextLine();
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid type!!!Please enter digits");
                            }
                        }

                        break;

                    case 7:
                        //-------------------Update ATM status---------------------------------------------
                        //System.out.println("Enter ATM ID");
                       // id = scanner.nextInt();
                        ams.updateATMStatus();
                        break;
                    case 8:
                        //---------------------view audit trail-----------------------------------------------
                        System.out.println("Enter the ATM ID to view its Audit Trail");
                        id = scanner.nextInt();
                        ams.viewAuditTrail(id);
                        break;
                    case 9:
                        //--------------------exit-----------------------------------------------------------
                        System.out.println("System logged out!!!");
                        return;


                }


            }catch (InvalidMenuOptionException e){
                System.err.println(e.getMessage());
            }catch (InputMismatchException e) {
                scanner.next();

                System.err.println("Error: Invalid type!!!Please enter digits");
            }
        }
    }

    public static void adminMenu(){
        System.out.println("-------------------------");
        System.out.println("||  Administrator Menu ||");
        System.out.println("-------------------------");
        System.out.println("||1.Add Employee       ||");
        System.out.println("-------------------------");
        System.out.println("||2.Edit Employee      ||");
        System.out.println("-------------------------");
        System.out.println("||3.Remove Employee    ||");
        System.out.println("-------------------------");
        System.out.println("||4.View Employee Info ||");
        System.out.println("-------------------------");
        System.out.println("||5.Unfreeze card      ||");
        System.out.println("-------------------------");
        System.out.println("||6.Reset PIN          ||");
        System.out.println("-------------------------");
        System.out.println("||7.Update ATM Status  ||");
        System.out.println("-------------------------");
        System.out.println("||8.View Audit Trail   ||");
        System.out.println("-------------------------");
        System.out.println("||9.Exit               ||");
        System.out.println("-------------------------");
    }

    public static void validateMenuOption(int ch) throws InvalidMenuOptionException{
        if(ch<1||ch>9){
            throw new InvalidMenuOptionException("Error: Invalid menu option!!!please choose a valid option");
        }
    }

    public static void validateUser(int id) throws UserNotFoundException{
        if(!AdminValidators.checkUser(id)){
            throw new UserNotFoundException("Error: There is no User in the system that corresponds with that ID. Please try again.");
        }

    }

    public static void validateCardNumber(long cardNumber) throws UserNotFoundException{
        if(!AdminValidators.verifyCardNumber(cardNumber)){
            throw new UserNotFoundException("Error: There is no User in the system that corresponds with that Card Number. Please try again.");
        }
    }


}