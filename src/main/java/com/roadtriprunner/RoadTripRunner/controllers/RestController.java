//keeping this code for future although it's not needed for this story

/*
package com.roadtriprunner.RoadTripRunner.controllers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Value("${gmapsApiKey}")
    private String gmapsApiKey;
    String address = "Owensboro, KY";
    @GetMapping
    public void callAPIForLatLng() throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(gmapsApiKey)
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        context.shutdown();
    }
    public static String parse(String responseBody) {
        JSONArray locations = new JSONArray(responseBody);
        for (int i = 0; i < locations.length(); i++){
            JSONObject location = locations.getJSONObject(i);
            String name = location.getString("name");
            System.out.println(name);
        }
        return null;
    }
}
*/