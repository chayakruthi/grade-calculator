package com.example.app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

public class GradeCalculator {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", 8083), 0);

        server.createContext("/", exchange -> {

            String response = "";

            if ("POST".equals(exchange.getRequestMethod())) {

                byte[] data = exchange.getRequestBody().readAllBytes();
                String formData = new String(data, StandardCharsets.UTF_8);

                String marksText = formData.split("=")[1];
                marksText = URLDecoder.decode(marksText, StandardCharsets.UTF_8);

                int marks = Integer.parseInt(marksText);

                String grade;
                String result;

                if (marks >= 90) {
                    grade = "A";
                    result = "Excellent";
                } else if (marks >= 75) {
                    grade = "B";
                    result = "Very Good";
                } else if (marks >= 60) {
                    grade = "C";
                    result = "Good";
                } else if (marks >= 40) {
                    grade = "D";
                    result = "Pass";
                } else {
                    grade = "F";
                    result = "Fail";
                }

                response =
                        "<html><body style='font-family:Arial'>" +

                        "<h1>Grade Calculator</h1>" +

                        "<p><b>Marks:</b> " + marks + "</p>" +

                        "<p><b>Grade:</b> " + grade + "</p>" +

                        "<p><b>Result:</b> " + result + "</p>" +

                        "<br><a href='/'>Calculate Again</a>" +

                        "</body></html>";

            } else {

                response =
                        "<html><body style='font-family:Arial'>" +

                        "<h1>Grade Calculator</h1>" +

                        "<form method='POST'>" +

                        "<input type='number' name='marks' placeholder='Enter Marks' required/>" +

                        "<br><br>" +

                        "<button type='submit'>Calculate Grade</button>" +

                        "</form>" +

                        "</body></html>";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.setExecutor(null);

        server.start();

        System.out.println("Server Started!");
        System.out.println("Open Browser:");
        System.out.println("http://localhost:8083");
    }
}
