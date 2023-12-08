package model;

public class Card {
    private int passwrod;
    private int number;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Card(int passwrod, int number, int amount){
        this.passwrod = passwrod;
        this.number = number;
        this.amount = amount;
    }

    public int getPasswrod() {
        return passwrod;
    }

    public int getNumber() {
        return number;
    }
}
