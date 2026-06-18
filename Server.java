import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class Server {
    private static List<String> expenses = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/addExpense", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            if ("POST".equals(exchange.getRequestMethod())) {
                BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                expenses.add(br.readLine());
                exchange.sendResponseHeaders(200, 0);
            }
            exchange.close();
        });

        server.createContext("/getExpenses", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            String response = String.join(" | ", expenses);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/deleteExpense", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            int index = Integer.parseInt(br.readLine());
            if(index >= 0 && index < expenses.size()) expenses.remove(index);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        });

        server.start();
        System.out.println("Server started on 8080...");
    }
}