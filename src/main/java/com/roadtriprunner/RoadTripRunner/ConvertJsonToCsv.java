package com.roadtriprunner.RoadTripRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roadtriprunner.RoadTripRunner.controllers.CreateTripController;
import com.roadtriprunner.RoadTripRunner.models.NationalParkLocation;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ConvertJsonToCsv {

//    public static void convertJsonToCSV(String body) throws IOException {
//        JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/nationalParks.json"));
//        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
//        JsonNode firstObject = jsonTree.elements().next();
//        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//
//        CsvMapper csvMapper = new CsvMapper();
//        csvMapper.writerFor(JsonNode.class)
//                .with(csvSchema)
//                .writeValue(new File("src/main/resources/nationalParks.csv"), jsonTree);
//    }


    @Value("${natParkApiKey}")
    private String natParkApiKey;


    @SneakyThrows
    public String getNationalParks() {
        HttpClient client = HttpClient.newBuilder().build();
        String nationalParkUrl = "https://developer.nps.gov/api/v1/parks?limit=600&api_key=" + natParkApiKey;

        HttpRequest nationalParkRequest = HttpRequest.newBuilder()
                .uri(URI.create(nationalParkUrl))
                .timeout(Duration.ofMinutes(2))
                .build();
        HttpResponse<String> response = client.send(nationalParkRequest, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        ConvertJsonToCsv.convertJsonToCSV(gson.toJson(response.body()));
        String jsonString = gson.toJson(response.body());
        System.out.println(jsonString);
        return jsonString;
    }


    private static List convertJsonStringToObjects(String jsonString){
        List nationalParks = null;

        try {
            nationalParks = new ObjectMapper().readValue(jsonString, new TypeReference<>() {});
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nationalParks;
    }


    private static void writeListObjectsToCsvFile(List nationalParks, String pathFile) {
        final String[] CSV_HEADER = {"id", "url", "fullName", "parkCode", "description", "latitude", "longitude",
                "latLong", "activities", "topics", "states", "contacts", "entranceFees", "entrancePasses",
                "fees", "directionsInfo", "directionsUrl", "operatingHours", "addresses", "images",
                "weatherInfo", "name", "designation"};

        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;

        try {
            fileWriter = new FileWriter(pathFile);
            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(CSV_HEADER));

            for (NationalParkLocation nationalPark : nationalParks) {
                List data = Arrays.asList(
                        nationalPark.getId());

                csvPrinter.printRecord(data);
            }
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvPrinter.close();
            } catch (IOException e) {
                System.out.println("Flushing/closing error!");
                e.printStackTrace();
            }
        }
    }


    String jsonString = getNationalParks();

    List nationalParks = convertJsonStringToObjects(jsonString);

    writeListObjectsToCsvFile(nationalParks, "nationalParks.csv");
}

