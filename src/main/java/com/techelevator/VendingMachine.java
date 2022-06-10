package com.techelevator;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private BigDecimal currentBalance = new BigDecimal(0);
    private Map<String,Product> products;
    private List<String> logMessages = new ArrayList<>();
    private List<String> namesOfItemSold = new ArrayList<>();
    private FileIO fileIO = new FileIO();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private static final BigDecimal quarter = new BigDecimal(.25).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal dime = new BigDecimal(.10).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal nickel = new BigDecimal(.05).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal pennie = new BigDecimal(.01).setScale(2, RoundingMode.HALF_EVEN);

    public VendingMachine(String sourceFile){
        List<String[]> productsList = fileIO.readProducts(sourceFile);
        Map<String,Product> products = new HashMap<>();
        for (String[] productArray : productsList) {
            Product product = Product.create(productArray[1],Double.parseDouble(productArray[2]),productArray[0],productArray[3]);
            products.put(product.getSlot(),product);
        }
        this.products = products;
    }

    public BigDecimal getCurrentBalance(){
        return currentBalance.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Map<String,Product> getProducts(){
        return products;
    }

    public List<String> getLogMessages() {
        return logMessages;
    }
    public BigDecimal feedMoney(Object amount){
        if (amount == null){
            return null;
        }
        BigDecimal oldBalance = getCurrentBalance();
        try{
            int moneyFed = Integer.parseInt(amount.toString().replace("$", ""));
            if (moneyFed>0) {
                this.currentBalance = this.currentBalance.add(new BigDecimal(moneyFed)).setScale(2, RoundingMode.HALF_EVEN);
                addToLog(oldBalance, this.currentBalance, "FEED MONEY:");
                return currentBalance;
            }
        } catch (NumberFormatException e){

        }
        return null;
    }

    public String makePurchase(String productCode) {
        Product product = this.products.get(productCode);
        if (product!=null) {
            if (getCurrentBalance().compareTo(new BigDecimal(product.getPrice())) >= 0) {
                boolean purchaseSuccessful = this.dispenseProduct(productCode);
                if (purchaseSuccessful) {
                    BigDecimal oldBalance = getCurrentBalance();
                    this.currentBalance = this.currentBalance.subtract(new BigDecimal(product.getPrice())).setScale(2, RoundingMode.HALF_EVEN);
                    BigDecimal newBalance = this.currentBalance;
                    this.addToLog(oldBalance, newBalance, product.getName() + " " + product.getSlot());
                    this.namesOfItemSold.add(product.getName());
                    return "You've purchased " + product.getName() + " for " + NumberFormat.getCurrencyInstance().format(product.getPrice()) + "! " + product.getReturnMessage();
                } else {
                    return product.getName() + " is sold out! Please try again.";
                }
            } else {
                return "Insufficient funds. Please try again.";
            }
        } else {
            return "Product not found. Please try again.";
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

    public int[] makeChange(){
        BigDecimal[] changeValue = {quarter, dime, nickel, pennie};
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        int pennies = 0;
        BigDecimal oldBalance = getCurrentBalance();
        //Note on this if statement. BigDecimal.compareTo accepts another Bigdecimal number. It returns -1 if less than, 0 if equal to, 1 if greater than.
        while (currentBalance.compareTo(new BigDecimal(0)) > 0){
            if (currentBalance.compareTo(changeValue[0]) >= 0){
                currentBalance = currentBalance.subtract(changeValue[0]).setScale(2, RoundingMode.HALF_EVEN);
                quarters++;
            } else if (currentBalance.compareTo(changeValue[1]) >= 0){
                currentBalance = currentBalance.subtract(changeValue[1]).setScale(2, RoundingMode.HALF_EVEN);
                dimes++;
            }else if (currentBalance.compareTo(changeValue[2]) >= 0){
                currentBalance = currentBalance.subtract(changeValue[2]).setScale(2, RoundingMode.HALF_EVEN);
                nickels++;
            }else{
                currentBalance = currentBalance.subtract(changeValue[3]).setScale(2, RoundingMode.HALF_EVEN);
                pennies++;
            }
        }
        int[] change = {quarters, dimes, nickels, pennies};
        addToLog(oldBalance,new BigDecimal(0),"MAKE CHANGE:");
        return change;
    }

    public void addToLog(BigDecimal oldBalance, BigDecimal newBalance,String logInfo) {
        LocalDateTime time = LocalDateTime.now();
        String dateTime = time.format(dateTimeFormatter);
        if (logInfo!=null && oldBalance!=null && newBalance!=null){
            this.logMessages.add(dateTime + ": " +  logInfo + " " + NumberFormat.getCurrencyInstance().format(oldBalance) + " " + NumberFormat.getCurrencyInstance().format(newBalance));
        }
    }

    public void writeLog(String fileName) {
        fileIO.writeLog(fileName,this.logMessages);
    }

    public void writeSalesLog(String fileName){
        fileIO.read_WritePersistentSalesLog(fileName, this.namesOfItemSold);
    }

    public HashMap<String,Integer> readSalesLog(String fileName){
        return fileIO.readSalesLog(fileName);
    }
}
