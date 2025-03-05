package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.AccountType;
import za.co.nesloedufy.codepotatoes.atm.pojos.BankAccount;

public class CurrentAccount extends BankAccount {
    public CurrentAccount(String accountHolderName, long accountNumber, double balance) {
        super(accountHolderName, accountNumber, AccountType.CURRENT_ACCOUNT, balance);

    }
}
