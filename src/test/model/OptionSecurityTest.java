package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;


public class OptionSecurityTest {
    private OptionSecurity testOption;

    @BeforeEach
    void runBefore() throws ParseException {
        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
    }


    @Test
    void testConstructor() {
        assertEquals("Shopify", testOption.getName());
        assertEquals(50, testOption.getCurrentPrice());
        assertEquals(10, testOption.getQuantity());
        assertEquals(50, testOption.getAveragePrice());
        testOption.setCurrentPrice(80);
        assertEquals(80, testOption.getCurrentPrice());
    }

    @Test
    void testProfitOrLoss() {
        assertEquals(0, testOption.profitOrLoss());
        testOption.setCurrentPrice(100);
        assertEquals(500, testOption.profitOrLoss());
        testOption.setCurrentPrice(40);
        assertEquals(-100, testOption.profitOrLoss());
        testOption.update(2, 100);
        assertEquals(-220.00000000000003, testOption.profitOrLoss());
    }

    @Test
    void testValue() {
        assertEquals(500, testOption.value());
        testOption.setCurrentPrice(1500);
        assertEquals(15000, testOption.value());
        testOption.setCurrentPrice(10);
        assertEquals(100, testOption.value());
    }

    @Test
    void update() throws ParseException {
        testOption.update(1, 100);
        assertEquals(54.54545454545455, testOption.getAveragePrice());
        assertEquals(11, testOption.getQuantity());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.update(10, 1500);
        assertEquals(775, testOption.getAveragePrice());
        assertEquals(20, testOption.getQuantity());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.update(0, 1500);
        assertEquals(50, testOption.getAveragePrice());
        assertEquals(10, testOption.getQuantity());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.update(-5, 1000);
        assertEquals(50, testOption.getAveragePrice());
        assertEquals(5, testOption.getQuantity());
    }

    @Test
    void valid() throws ParseException {
        assertTrue(testOption.valid());
        testOption.update(-15, 1500);
        assertFalse(testOption.valid());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.setCurrentPrice(-50);
        assertFalse(testOption.valid());

        testOption = new OptionSecurity("Shopify", -50, "2022-08-20", 10);
        assertFalse(testOption.valid());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.averagePrice(15, -2500);
        assertFalse(testOption.valid());
    }

    @Test
    void averagePriceTests() throws ParseException {
        assertEquals(50, testOption.getAveragePrice());
        testOption.averagePrice(0, 143);
        assertEquals(50, testOption.getAveragePrice());
        testOption.averagePrice(10, 50);
        assertEquals(50, testOption.getAveragePrice());

        testOption.averagePrice(10, 150);
        assertEquals(100, testOption.getAveragePrice());

        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        testOption.averagePrice(10, 20);
        assertEquals(35, testOption.getAveragePrice());
    }

    //chec
    @Test
    void optionExpiryTests() throws ParseException {
        testOption = new OptionSecurity("Shopify", 50, "2022-08-20", 10);
        assertFalse(testOption.checkIfExpired());
        testOption = new OptionSecurity("Shopify", 50, "2002-08-20", 10);
        assertTrue(testOption.checkIfExpired());
        testOption = new OptionSecurity("Shopify", 50, "2022-03-03", 10);
        assertTrue(testOption.checkIfExpired());
    }

}
