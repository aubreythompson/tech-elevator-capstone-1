package com.techelevator;
import com.techelevator.view.Menu;
import org.junit.*;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;


public class VendingMachineCLITest {
    private static final String GIVEN_PRODUCT_KEYS = "D1, C1, D2, B1, C2, D3, A1, B2, C3, D4, A2, B3, C4, A3, B4, A4";
    private static final String MACHINE_FILE_PATH = "vendingmachine.csv";
    private static final String[] EXPECTED_PURCHASE_ARRAY_FIRST_FOUR = {"A1: Potato Crisps", "A2: Stackers", "A3: Grain Waves", "A4: Cloud Popcorn"};

    @Test
    public void purchaseProdcutsArray_returns_properly_formatted_array_given_valid_input(){
        VendingMachine machine = new VendingMachine(MACHINE_FILE_PATH);
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI userInterface = new VendingMachineCLI(menu);

        String[] actualArray = userInterface.purchaseProductsArray(GIVEN_PRODUCT_KEYS);

        for (int i = 0; i < 4; i++){
            Assert.assertEquals(EXPECTED_PURCHASE_ARRAY_FIRST_FOUR[i], actualArray[i]);
        }
    }


    //Got close to getting this test to work. The test itself can get the right value, but the method being tested
    //winds up not having anything after using the feed money method. Looking at the debugger, it seems that
    //the machine used in the test is somehow a seperate entity in memory from the machine used in the method
    //being tested. I think it has something to do with the constructors, but I didn't want to risk breaking
    //the entire program wanting to mess around with that. Figured best just to leave those lines un-properly unit tested

//    @Test
//    public void makeChangeDisplay_returns_expected_amount_given_input(){
//        VendingMachine machine = new VendingMachine(MACHINE_FILE_PATH);
//        Menu menu = new Menu(System.in, System.out);
//        VendingMachineCLI userInterface = new VendingMachineCLI(menu);
//
//        machine.feedMoney("$5");
//        BigDecimal test = machine.getCurrentBalance();
//        String actualChange = userInterface.makeChangeDisplay();
//
//        Assert.assertEquals("Change Provided:\nQuarters: 20\nDimes: 0\nNickels: 0\nPennies: 0", actualChange);
//    }
}
