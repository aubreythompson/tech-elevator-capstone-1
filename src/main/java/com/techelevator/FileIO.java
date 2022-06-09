package com.techelevator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}
