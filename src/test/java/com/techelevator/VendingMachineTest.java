package com.techelevator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class VendingMachineTest {
    private VendingMachine machine;
    private int STARTING_QUANTITY = 5;
    private String TEST_PRODUCT = "A1";

    @Before
    public void setup() {
        machine = new VendingMachine("vendingmachine.csv");

    }


    //dispenseProduct
    @Test
    public void dispense_product_decreases_product_quantity_by_1_given_starting_quantity() {
        //given
        Map<String,Product> products = machine.getProducts();

        //when
        boolean dispensedProduct = machine.dispenseProduct(TEST_PRODUCT);

        //then
        Assert.assertTrue(dispensedProduct);
        Assert.assertEquals(STARTING_QUANTITY-1,products.get(TEST_PRODUCT).getQuantity());

    }
    @Test
    public void dispense_product_does_nothing_if_product_is_sold_out() {
        //given
        Map<String,Product> products = machine.getProducts();

        //when
        for (int i = 0; i < STARTING_QUANTITY; i++) {
            machine.dispenseProduct(TEST_PRODUCT);
        }
        boolean productDispensed = machine.dispenseProduct(TEST_PRODUCT);


        //then
        Assert.assertTrue(!productDispensed);
        Assert.assertEquals(0,products.get(TEST_PRODUCT).getQuantity());
    }


    //makeChange

    //feedMoney

    //addToLog

    //makePurchase

    //writeSalesLog
}
