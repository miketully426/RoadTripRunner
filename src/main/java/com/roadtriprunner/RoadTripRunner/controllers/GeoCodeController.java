package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.RoadTripRunnerApplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.catalina.util.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class GeoCodeController {

//    @Value(gmapsApiKey)
//    private String gmapsApiKey;

    String url = "http://localhost:8080/planATrip";

//    @RequestMapping(path = "/planATrip/geocode", method = RequestMethod.GET)
//    public String getGeocode(@RequestParam String url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
////        String encodedAddress = URLEncoder.DEFAULT.encode(address, StandardCharsets.UTF_8);
//        Request request = new Request.Builder()
//                .url("'https://maps.googleapis.com/maps/api/js?key=' +gmapsApiKey+ &libraries=places&callback=initMap")
//                .get()
//                .addHeader("gmapsApiCall", "http://localhost:8080/planATrip") //needs more
////                .addHeader("gmapsApiKey", gmapsApiKey)
//                .build();
//
//        ResponseBody responseBody = client.newCall(request).execute().body();
//        System.out.println(responseBody);
//        return responseBody.string();
//    }

    String gmapRequestURL = "https://maps.googleapis.com/maps/api/js?key=" + "API_KEY" + "&libraries=places&callback=initMap";

//    @RequestMapping(path = "/planATrip", method = RequestMethod.GET)
    public void sendHttpRequest() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(gmapRequestURL)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(GeoCodeController::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);
            String name = album.getString("name");
            System.out.println(name);
        }
        return null;
    }
}

