package com.techelevator;

import java.io.*;
import java.util.*;

/**
 * This class interacts with external files for
 * - reading products into the vending machine
 * - writing the transaction log for an instance of the machine until it exits
 * - a persistent record of number of each item sold
 */

public class FileIO {


    /**
     * Create list of String arrays, each of which represents a product.
     * Represented as [code, name, price, type]
     * @param file the csv file that represents the products
     * @return list of String arrays products
     */
    public List<String[]> readProducts(String file){
        List<String[]> products = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(file))) {
            while (fileScanner.hasNextLine()) {
                String productText = fileScanner.nextLine();
                String[] productArray = productText.split("\\|");
                products.add(productArray);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Source file not found, program exiting");
        }

        return products;
    }

    /**
     *
     * @param file to write the log to
     * @param logMessages each line in the log from the vending machine
     * @return boolean successfully written
     */
    public boolean writeLog(String file, List<String> logMessages) {
        //catch null arguments
        if (file == null){
            return false;
        }
        //catch filePathway not existing or not being a file
        if (!new File(file).exists() || !new File(file).isFile()){
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))) {
            if (logMessages!=null && !logMessages.isEmpty()){
                for (String line : logMessages) {
                    writer.println(line);
                }
                return true;
            } else {
                System.out.println("No activity was detected, so no logs were written.");
                return false;
            }

        } catch(FileNotFoundException e) {
            System.out.println("Destination file not found, program exiting");
        }
        return false;
    }

    /**
     * Read from the persistent sales log and update it with today's sales.
     * @param file log file
     * @param todaysSales String list of names of items sold
     * @return boolean successfully written
     */
    public boolean writePersistentSalesLog(String file, List<String> todaysSales){
        //catch null arguments
        if (file == null || todaysSales == null){
            return false;
        }
        //catch filePathway not existing or not being a file
        if (!new File(file).exists() || !new File(file).isFile()){
            return false;
        }

        //recieve old salesLog from readSalesLog Method
        HashMap<String, Integer> salesLog = readSalesLog(file);

        //loop to add today's items to Sales log, or update amount sold if item already exists
        int valueTracker;
        for (String item : todaysSales){
            if (salesLog.containsKey(item)){
                valueTracker = salesLog.get(item);
                valueTracker++;
                salesLog.put(item, valueTracker);
            } else {
                salesLog.put(item, 1);
            }
        }

        //writes hashmap to sales log (the example didn't seem organized alphabetically, so hashmap because easier)
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))){
            for (Map.Entry<String, Integer> item : salesLog.entrySet()){
                writer.println(item.getKey() + "|" + item.getValue());
            }
            return true;
        } catch (FileNotFoundException e){
            System.out.println("Destination file not found, exiting program");
        }
        return false;
    }

    /**
     * Interprets the current persistent sales log
     * @param file
     * @return the log as a hashmap of product name and quantity sold over time
     */
    public HashMap<String, Integer> readSalesLog(String file){
        HashMap<String, Integer> salesLog = new HashMap<>();

        //catch null file
        if (file == null)
            return salesLog;

        //attempt to read old sales log, place info into a hashmap
        try (Scanner fileScanner = new Scanner(new File(file))){
            while (fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split("\\|");
                try{
                    salesLog.put(line[0], Integer.parseInt(line[1]));
                } catch (NumberFormatException e){
                    System.out.println("Error reading previous days sales log");
                    salesLog.clear();
                    return salesLog;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Sales log not found");
        }

        return salesLog;
    }
}
