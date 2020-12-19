package geocoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Geocoder {

    private static final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyDdizTentu2YN2HaXQ6MIZj-uP0z28BgGg";

    public GeocodeResult geocodeSync(String query) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?address=" + encodedQuery + "&key=" + API_KEY;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        GeocodeResult result = objectMapper.readValue((String) geocodingResponse.body(),
                GeocodeResult.class);
        return result;
    }
}
