package com.techelevator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachineTest {
   // private VendingMachine machine;
    private int STARTING_QUANTITY = 5;
    private String TEST_PRODUCT = "A1";
    private String TEST_PRODUCT_NAME = "Potato Crisps";
    private double TEST_PRODUCT_PRICE = 3.05;
    private String TEST_PRODUCT_TYPE = "Chip";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");



    /** constructor test*/
    @Test
    public void constructor_creates_expected_product_list() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        Map<String, Product> actualProducts = machine.getProducts();
        Map<String, Product> expectedProducts = new HashMap<>();
        expectedProducts.put(TEST_PRODUCT, Product.create(TEST_PRODUCT_NAME,TEST_PRODUCT_PRICE,TEST_PRODUCT,TEST_PRODUCT_TYPE));

        Assert.assertEquals(expectedProducts.size(),actualProducts.size());
        int i=0;
        for (Map.Entry<String, Product> entry : expectedProducts.entrySet()) {
            Assert.assertTrue(entry.getValue().equals(actualProducts.get(entry.getKey())));
            i++;
        }
    }


    /**dispenseProduct tests*/
    @Test
    public void dispense_product_decreases_product_quantity_by_1_given_starting_quantity() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
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
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
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


    /**makeChange tests*/
    @Test
    public void make_change_gives_4_quarters_when_fed_1_dollar_as_string() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
        machine.feedMoney("$1");
        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{4,0,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }

    @Test
    public void make_change_gives_0_when_fed_0() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{0,0,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }

    @Test
    public void make_change_gives_4_quarters_when_fed_1_as_int() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
        machine.feedMoney(1);

        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{4,0,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }

    @Test
    public void make_change_gives_0_when_fed_negative_number() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
        machine.feedMoney(-1);

        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{0,0,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }

    @Test
    public void make_change_gives_0_when_fed_41_cents() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
        machine.feedMoney(0.41);

        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{0,0,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }

    @Test
    public void make_change_gives_7200_when_fed_current_balance_is_195() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");
        machine.feedMoney(5);
        machine.makePurchase(TEST_PRODUCT);

        //when
        int[] actualChange = machine.makeChange();
        int[] expectedChange = new int[]{7,2,0,0};

        //then
        Assert.assertTrue(Arrays.equals(actualChange,expectedChange));
    }


    /**feedMoney tests*/
    ///feed negative
    @Test
    public void feed_money_returns_null_for_negative_input() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney(-5);

        Assert.assertEquals(null,moneyFeed);

    }
    ///feed zero
    @Test
    public void feed_money_returns_null_for_0_input() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney(0);

        Assert.assertEquals(null,moneyFeed);

    }
    ///feed String
    @Test
    public void feed_money_returns_null_for_string_with_decimal() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney("$10.0");

        Assert.assertEquals(null,moneyFeed);

    }
    @Test
    public void feed_money_returns_null_for_string_with_dollars() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney("10 dollars");

        Assert.assertEquals(null,moneyFeed);

    }

    @Test
    public void feed_money_returns_null_given_double() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney(10.0);

        Assert.assertEquals(null,moneyFeed);

    }
    @Test
    public void feed_money_returns_correct_amount_given_int() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        BigDecimal moneyFeed = machine.feedMoney(15);

        Assert.assertEquals(15,moneyFeed.floatValue(),.001);

    }

    /** addToLog tests*/
    @Test
    public void add_feed_money_line_directly_to_log() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.addToLog(new BigDecimal(0),new BigDecimal(10), "FEED MONEY:");

        LocalDateTime time = LocalDateTime.now();
        String dateTime = time.format(dateTimeFormatter);
        List<String> expectedLogMessages = new ArrayList<>();
        expectedLogMessages.add(dateTime + ": FEED MONEY: $0.00 $10.00");

        List<String> actualLogMessages = machine.getLogMessages();

        Assert.assertTrue(expectedLogMessages.get(0).equals(actualLogMessages.get(0)));

    }

    @Test
    public void add_log_adds_nothing_given_null_message() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.addToLog(new BigDecimal(0),new BigDecimal(0), null);

        List<String> actualLogMessages = machine.getLogMessages();
        List<String> expectedLogMessages = new ArrayList<>();

        Assert.assertEquals(expectedLogMessages,actualLogMessages);

    }
    @Test
    public void add_log_adds_nothing_given_null_balance() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.addToLog(null,new BigDecimal(0), "FEED MONEY:");

        List<String> actualLogMessages = machine.getLogMessages();
        List<String> expectedLogMessages = new ArrayList<>();

        Assert.assertEquals(expectedLogMessages,actualLogMessages);

    }
    ///calling feedmoney, makepurchase and makechange adds to log
    @Test
    public void feed_money_and_make_purchase_and_make_change_adds_to_log() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.feedMoney(10);
        machine.makePurchase(TEST_PRODUCT);
        machine.makeChange();

        LocalDateTime time = LocalDateTime.now();
        String dateTime = time.format(dateTimeFormatter);
        List<String> expectedLogMessages = new ArrayList<>();
        expectedLogMessages.add(dateTime + ": FEED MONEY: $0.00 $10.00");
        expectedLogMessages.add(dateTime + ": "+TEST_PRODUCT_NAME + " " + TEST_PRODUCT+ " $10.00 $6.95");
        expectedLogMessages.add(dateTime + ": MAKE CHANGE: $6.95 $0.00");

        List<String> actualLogMessages = machine.getLogMessages();

        Assert.assertEquals(expectedLogMessages.size(),actualLogMessages.size());
        for (int i = 0; i < expectedLogMessages.size(); i++) {
            Assert.assertTrue(expectedLogMessages.get(i).equals(actualLogMessages.get(i)));
        }


    }

    /**makePurchase tests*/
    @Test
    public void make_purchase_returns_product_not_found_given_invalid_product_code() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        String returnMessage = machine.makePurchase("blah");

        Assert.assertEquals("Product not found. Please try again.",returnMessage);

    }

    @Test
    public void make_purchase_returns_product_not_found_given_null() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        String returnMessage = machine.makePurchase(null);

        Assert.assertEquals("Product not found. Please try again.",returnMessage);
    }

    @Test
    public void make_purchase_returns_insufficient_funds_if_money_not_fed() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        String returnMessage = machine.makePurchase(TEST_PRODUCT);

        Assert.assertEquals("Insufficient funds. Please try again.",returnMessage);
    }

    @Test
    public void make_purchase_returns_insufficient_funds_if_money_fed_is_not_enough() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.feedMoney("$1");
        String returnMessage = machine.makePurchase(TEST_PRODUCT);

        Assert.assertEquals("Insufficient funds. Please try again.",returnMessage);
    }

    @Test
    public void make_purchase_returns_sold_out_if_product_is_sold_out() {
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        machine.feedMoney("$5");
        for (int i = 0; i < STARTING_QUANTITY; i++) {
            machine.dispenseProduct(TEST_PRODUCT);
        }
        String returnMessage = machine.makePurchase(TEST_PRODUCT);

        Assert.assertEquals(TEST_PRODUCT_NAME + " is sold out! Please try again.",returnMessage);
    }

    @Test
    public void make_purchase_makes_current_balance_195_when_fed_5_dollars_and_purchasing_item_for_305() {
        //given
        VendingMachine machine = new VendingMachine("vendingmachinetest.csv");

        //when
        machine.feedMoney(5);
        machine.makePurchase(TEST_PRODUCT);

        //then
        Assert.assertEquals(1.95,machine.getCurrentBalance().floatValue(),.001);
    }

    //writeSalesLog - just calls fileIO method so can skip?
}
