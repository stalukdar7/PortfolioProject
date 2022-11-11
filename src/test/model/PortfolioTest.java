package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PortfolioTest {
    Portfolio portfolio;
    OptionSecurity testOption;
    Stock testStock;


    @BeforeEach
    void runBefore() throws ParseException {
        portfolio = new Portfolio(5000);
        testStock = new Stock("SHOP.TO", 500, 10);
        testOption = new OptionSecurity("SHOP.TO", 50, "2022-08-20", 10);
        //TICKER

    }

    @Test
    void testStockAdd() throws IOException {
        portfolio.addStock("SHOP.TO", 500, 10);
        assertEquals(portfolio.getSecurity(0).getName(), testStock.getName());
        assertEquals(portfolio.getSecurity(0).value(), testStock.value());
    }

    @Test
    void testOptionAdd() throws ParseException, IOException {
        portfolio.addOptions("SHOP.TO", 50, "2022-08-20", 10);
        assertEquals(portfolio.getSecurity(0).getName(), testOption.getName());
        assertEquals(portfolio.getSecurity(0).value(), testOption.value());
    }

    @Test
    void checkNumberProduction() throws ParseException {
        portfolio.addStock("SHOP.TO", 500, 10);
        portfolio.addOptions("SHOP.TO", 50, "2022-08-20", 10);
        portfolio.printAllSecuritiesNumberedValueQuantity();
        portfolio.printAllSecuritiesNumbered();
    }

    @Test
    void checkTotalValue() throws ParseException, IOException {
        portfolio.addStock("SHOP.TO", 500, 10);
        portfolio.addOptions("SHOP.TO", 50, "2022-08-20", 10);
        assertTrue(portfolio.totalValue() > 5500);
    }

    @Test
    void checkSimpleReturn() throws ParseException, IOException {
        portfolio.addStock("SHOP.TO", 700, 10);
        portfolio.addOptions("SHOP.TO", 50, "2022-08-20", 10);
        assertTrue(portfolio.simpleReturn() > 0);
    }

    @Test
    void checkValidCheck() throws ParseException {
        portfolio.addStock("SHOP.TO", 500, 10);
        portfolio.addOptions("SHOP.TO", 50, "2022-08-20", 10);
        portfolio.getSecurity(0).update(-20, 500);
    }


}
