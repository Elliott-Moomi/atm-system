package za.co.nesloedufy.codepotatoes.atm.employee;

import za.co.nesloedufy.codepotatoes.atm.admin.AdministratorManagementSystem;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;
import za.co.nesloedufy.codepotatoes.atm.pojos.Client;

import java.util.Map;

public class ClientValidators {
    public static Map<Integer, Client> clientMap = ClientManagementSystem.getClientMap();

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
