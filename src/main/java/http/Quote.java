package http;

import java.util.ArrayList;

public class Quote {

    private String author;
    private String quote;
    public static ArrayList<Quote> quotes = new ArrayList<>();
    public static Quote quoteOfTheDay = new Quote("", "");

    public Quote(String author, String quote) {
        this.author = author;
        this.quote = quote;

    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }

    public static void setQuoteOfTheDay(Quote quoteOfTheDay) {
        Quote.quoteOfTheDay = quoteOfTheDay;
    }
}
