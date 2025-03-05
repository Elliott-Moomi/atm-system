package za.co.nesloedufy.codepotatoes.atm.pojos;

import za.co.nesloedufy.codepotatoes.atm.enums.UserRoles;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int id;
    private String email;
    private long phoneNumber;
    private String address;
    private UserRoles role;
    private Card card;

    public User(String name, String email, long phoneNumber, String address,UserRoles role, Card card) {
        this.id = 0;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public UserRoles getRole() {
        return role;
    }

    public Card getCard() {
        return card;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    @Override
    public String toString(){
       return "User Details\n-------------\n"+
               "\nID: "+id+
               "\nName: "+name+
               "\nEmail: "+email+
               "\nPhone Number: 0"+phoneNumber+
               "\nAddress: "+address+
               "\nRole:"+role+
               "\n"+ card;

    }
}
