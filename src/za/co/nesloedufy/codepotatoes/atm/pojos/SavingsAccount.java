package za.co.nesloedufy.codepotatoes.atm.pojos;


import za.co.nesloedufy.codepotatoes.atm.enums.AccountType;
import za.co.nesloedufy.codepotatoes.atm.pojos.BankAccount;

public class SavingsAccount extends BankAccount {
    private double interestRate = 0.08;
    public SavingsAccount(String accountHolderName, long accountNumber, double balance) {
        super(accountHolderName, accountNumber, AccountType.SAVINGS_ACCOUNT, balance);
    }

}
