package services;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class ReservationServer {

    public static void startServer(ReservationManager manager) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/reserve", new ReserveHandler(manager));

        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:8080");
    }
}