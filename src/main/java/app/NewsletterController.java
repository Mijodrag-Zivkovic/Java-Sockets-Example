package app;

import http.Citat;
import http.Request;
import http.response.HtmlRedirection;
import http.response.HtmlResponse;
import http.response.Response;

public class NewsletterController extends Controller {

    public NewsletterController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/apply\">" +
                "<label>Autor: </label><input name=\"autor\" type=\"text\"><br><br>" +

                "<label>Citat: </label><input name=\"citat\" type=\"text\"><br><br>" +
                "<button>Submit</button>" +
                "</form>" +
                "<h2>Citat dana:</h2><br>" +
                Citat.citatDana.getAutor() + ": " + Citat.citatDana.getCitat() +
                "<h2>Svi citati:</h2><br>";
        for (Citat c : Citat.citati) {
            String s = "" + c.getAutor() + ": " + c.getCitat() + "<br>";
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


        return new HtmlRedirection("/newsletter");
    }
}
