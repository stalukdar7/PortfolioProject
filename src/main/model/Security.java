package model;

import exceptions.TickerNotValid;
import org.json.JSONObject;

import java.io.IOException;

public interface Security {
    //Getters
    String getName();

    double getCurrentPrice() throws TickerNotValid;

    int getQuantity();

    double getAveragePrice();

    //Update
    void update(int quantity,double price);

    void averagePrice(int addQuantity, double addPrice);


    //Functions

    double profitOrLoss() throws IOException;

    double value() throws IOException;

    JSONObject toJson() throws IOException;
}
