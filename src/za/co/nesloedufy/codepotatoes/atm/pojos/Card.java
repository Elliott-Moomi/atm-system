package za.co.nesloedufy.codepotatoes.atm.pojos;
import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;

import java.io.Serializable;
import java.time.LocalDate;


public class Card implements Serializable {
   private String bankName;
   private long cardNumber;
   private long accountNumber;
   private int cvv;
   private int pin;
   private CardStatus cardStatus;
   private LocalDate expiryDate;


   
   

    public Card(String bankName, long cardNumber, long accountNumber, int cvv, int pin, CardStatus cardStatus, LocalDate expiryDate) {
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.cvv = cvv;
        this.pin = pin;
        this.cardStatus = cardStatus;
        this.expiryDate = expiryDate;
    }

    public String getBankName() {
        return bankName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public int getPin() {
        return pin;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Card Details\n-------------\n"+
                "Bank Name: " + bankName+
                "\nCard Number: " + cardNumber +
                "\nAccount Number: " + accountNumber +
                "\nCVV: " + cvv +
                "\nCard Status: " + cardStatus +
                "\nExpiry Date: " + expiryDate;
    }
}
