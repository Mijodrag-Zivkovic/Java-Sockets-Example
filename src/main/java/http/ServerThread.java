package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket client;
    private Socket servis;
    private BufferedReader in, servisIn;
    private PrintWriter out, servisOut;
    private Gson gson;

    public ServerThread(Socket sock) {
        this.client = sock;
        gson = new Gson();
        try {
            servis = new Socket("localhost", 8114);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            servisIn = new BufferedReader(new InputStreamReader(servis.getInputStream()));
            servisOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(servis.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            // uzimamo samo prvu liniju zahteva, iz koje dobijamo HTTP method i putanju
            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();
            int contentLength = 0;

            System.out.println("\nHTTP ZAHTEV KLIJENTA:\n");
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
                if (requestLine.contains("Content-Length:")) {
                    requestLine = requestLine.replace("Content-Length: ", "");
                    contentLength = Integer.parseInt(requestLine);
                }
            } while (!requestLine.trim().equals(""));

            if (method.equals(HttpMethod.POST.toString())) {
                // TODO: Ako je request method POST, procitaj telo zahteva (parametre)
                char[] input = new char[contentLength];
                in.read(input);

                System.out.println(input);
                System.out.println(contentLength);
                String s = String.valueOf(input);

                s = s.replace('+', ' ');
                //s = URLDecoder.decode(s,StandardCharsets.UTF_8.name());
                //s.getBytes(StandardCharsets.UTF_8).length;

                System.out.println(s);

                Citat.citati.add(new Citat(s.substring(s.indexOf("=") + 1, s.indexOf("&")), s.substring(s.lastIndexOf("=") + 1)));
            }
            //String zdravo = "zdravo";
            //servisOut.println(zdravo);
//            for(Citat c : Citat.citati)
//            {
//                servisOut.println(gson.toJson(c));
//                System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//            }

            String servisRequest =
                    "GET /qotd HTTP/1.1\r\n" +
                            "Host: localhost:8114\r\n" +
                            "Connection: keep-alive\r\n";
            servisOut.println(servisRequest);

            requestLine = servisIn.readLine();
            System.out.println("\nOdgovor Servisa:\n");
            do {
                System.out.println(requestLine);
                requestLine = servisIn.readLine();
            } while (!requestLine.trim().equals(""));
            System.out.println("");
            requestLine = servisIn.readLine();
            Citat c = gson.fromJson(requestLine, Citat.class);
            c.setCitatDana(c);
            System.out.println(Citat.citatDana.getAutor() + " " + Citat.citatDana.getCitat());
            System.out.println(requestLine);
            System.out.println("kraj");

            Request request = new Request(HttpMethod.valueOf(method), path);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP odgovor:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
