package com.techelevator;

import java.io.*;
import java.util.*;

public class FileIO {

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

    public void writeLog(String file, List<String> logMessages) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))) {
            for (String line : logMessages) {
                writer.println(line);
            }
        } catch(FileNotFoundException e) {
            System.out.println("Destination file not found, program exiting");
        }
    }

    public boolean read_WritePersistentSalesLog(String file, List<String> todaysSales){
        HashMap<String, Integer> salesLog = new HashMap<>();

        //attempt to read old sales log, place info into a hashmap
        try (Scanner fileScanner = new Scanner(new File(file))){
            while (fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split("\\|");
                try{
                    salesLog.put(line[0], Integer.parseInt(line[1]));
                } catch (NumberFormatException e){
                    System.out.println("Error reading previous days sales log");
                    return false;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Sales log not found");
        }

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

    public HashMap<String, Integer> readSalesLog(String file){
        HashMap<String, Integer> salesLog = new HashMap<>();

        //attempt to read old sales log, place info into a hashmap
        try (Scanner fileScanner = new Scanner(new File(file))){
            while (fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split("\\|");
                try{
                    salesLog.put(line[0], Integer.parseInt(line[1]));
                } catch (NumberFormatException e){
                    System.out.println("Error reading previous days sales log");
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Sales log not found");
        }

        return salesLog;
    }
}
