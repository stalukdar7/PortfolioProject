package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
//cron job
//Represents a portfolio which consists of list of stock and transaction activities

public class Portfolio implements Writable {

    private ArrayList<Security> portfolio;
    private HashMap<String, Integer> historicalData;
    private int cashInvested;

    public Portfolio(int cashInvested) {
        this.portfolio = new ArrayList<>();
        this.historicalData = new HashMap<>();
        this.cashInvested = cashInvested;


    }

    public void setCashInvested(int i) {
        this.cashInvested = i;

    }

    public int getCashInvested() {
        return cashInvested;

    }


    // EFFECTS: Returns a given security given index
    public Security getSecurity(int i) {
        return portfolio.get(i);
    }

    public void historicalDataLogging() throws IOException {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = currentDate.format(date);
        historicalData.put(d, totalValue());
    }

    public String printAllSecuritiesNumberedValueQuantity() {
        String output = "";
        for (int i = 0; i < portfolio.size(); i++) {
            Integer temp = i + 1;
            try {
                output += Integer.toString(i) + " " + portfolio.get(i).getName()
                        + " Total Value $" + Double.toString(portfolio.get(i).value())
                        + " quantity: " + Integer.toString(portfolio.get(i).getQuantity()) + "\n";

            } catch (IOException e) {
                System.out.println("One of the securities aren't formatted properly");
                e.printStackTrace();
            }
        }
        EventLog.getInstance().logEvent(new Event("Valuation Checked."));
        return output;
    }


    // EFFECTS: prints all security paired with a number
    public void printAllSecuritiesNumbered() {
        for (int i = 0; i < portfolio.size(); i++) {
            int temp = i + 1;
            System.out.println(temp + " " + portfolio.get(i).getName());
        }
    }


    // EFFECTS: returns total value of a portfolio
    public int totalValue() throws IOException {
        int value = 0;
        for (Security security : portfolio) {
            value += security.value();
        }
        return value;
    }

    // EFFECTS: returns simple of a portfolio
    public double simpleReturn() throws IOException {
        double value = 0;
        double cost = 0;
        double returnPercent;
        for (Security security : portfolio) {
            value += security.value();
            cost += (security.getAveragePrice() * security.getQuantity());
        }
        returnPercent = ((value / cost) - 1) * 100;
        return returnPercent;
    }


    // EFFECTS: adds a stock to a portfolio
    public void addStock(String companyName, double avg, int quantity) {
        Stock stock = new Stock(companyName, avg, quantity);
        portfolio.add(stock);
        EventLog.getInstance().logEvent(new Event("Stock Added."));
    }

    // EFFECTS: adds an option to a portfolio
    public void addOptions(String companyName, int currentValue, String expiry, int quantity) throws ParseException {
        OptionSecurity option = new OptionSecurity(companyName, currentValue, expiry, quantity);
        portfolio.add(option);
        EventLog.getInstance().logEvent(new Event("Option Added."));
    }

    public int getValue(String date) {
        return historicalData.get(date);
    }

    @Override
    public JSONObject toJson() throws IOException {
        JSONObject json = new JSONObject();
        json.put("Historical Data", hashmapToJson());
        json.put("Securities", securitiesToJson());
        json.put("Cash Invested", cashInvested);
        return json;
    }

    public int[] hashmaptoList() {
        Collection temp = historicalData.values();
        List<Integer> list = new ArrayList<>(temp);
        int[] answer = list.stream().mapToInt(i -> i).toArray();
        return answer;
    }

    public String hashmaptoString() {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : historicalData.keySet()) {
            mapAsString.append(key + "=" + historicalData.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    private JSONArray hashmapToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : historicalData.entrySet()) {
            jsonArray.put(hashmapToJson(entry.getKey(), entry.getValue()));
        }
        return jsonArray;
    }

    private JSONObject hashmapToJson(String k, Integer v) {
        JSONObject json = new JSONObject();
        json.put("Date", k);
        json.put("Day End Value", v);
        return json;
    }


    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray securitiesToJson() throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (Security s : portfolio) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    public void addHistoricalDataPoint(String date, int value) {
        historicalData.put(date, value);
    }

    ///do time weighted eventually
}
