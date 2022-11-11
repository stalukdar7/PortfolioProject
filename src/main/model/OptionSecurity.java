

package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

//Class which contains Option which implements security and has quantity, expiry, current value and name
public class OptionSecurity implements Security, Writable {
    private final String companyName;
    private int quantity;
    private double currentPrice;
    private double averagePrice;
    private boolean expired;
    private Date formattedExpiry;
    private String expiryRaw;
    // REQUIRES: currentPrice >= 0
    //           quantity >= 0
    // EFFECTS: Constructs an option class which, takes a name, current price, expiry and
    // quantity. Also calculates average price
    public OptionSecurity(String companyName, double currentValue, String expiry, int quantity) throws ParseException {
        this.companyName = companyName;
        this.currentPrice = currentValue;
        this.quantity = quantity;
        this.expiryRaw = expiry;
        this.formattedExpiry = new SimpleDateFormat("yyyy-MM-dd").parse(expiry);
        averagePrice();

    }

    @Override
    // EFFECTS: returns Company Name
    public String getName() {
        return companyName;
    }

    @Override
    // EFFECTS: returns Current Price
    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    // EFFECTS: returns Quantity of stock held
    public int getQuantity() {
        return quantity;
    }

    @Override
    // EFFECTS: returns average price
    public double getAveragePrice() {
        return averagePrice;
    }

    // EFFECTS: sets a new current price
    public void setCurrentPrice(double prc) {
        currentPrice = prc;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Updates for any purchase sell(negative),buy (positive);updates average price too
    public void update(int quantity, double price) {
        if (quantity > 0) {
            averagePrice(quantity, price);
        }
        this.quantity += quantity;
    }

    // EFFECTS: Checks if the given security is valid
    public Boolean valid() {
        return (getQuantity() > 0) && (getCurrentPrice() > 0) && (getAveragePrice() > 0);
    }

    @Override
    // REQUIRES: positive current & average price
    // EFFECTS: Returns a dollar amount of return
    public double profitOrLoss() {
        return (getQuantity() * (getCurrentPrice() - getAveragePrice()));
    }

    @Override
    // EFFECTS: Return a value
    public double value() {
        return (getQuantity() * getCurrentPrice());
    }

    // MODIFIES: This
    // EFFECTS: Returns an averagePrice based on initial calculations
    public void averagePrice() {
        averagePrice = currentPrice;
    }

    // MODIFIES: This
    // EFFECTS: Returns an average price accounting for the prior average price as well as new transactions
    public void averagePrice(int addQuantity, double addPrice) {
        averagePrice = ((((float) addQuantity * addPrice)
                + ((float) quantity * getAveragePrice())) / (addQuantity + quantity));
    }

    // EFFECTS: Checks if the expiry date has elapsed or not
    public boolean checkIfExpired() throws ParseException {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = currentDate.format(date);
        Date currentDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        expired = formattedExpiry.before(currentDateFormatted);
        return expired;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Type", "Option");
        json.put("Company Name", companyName);
        json.put("Current Value", value());
        json.put("Expiry", expiryRaw);
        json.put("Quantity", quantity);
        return json;
    }
}
