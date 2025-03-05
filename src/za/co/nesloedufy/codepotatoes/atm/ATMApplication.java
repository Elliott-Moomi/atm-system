package za.co.nesloedufy.codepotatoes.atm;

import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InsufficientFundsException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidMenuOptionException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.UserNotFoundException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ATMApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ATMManagementSystem atm = new ATMManagementSystem();
    public static void main(String[] args) {
        atm.clientLogin();

        boolean validMenuInput = false;
        boolean validType = false;
        double amount;
        while (!validMenuInput){
            try {

                atmMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                validateMenuOption(choice);
                boolean backToMenu = false;
                switch (choice){

                    case 1:
                        //--------------View Balance----------------------------------
                       while (!backToMenu) {
                           try {

                               System.out.println("Select Account to view balance \nor press -1 to navigate back\n----------------------------------");
                               System.out.println("1.Current Account\n2.Savings Account");
                               choice = scanner.nextInt();
                               scanner.nextLine();
                               if (choice!= -1) {
                                   validateAccountSelection(choice);
                                   atm.viewBalance(choice);
                               }else {
                                   backToMenu = true;
                               }
                           }catch (InvalidMenuOptionException e){
                               System.err.println(e.getMessage());

                           }catch (InputMismatchException e){
                               System.err.println("Invalid type,please enter digits");
                               scanner.next();
                           }
                       }

                        break;
                    case 2:
                        //--------------Deposit----------------------------------
                        while (true) {
                            try {

                                System.out.println("Select Account to deposit to\nor press -1 to navigate back\n----------------------------------");
                                System.out.println("1.Current Account\n2.Savings Account");
                                choice = scanner.nextInt();
                                if (choice != -1) {
                                    validateAccountSelection(choice);
                                }else {
                                    break;
                                }

                                System.out.println("Enter amount to deposit or -1 to navigate back");
                                amount = scanner.nextDouble();//come back to validate amount
                                scanner.nextLine();

                                if (amount == -1) {

                                    break;
                                }else if(amount<1){
                                    throw new IllegalArgumentException("Error! Invalid amount,please enter a positive value");
                                }else {
                                    atm.deposit(choice,amount);

                                }
                            }catch (InvalidMenuOptionException e){
                                System.err.println(e.getMessage());

                            }catch (IllegalArgumentException e){
                                System.err.println(e.getMessage());
                            }catch (InputMismatchException e){
                                System.err.println("Invalid type,please enter digits");
                                scanner.next();
                            }
                        }


                        break;
                    case 3:
                        //--------------Withdraw----------------------------------
                        while (true) {
                            try {

                                System.out.println("Select Account to withdraw from\nor press -1 to navigate back\n----------------------------------");//34 dashes
                                System.out.println("1.Current Account\n2.Savings Account");
                                choice = scanner.nextInt();
                                if (choice != -1) {
                                    validateAccountSelection(choice);
                                }else {
                                    break;
                                }


                                while (true){
                                    try {
                                        System.out.println("Enter amount to withdraw or -1 to navigate back");
                                        amount = scanner.nextDouble();//come back to validate amount
                                        scanner.nextLine();
                                        if (amount == -1) {

                                            break;
                                        }else if(amount<1){
                                            throw new IllegalArgumentException("Error! Invalid amount,please enter a positive value");
                                        }else {
                                            ATMValidators.validateAmount(choice,amount);
                                            atm.withdraw(choice,amount);

                                        }

                                    }catch (InsufficientFundsException e){
                                        System.err.println(e.getMessage());
                                    }catch (IllegalArgumentException e){
                                        System.err.println(e.getMessage());
                                    }
                                }

                            }catch (InvalidMenuOptionException e){
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                System.err.println("Invalid type,please enter digits");
                                scanner.next();
                            }
                        }


                        break;
                    case 4:
                        //--------------Reset Pin----------------------------------
                        atm.resetPin();
                        break;
                    case 5:
                        //--------------Transfer----------------------------------


                        while (true){
                            try {
                                System.out.println("Enter amount to transfer or -1 to navigate back");
                                amount = scanner.nextDouble();//come back to validate amount
                                scanner.nextLine();
                                choice = 1;
                                if (amount == -1){
                                    break;
                                } else if (amount < 1) {
                                    throw new IllegalArgumentException("Error!Invalid amount,please enter a positive value");
                                } else {
                                    ATMValidators.validateAmount(choice,amount);
                                    while (true) {
                                        try {
                                            System.out.println("Enter Account number of receiver or -1 to navigate back ");
                                            long accountNumber = scanner.nextLong();
                                            if (accountNumber!= -1){
                                                verifyAccountNumber(accountNumber);
                                                atm.transfer(amount,accountNumber);
                                                break;
                                            }else {
                                                break;
                                            }


                                        }catch (UserNotFoundException e){
                                            System.err.println(e.getMessage());
                                        }catch (InputMismatchException e){
                                            System.err.println("Error :Invalid type!Please enter digits");
                                            scanner.next();
                                        }
                                    }
                                }

                            }catch (InsufficientFundsException e){
                                System.err.println(e.getMessage());
                            }catch (IllegalArgumentException e){
                                System.err.println(e.getMessage());
                            }catch (InputMismatchException e){
                                System.err.println("Error :Invalid type!Please enter digits");
                                scanner.next();
                            }
                        }




                        break;
                    case 6:
                        //--------------Print Account statements----------------------------------
                        while (!backToMenu) {
                            try {

                                System.out.println("\nSelect Account to print statements \nor press -1 to navigate back\n----------------------------------");
                                System.out.println("1.Current Account\n2.Savings Account");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                if (choice!= -1) {
                                    validateAccountSelection(choice);
                                    atm.printAccountStatements(choice);
                                }else {
                                    backToMenu = true;
                                }
                            }catch (InvalidMenuOptionException e){
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                System.err.println("Invalid type,please enter digits");
                                scanner.next();
                            }
                        }

                        break;
                    case 7:
                        //--------------View Transaction History----------------------------------
                        while (!backToMenu) {
                            try {

                                System.out.println("\nSelect Account to view transaction history \nor press -1 to navigate back\n----------------------------------");
                                System.out.println("1.Current Account\n2.Savings Account");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                if (choice!= -1) {
                                    validateAccountSelection(choice);
                                    atm.viewTransactionHistory(choice);
                                }else {
                                    backToMenu = true;
                                }
                            }catch (InvalidMenuOptionException e){
                                System.err.println(e.getMessage());

                            }catch (InputMismatchException e){
                                System.err.println("Invalid type,please enter digits");
                                scanner.next();
                            }
                        }

                        break;
                    case 8:
                        //---------------Exit-----------------------------------------------------
                        System.out.println("Logged out of the ATM...");
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

    private static void atmMenu(){
        System.out.println("------------------------------");
        System.out.println("||        ATM Menu          ||");
        System.out.println("------------------------------");
        System.out.println("||1.View Balance            ||");
        System.out.println("------------------------------");
        System.out.println("||2.Deposit                 ||");
        System.out.println("------------------------------");
        System.out.println("||3.Withdraw                ||");
        System.out.println("------------------------------");
        System.out.println("||4.Reset Pin               ||");
        System.out.println("------------------------------");
        System.out.println("||5.Transfer                ||");
        System.out.println("------------------------------");
        System.out.println("||6.Print Account Statements||");
        System.out.println("------------------------------");
        System.out.println("||7.View Transaction History||");
        System.out.println("------------------------------");
        System.out.println("||8.Exit                    ||");
        System.out.println("------------------------------");
    }

    private static void validateMenuOption(int choice) throws InvalidMenuOptionException{
        if (choice<1 || choice > 8){
            throw new InvalidMenuOptionException("Error: Invalid menu option!!!please select a valid option");
        }
    }
    private static void validateAccountSelection(int choice) throws InvalidMenuOptionException{
        if (choice<1 || choice > 2){
            throw new InvalidMenuOptionException("Error: Invalid account selection!!!please select a valid account");
        }
    }

    private static void verifyAccountNumber(long accountNumber) throws UserNotFoundException {
        if (!ATMValidators.validateAccountNumber(accountNumber)){
            throw new UserNotFoundException("There is no user with this account number,please try again");
        }
    }
}
