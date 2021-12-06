package app;

import http.Quote;
import http.Request;
import http.response.HtmlRedirection;
import http.response.HtmlResponse;
import http.response.Response;

public class QuotesController extends Controller {

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/apply\">" +
                "<label>Author: </label><input name=\"autor\" type=\"text\"><br><br>" +

                "<label>Quote: </label><input name=\"citat\" type=\"text\"><br><br>" +
                "<button>Submit</button>" +
                "</form>" +
                "<h2>Quote of the day:</h2><br>" +
                    Quote.quoteOfTheDay.getAuthor() + ": " + Quote.quoteOfTheDay.getQuote() +
                "<h2>All quotes:</h2><br>";
        for (Quote c : Quote.quotes) {
            String s = "" + c.getAuthor() + ": " + c.getQuote() + "<br>";
            htmlBody += s;
        }
        String content = "<html><head><title>Odgovor servera</title></head>\n";
        content += "<body>" + htmlBody + "</body></html>";

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {
        // TODO: obradi POST zahtev
        //
        //System.out.println("sacuvan");


        return new HtmlRedirection("/quotes");
    }
}
