package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) {

        Quote.quotes.add(new Quote("Nelson Mandela", "The greatest glory in living lies not in never falling, but in rising every time we fall."));
        Quote.quotes.add(new Quote("John Lenon", "Life is what happens when you're busy making other plans."));
        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket sock = ss.accept();
                new Thread(new ServerThread(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
