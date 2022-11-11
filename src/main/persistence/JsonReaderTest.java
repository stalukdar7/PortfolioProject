package persistence;

import model.Portfolio;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() throws ParseException {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Portfolio p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPortfolio() throws ParseException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPortfolio.json");
        try {
            Portfolio p = reader.read();
            assertEquals(0, p.totalValue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPortfolio() throws ParseException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPortfolio.json");
        try {
            Portfolio p = reader.read();
            assertTrue(p.totalValue() > 25000);
            assertTrue(p.simpleReturn() > 0);
            assertEquals(p.getCashInvested(),10000);
            assertEquals("SHOP.TO", p.getSecurity(0).getName());
            assertEquals("AAPL", p.getSecurity(1).getName());
            assertEquals("SPY", p.getSecurity(2).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}