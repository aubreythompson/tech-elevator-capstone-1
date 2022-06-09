package com.techelevator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private double currentBalance = 0;
    private Map<String,Product> products;
    private List<String> logMessages = new ArrayList<>();

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

    public List<String> getLogMessages() {
        return logMessages;
    }

    public Map<String,Product> getProducts(){
        return products;
    }
    public double feedMoney(Object amount){
        if (amount == null){
            System.out.println("Invalid dollar amount entered, please try again");
            return -1;
        }
        double oldBalance = this.currentBalance;
        try{
            int moneyFed = Integer.parseInt(amount.toString().replace("$", ""));
            this.currentBalance += moneyFed;
            log(oldBalance,this.currentBalance,"FEED MONEY:");
            return currentBalance;
        } catch (NumberFormatException e){

        }
        System.out.println("invalid dollar amount entered, please try again");
        return -1;
    }

    public String makePurchase(String productCode) {
        Product product = this.products.get(productCode);
        if (this.currentBalance >= product.getPrice()) {
             boolean purchaseSuccessful = this.dispenseProduct(productCode);
             if (purchaseSuccessful) {
                 double oldBalance = this.currentBalance;
                 this.currentBalance -= product.getPrice();
                 double newBalance = this.currentBalance;
                 this.log(oldBalance,newBalance,product.getName() + " " + product.getSlot());
                 return "You've purchased " + product.getName() + " for " + product.getPrice() + "! " + product.getReturnMessage();
             } else {
                 return product.getName() + " is sold out! Please try again.";
             }
        } else {
            return "Insufficient funds. Please try again.";
        }
    }

    public boolean dispenseProduct(String productCode) {
        Product product = this.products.get(productCode);
        if (!product.isSoldOut()) {
            product.setQuantity(product.getQuantity()-1);
            return true;
        } else {
            return false;
        }
    }

    public void log(double oldBalance, double newBalance,String logInfo) {
        this.logMessages.add("date and time " +  logInfo + " " + oldBalance + " " + newBalance);
    }

    public int[] makeChange(){
        double[] changeValue = {.25, .10, .05, .01};
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        int pennies = 0;
        double oldBalance = this.currentBalance;
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
        log(oldBalance,0,"MAKE CHANGE:");
        return change;
    }
}
