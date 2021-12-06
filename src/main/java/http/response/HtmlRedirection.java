package http.response;

public class HtmlRedirection extends Response {

    private final String location;

    public HtmlRedirection(String location) {
        this.location = location;
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 301 Redirect\r\nLocation: /quotes\r\n\r\n";
        //response += html;

        return response;
    }

}
