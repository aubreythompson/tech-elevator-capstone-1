package com.techelevator;

public abstract class Product {

    private static final int STARTING_QUANTITY = 5;

    private double price;
    private String name;
    private String sound;
    private String slot;
    private int quantity = STARTING_QUANTITY;

    public Product(String name,double price,String slot,String sound){
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.sound = sound;
    }

    public double getPrice() {
        return price;
    }

    public String getReturnMessage() {
        return sound + " " + sound + ", Yum!";
    }

    public String getSlot() {
        return slot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public boolean isSoldOut() {
        if (this.quantity==0) {
            return true;
        }
        return false;
    }
}
