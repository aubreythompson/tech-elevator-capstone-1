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

/** Vending Machine Class
 * Vending Machine stores the products, vends them, takes money, makes change, and logs the interactions.
 *
 */

public class VendingMachine {
    private BigDecimal currentBalance = new BigDecimal(0);
    private Map<String,Product> products;  //a hash map of Product codes (also called slots) and the Product objects themselves
    private List<String> logMessages = new ArrayList<>(); //this is the detailed log of money in and out
    private List<String> namesOfItemSold = new ArrayList<>(); //this is the list of items sold for the sales log
    private FileIO fileIO = new FileIO();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private static final BigDecimal quarter = new BigDecimal(.25).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal dime = new BigDecimal(.10).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal nickel = new BigDecimal(.05).setScale(2, RoundingMode.HALF_EVEN);
    private static final BigDecimal pennie = new BigDecimal(.01).setScale(2, RoundingMode.HALF_EVEN);
    private static final String MAKE_CHANGE = "MAKE CHANGE:";
    private static final String FEED_MONEY = "FEED MONEY:";



    /**
     * Class Constructor for Vending Machine. It always takes a sourceFile specifying what products are there.
     * It calls FileIO to read the file and turn it into a List of String arrays.
     * The array is formatted as such: [Product Code/Slot, Product name, Product price, Product type]
     * The constructor uses this array to create a product out of each String array,
     * and adds it to the products variable.
     * @param sourceFile
     */
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

    public List<String> getNamesOfItemSold() {
        return namesOfItemSold;
    }
    public List<String> getLogMessages() {
        return logMessages;
    }

    /**
     * Take money into the machine.
     * @param amount - can be a string representing an integer, with or without $, or integer
     * @return current balance of the machine
     *
     * This method updates the logMessages.
     */
    public BigDecimal feedMoney(Object amount){
        if (amount == null){
            return null;
        }
        BigDecimal oldBalance = getCurrentBalance();
        try{
            int moneyFed = Integer.parseInt(amount.toString().replace("$", ""));
            if (moneyFed>0) {
                this.currentBalance = this.currentBalance.add(new BigDecimal(moneyFed)).setScale(2, RoundingMode.HALF_EVEN);
                addToLog(oldBalance, this.currentBalance, FEED_MONEY);
                return currentBalance;
            }
        } catch (NumberFormatException e){

        }
        return null;
    }

    /**
     * Handle an (attempted) purchase.
     * @param productCode which represents the key in the hashmap of products
     * @return A message explaining whether the object was purchased, and why not if not.
     *
     * This method updates the log messages and the list of items sold if the purchase was made successfully.
     */
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

    /**
     * Decrease product quantity by one if it is not sold out.
     * @param productCode
     * @return boolean to represent whether the product was dispensed.
     */
    public boolean dispenseProduct(String productCode) {
        Product product = this.products.get(productCode);
        if (!product.isSoldOut()) {
            product.setQuantity(product.getQuantity()-1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Give the user their remaining balance in the fewest amount of coins.
      * @return an integer array [numberOfQuarters,numberOfDimes,numberOfNickels,numberOfPennies]
     *
     * This method adds to log and sets the current balance to zero.
     */
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
            } //this shouldn't normally be used but may if the program encounters an imprecise float
        }
        int[] change = {quarters, dimes, nickels, pennies};
        addToLog(oldBalance,new BigDecimal(0),MAKE_CHANGE);
        return change;
    }

    /**
     * Adds to the list of log messages for this machine. Happens when the machine is fed money,
     * the user makes a purchase, and when the machine dispenses change.
     * @param oldBalance the balance the machine had before the transaction
     * @param newBalance the balance after
     * @param logInfo what kind of transaction it was out of the three above
     */
    public void addToLog(BigDecimal oldBalance, BigDecimal newBalance,String logInfo) {
        LocalDateTime time = LocalDateTime.now();
        String dateTime = time.format(dateTimeFormatter);
        if (logInfo!=null && oldBalance!=null && newBalance!=null){
            this.logMessages.add(dateTime + ": " +  logInfo + " " + NumberFormat.getCurrencyInstance().format(oldBalance) + " " + NumberFormat.getCurrencyInstance().format(newBalance));
        }
    }

    public boolean writeLog(String fileName) {
        return fileIO.writeLog(fileName,this.logMessages);
    }

    public boolean writeSalesLog(String fileName){
        return fileIO.writePersistentSalesLog(fileName, this.namesOfItemSold);
    }

    public HashMap<String,Integer> readSalesLog(String fileName){
        return fileIO.readSalesLog(fileName);
    }
}
