package persistence;

import model.Portfolio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Portfolio read() throws IOException, ParseException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePortfolio(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Portfolio from JSON object and returns it
    private Portfolio parsePortfolio(JSONObject jsonObject) throws ParseException {
        int cashInvested = jsonObject.getInt("Cash Invested");
        Portfolio p = new Portfolio(cashInvested);
        parseHashMap(p, jsonObject);
        addSecurities(p, jsonObject);
        return p;
    }


    private void parseHashMap(Portfolio p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Historical Data");
        for (Object json : jsonArray) {
            JSONObject nextInput = (JSONObject) json;
            addData(p, nextInput);
        }
    }

    private void addData(Portfolio p, JSONObject nextInput) {
        int value = nextInput.getInt("Day End Value");
        String date = nextInput.getString("Date");
        p.addHistoricalDataPoint(date,value);
    }

    // MODIFIES: p
    // EFFECTS: parses Security from JSON object and adds them to Portfolio
    private void addSecurities(Portfolio p, JSONObject jsonObject) throws ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("Securities");
        for (Object json : jsonArray) {
            JSONObject nextSecurity = (JSONObject) json;
            addSecurity(p, nextSecurity);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses security from JSON object and adds it to portfolio
    private void addSecurity(Portfolio p, JSONObject jsonObject) throws ParseException {
        String type;
        if (jsonObject.getString("Type").equals("Option")) {

            String optionName = jsonObject.getString("Company Name");
            int optionPrice = jsonObject.getInt("Current Value");
            String optionExpiry = jsonObject.getString("Expiry");
            int optionQuantity = jsonObject.getInt("Quantity");
            p.addOptions(optionName, optionPrice, optionExpiry, optionQuantity);

        } else {
            String stockName = jsonObject.getString("Company Name");
            int averagePrice = jsonObject.getInt("Average Price");
            int stockQuantity = jsonObject.getInt("Quantity");
            p.addStock(stockName, averagePrice, stockQuantity);
        }

    }
}
