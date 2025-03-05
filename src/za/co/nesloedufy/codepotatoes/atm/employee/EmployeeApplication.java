package za.co.nesloedufy.codepotatoes.atm.employee;

import za.co.nesloedufy.codepotatoes.atm.admin.AdminApplication;
import za.co.nesloedufy.codepotatoes.atm.admin.AdminValidators;
import za.co.nesloedufy.codepotatoes.atm.CardManagement;
import za.co.nesloedufy.codepotatoes.atm.admin.AdministratorManagementSystem;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidMenuOptionException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.UserNotFoundException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.YesOrNoException;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.BankAccount;
import za.co.nesloedufy.codepotatoes.atm.pojos.Card;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;
import za.co.nesloedufy.codepotatoes.atm.pojos.CurrentAccount;
import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;

import java.util.InputMismatchException;
import java.util.Scanner;

import za.co.nesloedufy.codepotatoes.atm.admin.AdministratorManagementSystem;
import za.co.nesloedufy.codepotatoes.atm.pojos.SavingsAccount;

public class EmployeeApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdministratorManagementSystem ams = new AdministratorManagementSystem();
    private static final ClientManagementSystem cms = new ClientManagementSystem();
    public static void main(String[] args) {
        ams.employeeLogin();

        boolean validMenuInput = false;

        while(!validMenuInput) {
            try {
                employeeMenu();
                int ch = scanner.nextInt();
                scanner.nextLine();
                validateMenuOption(ch);
                boolean backToMenu = true;

                String name;
                int id;
                boolean validType = false;

                switch (ch) {
                    case 1:

                        //-----------------------------add client---------------------------------------
                        System.out.println("Enter client names:");
                        name = scanner.nextLine();

                        System.out.println("Enter client Email address:");
                        String email = scanner.nextLine();

                        long phoneNumber = 0;
                        while(!validType){
                            try {
                                System.out.println("Enter client Phone number:");
                                phoneNumber = scanner.nextLong();
                                scanner.nextLine();
                                validType =true;
                            }catch (InputMismatchException e){
                                scanner.next();
                                System.err.println("Error: Invalid Type!!!Please try again");
                            }

                        }


                        System.out.println("Enter client Home address:");
                        String address = scanner.nextLine();

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
                        BankAccount currentAccount = new CurrentAccount(name,card.getAccountNumber(),0);
                        BankAccount savingsAccount = new SavingsAccount(name,card.getAccountNumber(),0);

                        Client client = new Client(name, email, phoneNumber, address, UserRoles.CLIENT, card,currentAccount,savingsAccount);
                        cms.addClient(client);


                        break;

                    case 2:
                        //-----------------------------edit client---------------------------------------
                        while(backToMenu) {
                            try{
                                cms.clientList();
                                System.out.println("Enter the ID of the client to edit or press -1 to navigate back");
                                id = scanner.nextInt();

                                if (id != -1){
                                    validateUser(id);
                                    cms.editClient(id);
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
                        //-----------------------------remove Client---------------------------------------
                        while (backToMenu) {
                            try{
                                cms.clientList();
                                System.out.println("Enter the ID of the client to remove or press -1 to navigate back");
                                id = scanner.nextInt();
                                scanner.nextLine();

                                if (id != -1) {

                                    validateUser(id);
                                    try {

                                        System.out.println("Are you sure you want to remove " + cms.getClient(id).getName() + " from the system? Enter yes or no");
                                        String response = scanner.nextLine().toLowerCase();
                                        if (response.equals("no")){
                                            backToMenu = false;
                                        }else if(response.equals("yes")){
                                            cms.removeClient(id);
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
                        //----------------Unfreeze card-----------------------------------

                        while (backToMenu) {
                            try {
                                System.out.println("Enter card number or press -1 to navigate back");
                                long cardNumber = scanner.nextLong();
                                if (cardNumber != -1) {
                                    validateCardNumber(cardNumber);
                                    cms.unfreezeCard(cardNumber);
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
                        //--------------------Check Cash up-----------------------------------------------
                        System.out.println("Check Cash up");
                        break;

                    case 6:
                        //-------------------Print transactions---------------------------------------------
                        System.out.println("Printing account statements");
                        break;
                    case 7:
                        //-----------------------------view client---------------------------------------

                        while (backToMenu) {
                            try {
                                cms.clientList();
                                System.out.println("Enter the ID of the client you want to view or press -1 to navigate back");
                                id = scanner.nextInt();
                                if (id != -1) {
                                    validateUser(id);
                                    cms.viewClientInfo(id);
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
                    case 8:
                        //---------------------Administrator side-----------------------------------------------
                        AdminApplication.adminRunner();
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

    public static void employeeMenu(){
        System.out.println("-------------------------");
        System.out.println("||   Employee Menu     ||");
        System.out.println("-------------------------");
        System.out.println("||1.Add Client         ||");
        System.out.println("-------------------------");
        System.out.println("||2.Edit Client        ||");
        System.out.println("-------------------------");
        System.out.println("||3.Remove Client      ||");
        System.out.println("-------------------------");
        System.out.println("||4.Unfreeze card      ||");
        System.out.println("-------------------------");
        System.out.println("||5.Check Cash up      ||");
        System.out.println("-------------------------");
        System.out.println("||6.Print Transactions ||");
        System.out.println("-------------------------");
        System.out.println("||7.View Client Info   ||");
        System.out.println("-------------------------");
        System.out.println("||8.Administrator      ||");
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
        if(!ClientValidators.checkUser(id)){
            throw new UserNotFoundException("Error: There is no User in the system that corresponds with that ID. Please try again.");
        }

    }

    public static void validateCardNumber(long cardNumber) throws UserNotFoundException{
        if(!ClientValidators.verifyCardNumber(cardNumber)){
            throw new UserNotFoundException("Error: There is no User in the system that corresponds with that Card Number. Please try again.");
        }
    }

}
