package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;
import za.co.nesloedufy.codepotatoes.atm.pojos.Card;
import za.co.nesloedufy.codepotatoes.atm.pojos.User;

import java.io.Serializable;

public class Employee extends User implements Serializable {
    public Employee(String name, String email, long phoneNumber, String address, UserRoles role, Card card) {
        super(name, email, phoneNumber, address,role, card);

    }



}
