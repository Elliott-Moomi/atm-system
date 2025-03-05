package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;

public class Client extends User {
    private BankAccount currentBankAccount;
    private BankAccount savingsAccount;
    public Client(String name, String email, long phoneNumber, String address, UserRoles role, Card card, BankAccount currentBankAccount, BankAccount savingsAccount) {
        super(name, email, phoneNumber, address, role,card);
        this.currentBankAccount = currentBankAccount;
        this.savingsAccount = savingsAccount;
    }

    public BankAccount getCurrentAccount() {
        return currentBankAccount;
    }

    public BankAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setCurrentAccount(BankAccount currentBankAccount) {
        this.currentBankAccount = currentBankAccount;
    }

    public void setSavingsAccount(BankAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    @Override
    public String toString() {
        return "User Details\n-------------\n"+
                "\nID: "+getId()+
                "\nName: "+getName()+
                "\nEmail: "+getEmail()+
                "\nPhone Number: "+getPhoneNumber()+
                "\nAddress: "+getAddress()+"\n"+
                getCard()+"\n-------------------------"+
                "\n"+ getCurrentAccount() +
                "\n"+getSavingsAccount();
    }
}
