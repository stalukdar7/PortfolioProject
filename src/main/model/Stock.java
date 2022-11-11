package model;

import exceptions.TickerNotValid;
import org.json.JSONObject;
import persistence.Writable;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

////class which contains stocks, which has price, quantity and name
public class Stock implements Security, Writable {
    protected String companyName;
    protected int quantity;
    protected double averagePrice;
    protected yahoofinance.Stock wrapperStock;
    private boolean refresh = true;

    // REQUIRES: currentPrice >= 0
    //           quantity >= 0
    // EFFECTS: Constructs a stock class which, takes a name, current price and quantity. Also calculates average price
    public Stock(String companyName, double avgPrice, int quantity) {
        wrapperStock = new yahoofinance.Stock(companyName);
        this.companyName = companyName;
        this.quantity = quantity;
        this.averagePrice = avgPrice;
    }


    // REQUIRES: positive current, quantity & average price
    // EFFECTS: Returns a dollar amount of return
    public double profitOrLoss() throws IOException {
        return (quantity * (wrapperStock.getQuote(refresh).getAsk().doubleValue() - averagePrice));
    }


    // EFFECTS: Return a value
    public double value() throws IOException {
        return (quantity * wrapperStock.getQuote(refresh).getAsk().doubleValue());
    }


    // MODIFIES: this
    // EFFECTS: Updates for any purchase sell(negative),buy (positive);updates average price too
    public void update(int quantity, double price) {
        if (quantity > 0) {
            averagePrice(quantity, price);
        }
        this.quantity += quantity;
    }


    // MODIFIES: This
    // EFFECTS: Returns an average price accounting for the prior average price as well as new transactions
    public void averagePrice(int addQuantity, double addPrice) {
        averagePrice = (((addQuantity * addPrice) + (quantity * averagePrice)) / (addQuantity + quantity));
    }

    //Getters
    // EFFECTS: returns Company Name
    public String getName() {
        return companyName;
    }


    // EFFECTS: returns Current Price
    public double getCurrentPrice() throws TickerNotValid {
        StockQuote q = null;
        try {
            if (wrapperStock.isValid()) {
                q = wrapperStock.getQuote(refresh);
            } else {
                throw new TickerNotValid();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BigDecimal d = q.getAsk();
        return d.doubleValue();
    }


    public Boolean valid() {
        return (quantity > 0) && (averagePrice > 0);
    }


    // EFFECTS: returns Quantity of stock held
    public int getQuantity() {
        return quantity;
    }


    // EFFECTS: returns average price
    public double getAveragePrice() {
        return averagePrice;
    }


    @Override
    public JSONObject toJson() throws IOException {
        JSONObject json = new JSONObject();
        json.put("Type", "Stock");
        json.put("Company Name", companyName);
        json.put("Average Price", averagePrice);
        json.put("Quantity", quantity);
        return json;
    }

}
