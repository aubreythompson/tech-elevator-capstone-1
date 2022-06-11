package com.techelevator;

import java.text.NumberFormat;
import java.util.Objects;

/**
 * Abstract product class. Extended by "types" Chip, Gum, Drink, and Candy. All products should be specified when
 * constructed using the "create" function.
 *
 * Product is a class for storing data.
 */

public abstract class Product {

    private static final int STARTING_QUANTITY = 5;

    private double price;
    private String name;
    private String sound;
    private String code; //slot is used interchangeably with c
    private String type;
    private int quantity = STARTING_QUANTITY;

    public Product(String name,double price,String code,String type,String sound){
        this.name = name;
        this.price = price;
        this.code = code;
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

    public String getCode() {
        return code;
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

    public static Product create(String name, double price, String code, String type) {
        switch(type.toUpperCase()) {
            case "GUM":
                return new Gum(name, price, code);
            case "CHIP":
                return new Chip(name, price, code);
            case "DRINK":
                return new Drink(name, price, code);
            case "CANDY":
                return new Candy(name, price, code);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        if (!isSoldOut()){
            return code + " | " + name + " | " + NumberFormat.getCurrencyInstance().format(price) + " | " + quantity + " left";
        } else {
            return code + " | " + name + " | " + NumberFormat.getCurrencyInstance().format(price) + " | " + "SOLD OUT";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(sound, product.sound) && Objects.equals(code, product.code) && Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, sound, code, type, quantity);
    }
}
