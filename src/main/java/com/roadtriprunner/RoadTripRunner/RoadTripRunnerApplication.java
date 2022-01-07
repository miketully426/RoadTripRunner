package com.roadtriprunner.RoadTripRunner;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

/*
https://www.youtube.com/watch?v=qzRKa8I36Ww
 */

@SpringBootApplication
public class RoadTripRunnerApplication {

	/*method 1: uses java.net.HttpURLConnection
	private static HttpURLConnection connection;
*/
	public static void main(String[] args) {
		SpringApplication.run(RoadTripRunnerApplication.class, args);


//method 2: uses java.net.http.HttpClient and allows asynch calls
// create client
// build request using JSON placeholder URL
//send request via client asynch -- tell handler we want reponse as string
//once asynch is done and we have response, apply method to previous result
//:: means use body method from HTTPResponse class on the previous result
//then use that body and print it out
//then return all the results of the completable future and display the results
//will not display anything without .join();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.thenApply(RoadTripRunnerApplication::parse)
				.join();

	}

	public static String parse(String responseBody) {
		JSONArray albums = new JSONArray(responseBody);
		for (int i = 0; i < albums.length(); i++){
			JSONObject album = albums.getJSONObject(i);
			int id = album.getInt("id");
			int userId = album.getInt("userId");
			String title = album.getString("title");
			System.out.println(id + "   " + title + "   " + userId);
		}
		return null;
	}


}


/*method 1
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
			connection = (HttpURLConnection) url.openConnection();

// request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);  //5sec
			connection.setReadTimeout(5000);

//            get response from server

			int status = connection.getResponseCode(); //want status value 200
			System.out.println(status);  //print to determine if successful

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

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}

*/





