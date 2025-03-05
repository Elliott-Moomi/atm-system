package za.co.nesloedufy.codepotatoes.atm.employee;

import za.co.nesloedufy.codepotatoes.atm.admin.AdminValidators;
import za.co.nesloedufy.codepotatoes.atm.admin.AdministratorManagementSystem;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InvalidLoginException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.PinMismatchException;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ClientManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer, Client> clientMap;

    private int clientId = 0;

    public ClientManagementSystem(){
        clientMap = new HashMap<>();
        readFromDatabase();
    }


    public void storeToDatabase(){
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/resources/ClientDatabase.dat"))){
            os.writeObject(clientMap);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public void readFromDatabase(){
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream("src/resources/ClientDatabase.dat"))){
            int maxValue = 0;
            Map<Integer,Client> storedClients = (Map<Integer, Client>) oi.readObject();
            for (Map.Entry<Integer,Client> entry:storedClients.entrySet()){
                if(entry.getKey() > maxValue){
                    maxValue = entry.getKey();
                }
                clientMap.put(entry.getKey(),entry.getValue());
            }

            clientId = maxValue;
        }catch (EOFException e){
            System.err.println("Error: End of file");
        }catch (IOException |ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
    /*public void adminLogin(){
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
    }*/



    public static void freezeCard(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                entry.getValue().getCard().setCardStatus(CardStatus.FROZEN);
            }
        }


    }

    public void unfreezeCard(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                entry.getValue().getCard().setCardStatus(CardStatus.UNFROZEN);
                System.out.println("Card status set to unfrozen successfully");
            }
        }
        storeToDatabase();

    }


    public void addClient(Client client){
        clientId++;
        client.setId(clientId);
        clientMap.put(clientId,client);
        storeToDatabase();
        System.out.println("Client added successfully to the system.\nClient Card Details");
        System.out.println(client.getCard());
    }
    public void editClient(int id){
        boolean breakLoop = true;

        Client client = clientMap.get(id);

        while (breakLoop) {
            try {
                fieldSelectionMenu();
                System.out.println("Press the number of the field you want to edit or press -1 to navigate back");
                int ch = scanner.nextInt();
                scanner.nextLine();

                switch (ch) {

                    case 1:
                        System.out.println("Enter client names:");
                        String name = scanner.nextLine();

                        client.setName(name);
                        client.getCurrentAccount().setAccountHolderName(name);
                        client.getSavingsAccount().setAccountHolderName(name);
                        storeToDatabase();
                        System.out.println("Names edited successfully");


                        break;

                    case 2:

                        System.out.println("Enter client Email address:");
                        String email = scanner.nextLine();

                        client.setEmail(email);
                        storeToDatabase();
                        System.out.println("Email edited successfully");



                        break;

                    case 3:

                        System.out.println("Enter client Phone number:");
                        long phoneNumber = scanner.nextLong();
                        scanner.nextLine();

                        client.setPhoneNumber(phoneNumber);
                        storeToDatabase();
                        System.out.println("Phone number edited successfully");


                        break;

                    case 4:

                        System.out.println("Enter client Home address:");
                        String address = scanner.nextLine();

                        client.setAddress(address);
                        storeToDatabase();
                        System.out.println("Home Address edited successfully");

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
    public void removeClient(int id){
        clientMap.remove(id);
        storeToDatabase();
        System.out.println("Client removed from system successfully");
    }



    public void viewClientInfo(int id){
        System.out.println("Client Information:");
        System.out.println(clientMap.get(id));
    }





    public void clientList(){
        System.out.println("--------------------------");
        System.out.println("Client List");
        System.out.println("--------------------------");
        for (Map.Entry<Integer,Client> entry: clientMap.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue().getName());
        }

    }
    public Client getClient(int id){
        if (AdminValidators.checkUser(id)){
            return clientMap.get(id);
        }
        return null;
    }

    public void resetPin(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
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
        System.out.println("||-1.Back          ||");
        System.out.println("---------------------");
    }

    public static Map<Integer,Client> getClientMap(){
        return clientMap;
    }

}
