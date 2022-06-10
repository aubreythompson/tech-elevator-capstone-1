package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
