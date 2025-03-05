package za.co.nesloedufy.codepotatoes.atm;

import za.co.nesloedufy.codepotatoes.atm.atmexceptions.InsufficientFundsException;
import za.co.nesloedufy.codepotatoes.atm.atmexceptions.UserNotFoundException;
import za.co.nesloedufy.codepotatoes.atm.employee.ClientManagementSystem;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;

import java.util.Map;

public class ATMValidators {
    public static Map<Integer, Client> clientMap = ATMManagementSystem.getClientMap();

    public static void validateAmount(int choice,double amount) throws InsufficientFundsException {
        Client currentClient = ATMManagementSystem.getCurrentUser();
        switch (choice){
            case 1:

                if(amount > currentClient.getCurrentAccount().getBalance()){
                    throw new InsufficientFundsException("Error :You have insufficient funds to perform this action");
                }


                break;
            case 2:
                if(amount > currentClient.getSavingsAccount().getBalance()){
                    throw new InsufficientFundsException("Error :You have insufficient funds to perform this action");
                }
                break;
        }
    }

    public static boolean verifyPin(long cardNumber,int pin){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getCard().getPin() == pin) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUser(int id){
        if (clientMap.containsKey(id)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean verifyRole(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getRole().equals(UserRoles.ADMIN)) {
                return true;
            }

        }
        return false;
    }
    public static boolean validateAccountNumber(long accountNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getAccountNumber() == accountNumber) {
                return true;
            }

        }
        return false;
    }

    public static boolean verifyCardNumber(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                return true;
            }

        }
        return false;
    }

    public static boolean checkCardStatus(long cardNumber){
        for (Map.Entry<Integer,Client> entry:clientMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getCard().getCardStatus().equals(CardStatus.FROZEN)) {
                return true;
            }
        }
        return false;

    }
}
