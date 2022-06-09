package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {


    @Test
    public void get_return_message_returns_chew_chew_yum_given_gum() {
        //given
        Product gum = Product.create("Gum",.5,"A1","Gum");

        //when
        String returnMessage = gum.getReturnMessage();

        //then
        Assert.assertEquals("Chew Chew, Yum!",returnMessage);
    }
}

