package com.roadtriprunner.RoadTripRunner.controllers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class GeoCodeController {

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    String url = "http://localhost:8080/planATrip";

    @RequestMapping(path = "/planATrip/geocode", method = RequestMethod.GET)
    public String getGeocode(@RequestParam String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
//        String encodedAddress = URLEncoder.DEFAULT.encode(address, StandardCharsets.UTF_8);
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/js?key=AIzaSyDH10rziyorFJcwhqYFPCxO61ExsmdLp20&libraries=places&callback=initMap")
                .get()
                .addHeader("gmapsApiCall", "http://localhost:8080/planATrip") //needs more
//                .addHeader("gmapsApiKey", gmapsApiKey)
                .build();

        ResponseBody responseBody = client.newCall(request).execute().body();
        System.out.println(responseBody);
        return responseBody.string();
    }
}
