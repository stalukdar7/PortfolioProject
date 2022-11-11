package model;

import exceptions.TickerNotValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    private Stock testStock;
    private double currentPrice = 884.96;

    @BeforeEach
    void runBefore() {
        testStock = new Stock("SHOP.TO", 1143, 10);
    }

    @Test
    void testConstructor() throws TickerNotValid {
        assertEquals("SHOP.TO", testStock.getName());
        assertEquals(currentPrice, testStock.getCurrentPrice());
        assertEquals(10, testStock.getQuantity());
        assertEquals(1143, testStock.getAveragePrice());
    }

    @Test
    void testProfitOrLoss() throws IOException {
        assertEquals(-2580.3999999999996, testStock.profitOrLoss());

        assertEquals(-2580.3999999999996, testStock.profitOrLoss());
        testStock.update(2, 900);
        assertEquals(-2610.4799999999996, testStock.profitOrLoss());
    }


    @Test
    void update() throws IOException {
        testStock.update(1,1000);
        assertEquals(1130,testStock.getAveragePrice());
        assertEquals(11,testStock.getQuantity());
        assertEquals(9734.560000000001, testStock.value());

        testStock = new Stock("SHOP.TO",1143,10);
        testStock.update(10,1500);
        assertEquals(1321.5,testStock.getAveragePrice());
        assertEquals(20,testStock.getQuantity());
        assertEquals(17699.2,testStock.value());

        testStock = new Stock("SHOP.TO",1143,10);
        testStock.update(0,1500);
        assertEquals(1143,testStock.getAveragePrice());
        assertEquals(10,testStock.getQuantity());
        assertEquals(8849.6,testStock.value());

        testStock = new Stock("SHOP.TO",1143,10);
        testStock.update(-5,1000);
        assertEquals(1143,testStock.getAveragePrice());
        assertEquals(5,testStock.getQuantity());
        assertEquals(4424.8,testStock.value());
    }

    @Test
    void valid() {
        assertTrue(testStock.valid());
        testStock.update(-15,1500);
        assertFalse(testStock.valid());

        testStock = new Stock("SHOP.TO",-1143,10);
        assertFalse(testStock.valid());

        testStock = new Stock("SHOP.TO",1143,10);
        testStock.averagePrice(15,-2500);
        assertFalse(testStock.valid());
    }

    @Test
    void averagePriceTests() {
        assertEquals(1143,testStock.getAveragePrice());
        testStock.averagePrice(0,1143);
        assertEquals(1143,testStock.getAveragePrice());
        testStock.averagePrice(10,1143);
        assertEquals(1143,testStock.getAveragePrice());

        testStock.averagePrice(10,1500);
        assertEquals(1321.5,testStock.getAveragePrice());

        testStock = new Stock("SHOP.TO",1143,10);
        testStock.averagePrice(10,1000);
        assertEquals(1071.5,testStock.getAveragePrice());
    }

    @Test
    void additionalTests(){

    }

}