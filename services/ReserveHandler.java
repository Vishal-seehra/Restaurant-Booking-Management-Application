package services;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReserveHandler implements HttpHandler {

    private ReservationManager reservationManager;

    public ReserveHandler(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

            BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String formData = sb.toString();

            Map<String, String> params = parseFormData(formData);

            String firstName = params.getOrDefault("firstName", "");
            String lastName = params.getOrDefault("lastName", "");
            String contact = params.getOrDefault("contact", "");
            String email = params.getOrDefault("email", "");
            String date = params.getOrDefault("date", "");
            String time = params.getOrDefault("time", "");
            int guests = 0;
            try {
                guests = Integer.parseInt(params.getOrDefault("guests", "1"));
            } catch (NumberFormatException e) {
                guests = 1;
            }
            String notes = params.getOrDefault("notes", "");

            boolean success = reservationManager.reserveTable(
                    firstName, lastName, contact, email, date, time, guests, notes
            );

            String response = success ? "Reservation Successful" : "Reservation Failed";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<>();
        if (formData == null || formData.isEmpty()) return map;

        String[] pairs = formData.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
            map.put(key, value);
        }

        return map;
    }
}