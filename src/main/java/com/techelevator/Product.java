package com.techelevator;

import java.util.Objects;

public abstract class Product {

    private static final int STARTING_QUANTITY = 5;

    private double price;
    private String name;
    private String sound;
    private String slot;
    private String type;
    private int quantity = STARTING_QUANTITY;

    public Product(String name,double price,String slot,String type,String sound){
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.type = type;
        this.sound = sound;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
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

    public static Product create(String name, double price, String slot, String type) {
        switch(type.toUpperCase()) {
            case "GUM":
                return new Gum(name, price, slot);
            case "CHIP":
                return new Chip(name, price, slot);
            case "DRINK":
                return new Drink(name, price, slot);
            case "CANDY":
                return new Candy(name, price, slot);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        if (!isSoldOut()){
            return slot + " | " + name + " | " + price + " | " + quantity + " left";
        } else {
            return slot + " | " + name + " | " + price + " | " + "SOLD OUT";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(sound, product.sound) && Objects.equals(slot, product.slot) && Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, sound, slot, type, quantity);
    }
}
