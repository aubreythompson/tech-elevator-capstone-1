package com.techelevator;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private double currentBalance = 0;
    private Map<String,Product> products;

    public VendingMachine(String productsFile){
        FileIO fileIO = new FileIO();
        List<Product> productList = fileIO.readProducts(productsFile);
        Map<String,Product> products = new HashMap<>();
        for (Product product : productList) {
            products.put(product.getSlot(),product);
        }
        this.products = products;
    }

    public Double getCurrentBalance(){
        return currentBalance;
    }

    public Map<String,Product> getProducts(){
        return products;
    }
    public double feedMoney(Object amount){
        if (amount == null){
            System.out.println("Invalid dollar amount entered, please try again");
            return -1;
        }
        try{
            int moneyFed = Integer.parseInt(amount.toString().replace("$", ""));
            this.currentBalance += moneyFed;
            return currentBalance;
        } catch (NumberFormatException e){

        }
        System.out.println("invalid dollar amount entered, please try again");
        return -1;
    }

    public int[] makeChange(){
        double[] changeValue = {.25, .10, .05, .01};
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        int pennies = 0;
        while (currentBalance > 0){
            if (currentBalance >= changeValue[0]){
                currentBalance -= changeValue[0];
                quarters++;
            } else if (currentBalance >= changeValue[1]){
                currentBalance -= changeValue[1];
                dimes++;
            }else if (currentBalance >= changeValue[2]){
                currentBalance -= changeValue[2];
                dimes++;
            }else{
                currentBalance -= changeValue[3];
                pennies++;
            }
        }
        int[] change = {quarters, dimes, nickels, pennies};
        return change;
    }
}
