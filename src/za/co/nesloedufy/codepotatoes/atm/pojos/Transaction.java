package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.AccountType;
import za.co.nesloedufy.codepotatoes.atm.enums.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private LocalDateTime timestamp;
    private TransactionType transactionType;
    private AccountType accountType;
    private double amount;
    private long accountNumber;

    public Transaction(LocalDateTime timestamp, TransactionType transactionType, AccountType accountType, double amount,long accountNumber) {
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.amount = amount;
        this.accountNumber = accountNumber;


    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return String.format("Date :%-12s|Time :%-10s|Transaction Type :%-12s|Account type :%-20s|Amount :R%.02f",timestamp.toLocalDate(),timestamp.toLocalTime().format(formatter),transactionType,accountType,amount);
       //call account no. for atm transactions
    }
}
