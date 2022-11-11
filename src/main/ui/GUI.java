package ui;


import exceptions.LogException;
import model.EventLog;
import model.Portfolio;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class GUI extends JFrame {
    private static final String JSON_STORE = "./data/portfolio.json";
    private Portfolio portfolio;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

/*
    @SuppressWarnings("checkstyle:MethodLength")
    public GUI() throws LogException {



        JFrame frame;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        portfolio = new Portfolio(0);
        frame = new JFrame();

        JLabel label = new JLabel("Portfolio Options");

        JButton button3 = new JButton("Portfolio");

        JButton button4 = new JButton("Transaction Log");


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new Graph(portfolio.hashmaptoList()));
        panel.add(label);
        panel.add(new JButton(new FirstTimeSetUp()));
        panel.add(new JButton(new quickSummary()));
        panel.add(button3);
        panel.add(button4);
        panel.add(new JButton(new historicalData()));
        panel.add(new JButton(new logTodayData()));
        panel.add(new JButton(new saveFunction()));
        panel.add(new JButton(new loadFunction()));


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(closeprocedure());
        frame.setTitle("CPSC 210: Portfolio Tracker");
        frame.pack();
        frame.setVisible(true);


    }

    private int closeprocedure() throws LogException {
        LogPrinter lp;
        lp = new ScreenPrinter(GUI.this);
        ScreenPrinter screenPrinter = (ScreenPrinter) lp;
        System.out.println(lp.printLog(EventLog.getInstance()));
        return JFrame.EXIT_ON_CLOSE;
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class quickSummary extends AbstractAction {
        JFrame frame;

        quickSummary() {
            super("Quick Summary");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel output;
            output = new JLabel(portfolio.printAllSecuritiesNumberedValueQuantity());
            panel.add(output);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class saveFunction extends AbstractAction {
        JFrame frame;

        saveFunction() {
            super("Save Portfolio");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel output = new JLabel();
            try {
                jsonWriter.open();
                jsonWriter.write(portfolio);
                jsonWriter.close();
                output = new JLabel("Saved " + "Portfolio" + " to " + JSON_STORE);

            } catch (FileNotFoundException e) {
                output = new JLabel("Unable to write to file: " + JSON_STORE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            panel.add(output);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class loadFunction extends AbstractAction {
        JFrame frame;

        loadFunction() {
            super("Load Portfolio");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel output = new JLabel();
            try {
                portfolio = jsonReader.read();
                output = new JLabel("Loaded " + "Portfolio" + " from " + JSON_STORE);
            } catch (IOException e) {
                output = new JLabel("Unable to read from file: " + JSON_STORE);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            panel.add(output);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class logTodayData extends AbstractAction {
        JFrame frame;

        logTodayData() {
            super("Log Today Data");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel output;
            try {
                portfolio.historicalDataLogging();
            } catch (IOException e) {
                e.printStackTrace();
            }
            output = new JLabel("Logged" + portfolio.hashmaptoString() + "Successfully");
            panel.add(output);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class historicalData extends AbstractAction {
        JFrame frame;

        historicalData() {
            super("Historical Data");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel output;
            output = new JLabel(portfolio.hashmaptoString());
            panel.add(output);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }
    }

    public class FirstTimeSetUp extends AbstractAction {
        JTextField cash;

        FirstTimeSetUp() {
            super("First Time Set Up");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
            panel.setLayout(new GridLayout(4, 2));
            panel.add(new JLabel("How Much Cash Have You Invested"));
            cash = new JTextField();
            panel.add(cash);
            panel.add(new JButton(new FirstTimeSetUp2()));
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);


        }


        public class FirstTimeSetUp2 extends AbstractAction {


            FirstTimeSetUp2() {
                super("Done");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                int invested = Integer.parseInt(cash.getText());
                portfolio.setCashInvested(invested);
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                panel.setLayout(new GridLayout(4, 2));
                panel.add(new JLabel("What did you buy?"));

                panel.add(new JButton(new optionSetUp()));
                panel.add(new JButton(new stockSetUp()));
                frame.add(panel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CPSC 210: Portfolio Tracker");
                frame.pack();
                frame.setVisible(true);

            }
        }
    }

    @SuppressWarnings("checkstyle:TypeName")
    public class optionSetUp extends AbstractAction {
        JTextField quantity;
        JTextField ticker;
        JTextField averagePrice;
        JTextField expiry;
        JTextField value;

        optionSetUp() {
            super("Option");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            ticker = new JTextField();
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
            panel.setLayout(new GridLayout(4, 2));
            panel.add(new JLabel("What's the ticker of the stock? (refer to Yahoo Finance)"));
            panel.add(ticker);
            panel.add(new JLabel("What's the current value?"));
            value = new JTextField();
            panel.add(new JLabel("How much quantity did you buy?"));
            quantity = new JTextField();
            panel.add(new JLabel("What was the average price?"));
            averagePrice = new JTextField();
            panel.add(new JLabel("When is the expiry?"));
            expiry = new JTextField();
            panel.add(value);
            panel.add(quantity);
            panel.add(averagePrice);
            panel.add(expiry);


            panel.add(new JLabel("Add More Stock and Option"));
            panel.add(new JButton(new mainPage2()));

            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }

        public class mainPage2 extends AbstractAction {
            JFrame frame;

            mainPage2() {
                super("Done");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    portfolio.addOptions(ticker.getText(), Integer.parseInt(value.getText()),
                            expiry.getText(), Integer.parseInt(quantity.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                frame = new JFrame();

                JLabel label = new JLabel("Portfolio Options");


                JButton button3 = new JButton("Portfolio");

                JButton button4 = new JButton("Transaction Log");


                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                panel.setLayout(new GridLayout(4, 2));
                panel.add(new Graph(portfolio.hashmaptoList()));
                panel.add(label);
                panel.add(new JButton(new FirstTimeSetUp()));
                panel.add(new JButton(new quickSummary()));
                panel.add(button3);
                panel.add(button4);
                panel.add(new JButton(new historicalData()));
                panel.add(new JButton(new logTodayData()));
                panel.add(new JButton(new saveFunction()));
                panel.add(new JButton(new loadFunction()));


                frame.add(panel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CPSC 210: Portfolio Tracker");
                frame.pack();
                frame.setVisible(true);
            }
        }


    }


    public class stockSetUp extends AbstractAction {
        JTextField quantity;
        JTextField ticker;
        JTextField averagePrice;

        stockSetUp() {
            super("Stock");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            ticker = new JTextField();
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
            panel.setLayout(new GridLayout(4, 2));
            panel.add(new JLabel("What's the ticker of the stock? (refer to Yahoo Finance)"));
            panel.add(ticker);
            panel.add(new JLabel("How much quantity did you buy?"));
            quantity = new JTextField();
            panel.add(quantity);
            panel.add(new JLabel("What was the average price?"));
            averagePrice = new JTextField();
            panel.add(averagePrice);


            panel.add(new JLabel("Add More Stock and Option"));
            panel.add(new JButton(new mainPage()));

            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CPSC 210: Portfolio Tracker");
            frame.pack();
            frame.setVisible(true);
        }

        public class mainPage extends AbstractAction {
            JFrame frame;

            mainPage() {
                super("Done");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                portfolio.addStock(ticker.getText(), Integer.parseInt(averagePrice.getText()),
                        Integer.parseInt(quantity.getText()));
                frame = new JFrame();

                JLabel label = new JLabel("Portfolio Options");


                JButton button3 = new JButton("Portfolio");

                JButton button4 = new JButton("Transaction Log");


                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                panel.setLayout(new GridLayout(4, 2));
                panel.add(new Graph(portfolio.hashmaptoList()));
                panel.add(label);
                panel.add(new JButton(new FirstTimeSetUp()));
                panel.add(new JButton(new quickSummary()));
                panel.add(button3);
                panel.add(button4);
                panel.add(new JButton(new historicalData()));
                panel.add(new JButton(new logTodayData()));
                panel.add(new JButton(new saveFunction()));
                panel.add(new JButton(new loadFunction()));


                frame.add(panel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CPSC 210: Portfolio Tracker");
                frame.pack();
                frame.setVisible(true);
            }
        }


    }
    */


}



