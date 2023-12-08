package model;

public class Card {
    private int passwrod;
    private int number;
    public Card(int passwrod, int number){
        this.passwrod = passwrod;
        this.number = number;
    }

    public int getPasswrod() {
        return passwrod;
    }

    public int getNumber() {
        return number;
    }
}
