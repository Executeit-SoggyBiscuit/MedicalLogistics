package distMatrix;


import classes.DistanceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import geocoder.GeocodeResult;
import classes.LocationInfo;

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

        String encodedOrigin = URLEncoder.encode(origin.getLatlong(),"UTF-8");
        String requestUri = base_url + "&origin=" + encodedOrigin + "&destination=";
        if(destination.size()<80) {
            for (int i = 0; i < destination.size(); i++) {
                String encodedDestination = URLEncoder.encode(destination.get(i).getLatlong(), "UTF-8");
                if (i != destination.size() - 1) {
                    requestUri = requestUri + encodedDestination + "|";
                } else {
                    requestUri = requestUri + encodedDestination;
                }
            }
            requestUri = requestUri + "&key=" + GOOGLE_PLACE_API_KEY;
            DistResults result = requestHttp(requestUri);

        }else{
            for (int i = 0; i < 80; i++) {
                String encodedDestination = URLEncoder.encode(destination.get(i).getLatlong(), "UTF-8");
                if (i != destination.size() - 1) {
                    requestUri = requestUri + encodedDestination + "|";
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