package com.roadtriprunner.RoadTripRunner.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Controller
@RequestMapping("/")
public class CreateTripController {


    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    String gmapRequestURL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=" + "API_KEY";
    private static HttpURLConnection connection;

//    method 1
    @GetMapping("/planATrip")
    public String renderPlanATripPage(Model model) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(gmapRequestURL)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out :: println)
//                .thenApply(CreateTripController::parse)
                .join();
        return "planATrip";
    }

/*    @GetMapping("/planATrip")
    public String renderPlanATripPageWithAPICall(Model model) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

		try {
            URL url = new URL(gmapRequestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
            parse(responseContent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return "planATrip";
    }
*/

//    The parse method is not working because the http request returns too much data and it's not all JSON Array.
  /*
    public static String parse(String responseBody) {
        JSONArray places = new JSONArray(responseBody);
        for (int i = 0; i < places.length(); i++){
            JSONObject place = places.getJSONObject(i);
            String name = place.getString("name");
            System.out.println(name);
        }
        return null;
    }
*/
}
