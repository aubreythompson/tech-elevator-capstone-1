package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class FileIOTest {

    @Test
    public void read_products_creates_expected_product_list() {
        //given
        FileIO fileIO = new FileIO();

        //when
        List<String[]> actualProducts = fileIO.readProducts("vendingmachine.csv");

        List<String[]> expectedProducts = new ArrayList<>();
        expectedProducts.add(new String[]{"A1","Potato Crisps","3.05","Chip"});
        expectedProducts.add(new String[]{"A2","Stackers","1.45","Chip"});
        expectedProducts.add(new String[]{"A3","Grain Waves","2.75","Chip"});
        expectedProducts.add(new String[]{"A4","Cloud Popcorn","3.65","Chip"});
        expectedProducts.add(new String[]{"B1","Moonpie","1.80","Candy"});
        expectedProducts.add(new String[]{"B2","Cowtales","1.50","Candy"});
        expectedProducts.add(new String[]{"B3","Wonka Bar","1.50","Candy"});
        expectedProducts.add(new String[]{"B4","Crunchie","1.75","Candy"});
        expectedProducts.add(new String[]{"C1","Cola","1.25","Drink"});
        expectedProducts.add(new String[]{"C2","Dr. Salt","1.50","Drink"});
        expectedProducts.add(new String[]{"C3","Mountain Melter","1.50","Drink"});
        expectedProducts.add(new String[]{"C4","Heavy","1.50","Drink"});
        expectedProducts.add(new String[]{"D1","U-Chews","0.85","Gum"});
        expectedProducts.add(new String[]{"D2","Little League Chew","0.95","Gum"});
        expectedProducts.add(new String[]{"D3","Chiclets","0.75","Gum"});
        expectedProducts.add(new String[]{"D4","Triplemint","0.75","Gum"});


        //then
        Assert.assertEquals(expectedProducts.size(),actualProducts.size());
        for (int i=0; i<expectedProducts.size();i++) {
            String[] actualProductArray = actualProducts.get(i);
            String[] expectedProductArray = expectedProducts.get(i);
            for (int j=0; j<4; j++) {
                Assert.assertTrue(expectedProductArray[j].equals(actualProductArray[j]));
            }
        }

    }
    @Test
    public void read_sales_log_creates_hashmap_correctly() {
        //given
        FileIO fileIO = new FileIO();

        //when
        HashMap<String,Integer> actualProductValues = fileIO.readSalesLog("salesLogReadTest.txt");
        HashMap<String,Integer> expectedProductValues = new HashMap<>();
        expectedProductValues.put("Cola",6);
        expectedProductValues.put("Heavy",1);
        expectedProductValues.put("Cloud Popcorn",1);
        //then
        Assert.assertEquals(expectedProductValues.size(),actualProductValues.size());
        Assert.assertTrue(Arrays.equals(expectedProductValues.values().toArray(),actualProductValues.values().toArray()));
        for (String productName : expectedProductValues.keySet()) {
            Assert.assertEquals(expectedProductValues.get(productName),actualProductValues.get(productName));
        }
    }

    @Test
    public void write_sales_log_writes_when_given_existing_filepath(){
        FileIO fileIO = new FileIO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Chips");
        arrayList.add("Testing Snacks");

        boolean doesWrite = fileIO.writePersistentSalesLog("salesLogWriteTest.txt", arrayList);

        Assert.assertTrue(doesWrite);
    }

    @Test
    public void write_sales_log_does_not_write_given_bad_filepath(){
        FileIO fileIO = new FileIO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Chips");
        arrayList.add("Testing Snacks");

        boolean doesWrite = fileIO.writePersistentSalesLog("ILikeToBreakThings.txt", arrayList);

        Assert.assertFalse(doesWrite);
    }

    @Test
    public void write_sales_log_returns_false_given_null_filepath(){
        FileIO fileIO = new FileIO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Chips");
        arrayList.add("Testing Snacks");

        boolean doesWrite = fileIO.writePersistentSalesLog(null, arrayList);

        Assert.assertFalse(doesWrite);
    }

    @Test
    public void write_sales_log_returns_false_given_null_list(){
        FileIO fileIO = new FileIO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Chips");
        arrayList.add("Testing Snacks");

        boolean doesWrite = fileIO.writePersistentSalesLog("salesLogWriteTest.txt", null);

        Assert.assertFalse(doesWrite);
    }

    @Test
    public void write_sales_log_writes_when_given_empty_list(){
        FileIO fileIO = new FileIO();
        List<String> arrayList = new ArrayList<>();

        boolean doesWrite = fileIO.writePersistentSalesLog("salesLogWriteTest.txt", arrayList);

        Assert.assertTrue(doesWrite);
    }

    @Test
    public void read_sales_log_returns_empty_map_when_given_null_filepath(){
        FileIO fileIO = new FileIO();

        HashMap<String, Integer> testMap = fileIO.readSalesLog(null);

        Assert.assertEquals(0, testMap.size());
    }

    @Test
    public void read_sales_log_returns_empty_map_when_given_bad_filepath() {
        FileIO fileIO = new FileIO();

        HashMap<String, Integer> testMap = fileIO.readSalesLog("iBrokededItAgain");

        Assert.assertEquals(0, testMap.size());
    }

    @Test
    public void read_sales_log_catches_bad_number_formatting(){
        FileIO fileIO = new FileIO();

        HashMap<String, Integer> testMap = fileIO.readSalesLog("badSalesLogReadTest.txt");

        Assert.assertEquals(0, testMap.size());
    }

    @Test
    public void writeLog_writes_when_given_valid_filePath_and_populated_array(){
        FileIO fileIO = new FileIO();
        List<String> testSales = new ArrayList<>();

        testSales.add("06/10/2022 06:29 PM: FEED MONEY: $0.00 $10.00");
        testSales.add("06/10/2022 06:21 PM: Potato Crisps A1 $10.00 $6.95");
        testSales.add("06/10/2022 06:29 PM: Potato Crisps A1 $6.95 $3.90");
        testSales.add("06/10/2022 06:29 PM: MAKE CHANGE: $3.90 $0.00");

        boolean doesWrite = fileIO.writeLog("logTest.txt", testSales);

        Assert.assertTrue(doesWrite);
    }

    @Test
    public void writeLog_does_not_write_when_given_valid_filePath_and_empty_array(){
        FileIO fileIO = new FileIO();
        List<String> testSales = new ArrayList<>();

        boolean doesWrite = fileIO.writeLog("logTest.txt", testSales);

        Assert.assertFalse(doesWrite);
    }

    @Test
    public void writeLog_does_not_write_when_given_null_filePath_and_populated_array(){
        FileIO fileIO = new FileIO();
        List<String> testSales = new ArrayList<>();

        testSales.add("06/10/2022 06:29 PM: FEED MONEY: $0.00 $10.00");
        testSales.add("06/10/2022 06:21 PM: Potato Crisps A1 $10.00 $6.95");
        testSales.add("06/10/2022 06:29 PM: Potato Crisps A1 $6.95 $3.90");
        testSales.add("06/10/2022 06:29 PM: MAKE CHANGE: $3.90 $0.00");

        boolean doesWrite = fileIO.writeLog(null, testSales);

        Assert.assertFalse(doesWrite);
    }

    @Test
    public void writeLog_does_not_write_when_given_invalid_filePath_and_populated_array(){
        FileIO fileIO = new FileIO();
        List<String> testSales = new ArrayList<>();

        testSales.add("06/10/2022 06:29 PM: FEED MONEY: $0.00 $10.00");
        testSales.add("06/10/2022 06:21 PM: Potato Crisps A1 $10.00 $6.95");
        testSales.add("06/10/2022 06:29 PM: Potato Crisps A1 $6.95 $3.90");
        testSales.add("06/10/2022 06:29 PM: MAKE CHANGE: $3.90 $0.00");

        boolean doesWrite = fileIO.writeLog("stuffBreaker.txt", testSales);

        Assert.assertFalse(doesWrite);
    }
}