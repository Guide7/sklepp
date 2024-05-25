package com.example.projectyes;

public class DataItem {
    private int id;
    private String email;
    private int amount;
    private int price;

    public DataItem(int id, String email, int amount, int price) {
        this.id = id;
        this.email = email;
        this.amount = amount;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }
}
