package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.AccountType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {
    private String accountHolderName;
    private long accountNumber;
    private AccountType accountType;

    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String accountHolderName, long accountNumber, AccountType accountType, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.transactions =new ArrayList<>();
    }

    public void deposit(double amount){
        balance += amount;
    }

    public void withdraw(double amount){
        balance-=amount;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
    public void getTransactionHistory(){
        for (Transaction transaction:transactions){
            System.out.println(transaction);
        }
    }
    public List<Transaction> getTransactions(){
        return transactions;
    }

    @Override
    public String toString() {
      return  String.format("Account Details\n----------------------------------\nAccount Holder: %s\nAccount Number: %s\nAccount Type  : %s\nBalance       : R%.02f\n----------------------------------",accountHolderName,accountNumber,accountType,balance);

    }
}
