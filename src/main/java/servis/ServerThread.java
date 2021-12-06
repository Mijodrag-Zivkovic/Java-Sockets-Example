package servis;

import app.RequestHandler;
import com.google.gson.Gson;
import http.Citat;
import http.HttpMethod;
import http.Request;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private ArrayList<Citat> lista;

    public ServerThread(Socket sock) {
        this.client = sock;
        gson = new Gson();
        lista = new ArrayList<>();
        lista.add(new Citat("pera1", "citat1"));
        lista.add(new Citat("pera2", "citat2"));
        lista.add(new Citat("pera3", "citat3"));
        lista.add(new Citat("pera4", "citat4"));
        try {
            //inicijalizacija ulaznog toka
            in = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            //inicijalizacija izlaznog sistema
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            String requestLine = in.readLine();
            //System.out.println(requestLine);

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

            Random rn = new Random();
            int answer = rn.nextInt(4);
            Citat qotd = lista.get(answer);
            //System.out.println(gson.toJson(qotd));
            System.out.println("\nOdgovor servera: \n");

            String response =
                    "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\n" +
                            gson.toJson(qotd) +
                            "";

            System.out.println(response);
            //System.out.println("nesto");
            out.printf(response);


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
