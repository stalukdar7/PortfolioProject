package persistence;

import model.Portfolio;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {


    @Test
    void testWriterInvalidFile() {
        try {
            Portfolio p = new Portfolio(0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() throws ParseException {
        try {
            Portfolio p = new Portfolio(0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPortfolio.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPortfolio.json");
            p = reader.read();
            assertEquals(0, p.totalValue());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() throws ParseException {
        try {
            Portfolio p = new Portfolio(500);
            p.addStock("SHOP.TO", 6940, 10);
            p.addOptions("SPY", 500, "2023-05-26", 10);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPortfolio.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPortfolio.json");
            p = reader.read();
            assertTrue(p.totalValue() > 0);
            assertTrue(p.simpleReturn() < 0);
            assertEquals(500, p.getCashInvested());
            assertEquals("SHOP.TO", p.getSecurity(0).getName());
            assertEquals("SPY", p.getSecurity(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}