package za.co.nesloedufy.codepotatoes.atm.admin;

import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;

import java.util.Map;

public class AdminValidators {
    public static Map<Integer, Employee> employeeMap = AdministratorManagementSystem.getEmployeeMap();

    public static boolean verifyPin(long cardNumber,int pin){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getCard().getPin() == pin) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUser(int id){
        if (employeeMap.containsKey(id)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean verifyRole(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getRole().equals(UserRoles.ADMIN)) {
                return true;
            }

        }
        return false;
    }

    public static boolean verifyCardNumber(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber) {
                return true;
            }

        }
        return false;
    }

    public static boolean checkCardStatus(long cardNumber){
        for (Map.Entry<Integer,Employee> entry:employeeMap.entrySet()) {
            if (entry.getValue().getCard().getCardNumber() == cardNumber && entry.getValue().getCard().getCardStatus().equals(CardStatus.FROZEN)) {
                return true;
            }
        }
        return false;

    }
}
