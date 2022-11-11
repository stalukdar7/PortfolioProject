package ui;

import model.Portfolio;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

//Adapted from UBC Computer Science 210 Teller Application
public class ConsoleApplication {
    private static final String JSON_STORE = "./data/portfolio.json";
    private Portfolio portfolio;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Console application
    public ConsoleApplication() throws ParseException, IOException {
        runConsole();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runConsole() throws ParseException, IOException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye and Thank You!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws ParseException, IOException {
        if (command.equals("p")) {
            portfolioSummary();
        } else if (command.equals("u")) {
            doUpdate();
        } else if (command.equals("t")) {
            doTransaction();
        } else if (command.equals("r")) {
            System.out.println(portfolio.simpleReturn());
        } else if (command.equals("s")) {
            setup1();
        } else if (command.equals("h")) {
            portfolio.historicalDataLogging();
        } else if (command.equals("sa")) {
            savePortfolio();
        } else if (command.equals("l")) {
            loadPortfolio();

        } else if (command.equals("v")) {
            getDateValue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void getDateValue() {
        System.out.println("Which date would you like value for? Format ex. 2022-03-26");
        String selection = input.next();
        System.out.println(portfolio.getValue(selection));
    }

    private void setup1() throws ParseException, IOException {
        System.out.println("\tHow much cash did you invest?");
        int selection = Integer.parseInt(input.next());
        portfolio.setCashInvested(selection);
        setup();

    }

    private void loadPortfolio() throws ParseException {
        try {
            portfolio = jsonReader.read();
            System.out.println("Loaded " + "Portfolio" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void savePortfolio() {
        try {
            jsonWriter.open();
            jsonWriter.write(portfolio);
            jsonWriter.close();
            System.out.println("Saved " + "Portfolio" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        portfolio = new Portfolio(0);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> set up portfolio");
        System.out.println("\tp -> portfolio and summary");
        System.out.println("\tu -> update prices");
        System.out.println("\tt -> transaction");
        System.out.println("\tr -> percentage return");
        System.out.println("\th -> log historical data");
        System.out.println("\tv -> value of selected date");
        System.out.println("\tsa -> save portfolio to file");
        System.out.println("\tl -> load portfolio from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: summarizes account values
    private void portfolioSummary() {
        portfolio.printAllSecuritiesNumberedValueQuantity();
    }

    // MODIFIES: this
    // EFFECTS: conducts an update transaction
    private void doUpdate() throws ParseException, IOException {
        System.out.println("\t Select which security a transaction has occurred with");
        portfolio.printAllSecuritiesNumbered();
        int selection = Integer.parseInt(input.next()) - 1;
        System.out.println("\tWhat is the current price?");
        int price = Integer.parseInt(input.next());
        //  portfolio.getSecurity(selection).setCurrentPrice(price);
        returnCode();
    }

    // MODIFIES: this
    // EFFECTS: conducts a transaction
    private void doTransaction() throws ParseException, IOException {
        System.out.println("\t Select which security a transaction has occurred with");
        portfolio.printAllSecuritiesNumbered();
        int selection = Integer.parseInt(input.next()) - 1;
        System.out.println("\tHow many shares did you buy/sell? (please denote buy with + and sell with -)");
        int quantity = Integer.parseInt(input.next());
        System.out.println("\tAt what price did you buy/sell");
        int price = Integer.parseInt(input.next());
        portfolio.getSecurity(selection).averagePrice(quantity, price);
        returnCode();
    }

    // MODIFIES: this
    // EFFECTS: set up a portfolio
    private void setup() throws ParseException, IOException {
        System.out.println("\tWhat do you want to add?");
        System.out.println("\ts -> Stock");
        System.out.println("\to -> Option");
        String selection = input.next();
        if (selection.equals("s")) {
            stockSetup();
        } else if (selection.equals("o")) {
            optionSetup();
        }
    }

    private void stockSetup() throws ParseException, IOException {
        System.out.println("\tWhat's the name of the company?");
        String companyName = input.next();
        System.out.println("\tWhat's the current price of the company?");
        int currentPrice = Integer.parseInt(input.next());
        System.out.println("\tHow much did you buy?");
        int quantity = Integer.parseInt(input.next());
        portfolio.addStock(companyName, currentPrice, quantity);
        System.out.println("\tAre you done? (s) if more or (r) to return");
        String selection = input.next();
        if (selection.equals("s")) {
            stockSetup();
        } else if (selection.equals("r")) {
            returnCode();
        }
    }

    private void optionSetup() throws ParseException, IOException {
        System.out.println("\tWhat's the name of the company?");
        String companyName = input.next();
        System.out.println("\tWhat's the current price of the company?");
        int currentPrice = Integer.parseInt(input.next());
        System.out.println("\tHow much did you buy?");
        int quantity = Integer.parseInt(input.next());
        System.out.println("\tDate of Expiry (example format: 2022-08-20)");
        String date = input.next();
        portfolio.addOptions(companyName, currentPrice, date, quantity);
        System.out.println("\tAre you done? (o) if more or (r) to return");
        String selection = input.next();
        if (selection.equals("o")) {
            optionSetup();
        } else if (selection.equals("r")) {
            returnCode();
        }
    }


    public void returnCode() throws ParseException, IOException {
        displayMenu();
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        processCommand(command);
    }
}
