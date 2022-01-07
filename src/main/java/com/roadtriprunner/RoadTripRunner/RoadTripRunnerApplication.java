package com.roadtriprunner.RoadTripRunner;


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

//		method 2: uses java.net.http.HttpClient and allows asynch calls


		HttpClient client = HttpClient.newHttpClient();  // create client
		// build request using JSON placeholder URL
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();

//send request via client asynch -- tell handler we want reponse as string
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//				once asynch is done and we have response, apply method to previous result
//				:: means use body method from HTTPResponse class on the previous result
				.thenApply(HttpResponse::body)
//				then use that body and print it out
				.thenAccept(System.out::println)
//				then return all the results of the completable future and display the results
//				will not display anything without .join();
				.join();


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
	}
}



