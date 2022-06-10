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

    /**Test sold out*/
    @Test
    public void product_of_quantity_zero_returns_sold_out_message() {
        //given
        Product candy = Product.create("Candy",0.5,"A1","Candy");

        //when
        candy.setQuantity(0);

        //then
        Assert.assertTrue(candy.isSoldOut());
        Assert.assertEquals("A1 | Candy | 0.5 | SOLD OUT",candy.toString());
    }


}

