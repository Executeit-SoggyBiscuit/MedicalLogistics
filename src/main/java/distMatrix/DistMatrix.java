package distMatrix;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import classes.LocationInfo;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import geocoder.GeocodeResult;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

public class DistMatrix {

    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyDdizTentu2YN2HaXQ6MIZj-uP0z28BgGg";

    public static String base_url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric";

    public DistResults getClient(LocationInfo origin, ArrayList<LocationInfo> destination) throws IOException,
            InterruptedException {

        //String encodedOrigin = URLEncoder.encode(origin.getLatlong(),"UTF-8");
        String encodedOrigin = origin.getLatlong();
        String requestUri = base_url + "&origins=" + encodedOrigin + "&destinations=";
        if(destination.size()<80) {
            for (int i = 0; i < destination.size(); i++) {
                //String encodedDestination = URLEncoder.encode(destination.get(i).getLatlong(), "UTF-8");
                String encodedDestination = destination.get(i).getLatlong();
                if (i != destination.size()-1) {
                    requestUri = requestUri + encodedDestination + "%7C";
                } else {
                    requestUri = requestUri + encodedDestination;
                }
            }
            requestUri = requestUri + "&key=" + GOOGLE_PLACE_API_KEY;
            System.out.println(requestUri);

        }else{
            for (int i = 0; i < 80; i++) {
                String encodedDestination = URLEncoder.encode(destination.get(i).getLatlong(), "UTF-8");
                if (i != destination.size() - 1) {
                    requestUri = requestUri + encodedDestination + "%7C";
                } else {
                    requestUri = requestUri + encodedDestination;
                }
            }
        }

        DistResults result = requestHttp(requestUri);

        return result;
    }

    private DistResults requestHttp(String requestUrl) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUrl))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue((String) geocodingResponse.body(),
                DistResults.class);
    }

}