package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    /**Test constructors*/
    @Test
    public void get_return_message_returns_chew_chew_yum_given_gum() {
        //given
        Product gum = Product.create("Gum",.5,"A1","Gum");

        //when
        String returnMessage = gum.getReturnMessage();

        //then
        Assert.assertEquals("Chew Chew, Yum!",returnMessage);
    }

    @Test
    public void get_return_message_returns_crunch_crunch_yum_given_chip() {
        //given
        Product chip = Product.create("Chips",.5,"A1","Chip");

        //when
        String returnMessage = chip.getReturnMessage();

        //then
        Assert.assertEquals("Crunch Crunch, Yum!",returnMessage);
    }

    /**Test isSoldOut returns true when quantity is zero*/
    @Test
    public void product_of_quantity_zero_returns_sold_out_message() {
        //given
        Product candy = Product.create("Candy",0.5,"A1","Candy");

        //when
        candy.setQuantity(0);

        //then
        Assert.assertTrue(candy.isSoldOut());
        Assert.assertEquals("A1 | Candy | $0.50 | SOLD OUT",candy.toString());
    }

    @Test
    public void get_return_price_returns_product_price() {
        //given
        Product drink = Product.create("Drink",.5,"A1","Drink");

        //when
        double test = drink.getPrice();

        //then
        Assert.assertEquals(.5, test, 0.009);
    }

    @Test
    public void get_return_type_returns_product_type() {
        //given
        Product chip = Product.create("Chips",.5,"A1","Chip");

        //when
        String test = chip.getType();

        //then
        Assert.assertEquals("Chip", test);
    }

    @Test
    public void get_return_slot_returns_product_slot() {
        //given
        Product chip = Product.create("Chips",.5,"A1","Chip");

        //when
        String test = chip.getSlot();

        //then
        Assert.assertEquals("A1", test);
    }

    @Test
    public void get_quantity_price_returns_product_quantity() {
        //given
        Product chip = Product.create("Chips",.5,"A1","Chip");

        //when
        int test = chip.getQuantity();

        //then
        Assert.assertEquals(5, test);
    }

    @Test
    public void get_return_name_returns_product_name() {
        //given
        Product chip = Product.create("Chips",.5,"A1","Chip");

        //when
        String test = chip.getName();

        //then
        Assert.assertEquals("Chips", test);
    }

    @Test
    public void product_of_greater_quantity_than_zero_returns_proper_quantity() {
        //given
        Product candy = Product.create("Candy",0.5,"A1","Candy");

        //when
        candy.setQuantity(3);

        //then
        Assert.assertFalse(candy.isSoldOut());
        Assert.assertEquals("A1 | Candy | $0.50 | 3 left",candy.toString());
    }
}

