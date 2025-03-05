package za.co.nesloedufy.codepotatoes.atm;

import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidLoginException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.PinMismatchException;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.TransactionType;
import za.co.nesloedufy.codepotatoes.atm.pojos.BankAccount;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;
import za.co.nesloedufy.codepotatoes.atm.pojos.Transaction;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ATMManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<Integer, Client> clientMap;
    private static Client currentUser;
    public ATMManagementSystem(){
        clientMap = new HashMap<>();
        readFromDatabase();
    }
    public void storeToDatabase(){
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/resources/ClientDatabase.dat"))){
            os.writeObject(clientMap);
        }catch (FileNotFoundException e){
            System.err.println("Error:Data not saved!Could not find database");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public void readFromDatabase(){
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream("src/resources/ClientDatabase.dat"))){

            Map<Integer,Client> storedClients = (Map<Integer, Client>) oi.readObject();
            clientMap.putAll(storedClients);

        }catch (EOFException e){
            System.err.println("Error: End of file");
        }catch (IOException |ClassNotFoundException |ClassCastException e){
            System.err.println(e.getMessage());
        }
    }
    public Client getCurrentUser(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                return entry.getValue();
            }

        }
        return null;
    }
    public static Client getCurrentUser(){
        return currentUser;
    }

    public void clientLogin(){
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

                }else if (!(ATMValidators.verifyCardNumber(cardNumber))){
                    throw new InvalidLoginException("Error: Invalid card Number!!!Please try again");

                }else if(ATMValidators.checkCardStatus(cardNumber)){
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
                }else if(ATMValidators.verifyPin(cardNumber,userPin)) {
                    currentUser = getCurrentUser(cardNumber);
                    System.out.println("Login successful");
                    break;

                }else if(attempt == 1){
                    freezeCard(cardNumber);
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
    public void freezeCard(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                entry.getValue().getCard().setCardStatus(CardStatus.FROZEN);
            }
        }


    }

    //--------------basic functionalities----------------------------------
    public void viewBalance(int choice){
        if(choice == 1){
            System.out.println(currentUser.getCurrentAccount());

        }else if(choice == 2){
            System.out.println(currentUser.getSavingsAccount());
        }
    }

    public void deposit(int choice,double amount){
        double newBalance;
        Transaction transaction;
        switch (choice) {
            case 1:
                 BankAccount currentAccount = currentUser.getCurrentAccount();
                 newBalance = currentAccount.getBalance() + amount;
                 currentAccount.setBalance(newBalance);
                 transaction = new Transaction(LocalDateTime.now(),TransactionType.DEPOSIT,currentAccount.getAccountType(),amount,currentAccount.getAccountNumber());
                 currentAccount.addTransaction(transaction);
                 //TODO add transaction to atm
                storeToDatabase();
                System.out.println("Deposit was successful");


            break;
            case 2:

                BankAccount savingsAccount = currentUser.getSavingsAccount();
                newBalance = savingsAccount.getBalance() + amount;
                savingsAccount.setBalance(newBalance);
                transaction = new Transaction(LocalDateTime.now(),TransactionType.DEPOSIT,savingsAccount.getAccountType(),amount,savingsAccount.getAccountNumber());
                savingsAccount.addTransaction(transaction);
                //TODO add transaction to atm
                storeToDatabase();
                System.out.println("Deposit was successful");

            break;
        }

    }

    public void withdraw(int choice,double amount){
        double newBalance;
        Transaction transaction;
        switch (choice) {
            case 1:
                BankAccount currentAccount = currentUser.getCurrentAccount();

                    newBalance = currentAccount.getBalance() - amount;
                    currentAccount.setBalance(newBalance);
                    transaction = new Transaction(LocalDateTime.now(), TransactionType.WITHDRAWAL, currentAccount.getAccountType(), amount, currentAccount.getAccountNumber());
                    currentAccount.addTransaction(transaction);
                    //TODO add transaction to atm
                     storeToDatabase();
                    System.out.println("Withdrawal was successful");



                break;
            case 2:

                BankAccount savingsAccount = currentUser.getSavingsAccount();
                    newBalance = savingsAccount.getBalance() - amount;
                    savingsAccount.setBalance(newBalance);
                    transaction = new Transaction(LocalDateTime.now(), TransactionType.WITHDRAWAL, savingsAccount.getAccountType(), amount, savingsAccount.getAccountNumber());
                    savingsAccount.addTransaction(transaction);
                    //TODO add transaction to atm
                    storeToDatabase();
                    System.out.println("Withdrawal was successful");

                break;
        }

    }
    public void resetPin(){
        Client client = currentUser;


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
                client.getCard().setPin(newPin);
                storeToDatabase();
                System.out.println("PIN reset successfully");

    }

    public void transfer(double amount,long accountNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()){
            BankAccount receiverAccount = entry.getValue().getCurrentAccount();
            if (receiverAccount.getAccountNumber() == accountNumber){
                double newBalance = currentUser.getCurrentAccount().getBalance() - amount;
                currentUser.getCurrentAccount().setBalance(newBalance);

                double newReceiverBalance = receiverAccount.getBalance() + amount;
               receiverAccount.setBalance(newReceiverBalance);

                Transaction receiverTransaction = new Transaction(LocalDateTime.now(), TransactionType.MONEY_IN, receiverAccount.getAccountType(), amount, receiverAccount.getAccountNumber());
                Transaction senderTransaction = new Transaction(LocalDateTime.now(), TransactionType.TRANSFER, receiverAccount.getAccountType(), amount, receiverAccount.getAccountNumber());
                receiverAccount.addTransaction(receiverTransaction);
                currentUser.getCurrentAccount().addTransaction(senderTransaction);

                storeToDatabase();
                System.out.println("Transfer was successful");

            }
        }

    }
    public void printAccountStatements(int choice){
        List<Transaction> transactions;
        String fileName;
        File filePath;
        switch (choice){
            case 1:
                BankAccount currentAccount = currentUser.getCurrentAccount();
                transactions = currentAccount.getTransactions();
                fileName = currentAccount.getAccountHolderName()+" Current_Account_Statements.txt";
                filePath = new File( "src/resources/ATMPrinter/"+fileName);

                try (PrintWriter pw = new PrintWriter(new FileWriter(filePath),true)){
                    pw.append(fileName);
                    pw.println();
                    pw.append("Account Number :"+currentAccount.getAccountNumber());
                    pw.println();
                    for (Transaction transaction : transactions){
                        pw.append(transaction.toString());
                        pw.println();
                    }

                }catch (IOException e){
                    System.err.println(e.getMessage());
                }
                System.out.println("Current Account statement printed successfully");

                break;
            case 2:
                BankAccount savingsAccount = currentUser.getSavingsAccount();
                transactions = savingsAccount.getTransactions();
                fileName = savingsAccount.getAccountHolderName()+" Savings_Account_Statements.txt";
                filePath = new File( "src/resources/ATMPrinter/"+fileName);

                try (PrintWriter pw = new PrintWriter(new FileWriter(filePath),true)){
                    pw.append(fileName);
                    pw.println();
                    pw.append("Account Number :"+savingsAccount.getAccountNumber());
                    pw.println();
                    for (Transaction transaction : transactions){
                        pw.append(transaction.toString());
                        pw.println();
                    }

                }catch (IOException e){
                    System.err.println(e.getMessage());
                }
                System.out.println("Savings Account statement printed successfully");
                break;
        }
    }
    public void viewTransactionHistory(int choice){
        switch (choice){
            case 1:
                BankAccount currentAccount = currentUser.getCurrentAccount();
                currentAccount.getTransactionHistory();
                break;
            case 2:
                BankAccount savingsAccount = currentUser.getSavingsAccount();
                savingsAccount.getTransactionHistory();
                break;
        }
    }

    public static Map<Integer,Client> getClientMap(){
        return clientMap;
    }



    }

