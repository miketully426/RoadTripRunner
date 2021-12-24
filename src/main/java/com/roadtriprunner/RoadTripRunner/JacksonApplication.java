package com.roadtriprunner.RoadTripRunner;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadtriprunner.RoadTripRunner.models.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class JacksonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JacksonApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException {

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert it to location object
        Location location = objectMapper.readValue(new File("location.json"), Location.class);

        //print location details
        System.out.println(location);
    }
}


//OR!

//    //create ObjectMapper instance
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //convert json file to map
//    Map<?, ?> map = objectMapper.readValue(new FileInputStream("customer.json"), Map.class);
//
////iterate over map entries and print to console
//for (Map.Entry<?, ?> entry : map.entrySet()) {
//        System.out.println(entry.getKey() + "=" + entry.getValue());
//        }