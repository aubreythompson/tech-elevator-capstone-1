package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FileIOTest {

    @Test
    public void read_products_creates_expected_product_list() {
        //given
        FileIO fileIO = new FileIO();

        //when
        List<Product> actualProducts = fileIO.readProducts("vendingmachine.csv");

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(Product.create("Potato Crisps",3.05,"A1","Chip"));
        expectedProducts.add(Product.create("Stackers",1.45,"A2","Chip"));
        expectedProducts.add(Product.create("Grain Waves",2.75,"A3","Chip"));
        expectedProducts.add(Product.create("Cloud Popcorn",3.65,"A4","Chip"));
        expectedProducts.add(Product.create("Moonpie",1.80,"B1","Candy"));
        expectedProducts.add(Product.create("Cowtales",1.50,"B2","Candy"));
        expectedProducts.add(Product.create("Wonka Bar",1.50,"B3","Candy"));
        expectedProducts.add(Product.create("Crunchie",1.75,"B4","Candy"));
        expectedProducts.add(Product.create("Cola",1.25,"C1","Drink"));
        expectedProducts.add(Product.create("Dr. Salt",1.50,"C2","Drink"));
        expectedProducts.add(Product.create("Mountain Melter",1.50,"C3","Drink"));
        expectedProducts.add(Product.create("Heavy",1.50,"C4","Drink"));
        expectedProducts.add(Product.create("U-Chews",0.85,"D1","Gum"));
        expectedProducts.add(Product.create("Little League Chew",0.95,"D2","Gum"));
        expectedProducts.add(Product.create("Chiclets",0.75,"D3","Gum"));
        expectedProducts.add(Product.create("Triplemint",0.75,"D4","Gum"));


        //then
        Assert.assertEquals(expectedProducts.size(),actualProducts.size());
        int i = 0;
        for (Product product : expectedProducts) {
            Assert.assertTrue(product.equals(actualProducts.get(i)));
            i++;
        }

    }
}
