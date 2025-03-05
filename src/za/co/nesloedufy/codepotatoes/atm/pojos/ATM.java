package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.pojos.Transaction;

import java.io.Serializable;
import java.util.ArrayList;

public class ATM implements Serializable {
    private int atmId;
    private String  bankName;
    private double atmBalance;
    private String location;
    private String status;
    private ArrayList<Transaction> transactions;


    public ATM(int atmId, String bankName, double atmBalance, String location, String status) {
        this.atmId = atmId;
        this.bankName = bankName;
        this.atmBalance = atmBalance;
        this.location = location;
        this.status = status;
        transactions = new ArrayList<>();
    }
    public void getTransactions() {
        for (Transaction transaction: transactions){
            System.out.println(transaction+" "+transaction.getAccountNumber());
        }
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public int getAtmId() {
        return atmId;
    }

    public String getBankName() {
        return bankName;
    }

    public double getAtmBalance() {
        return atmBalance;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }


    //--------------------Setters----------------------------------

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAtmBalance(double atmBalance) {
        this.atmBalance = atmBalance;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "ATM ID: "+ atmId +
                "\nBank Name: "+ bankName +
                "\nATM Balance: "+ atmBalance +
                "\nLocation: " +location +
                "\nATM Status: " + status;
    }
}
