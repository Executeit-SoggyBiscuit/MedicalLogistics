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

    public GeocodeResult geocodeSync(String query,String location) throws IOException, InterruptedException {

        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String encodedLocation = URLEncoder.encode(location,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?address=" + encodedQuery + "&region=" + encodedLocation + "&key=" + API_KEY;

        GeocodeResult result = requestHttp(requestUri);
        if(result.getStatus() == "OK"){

        }else if(result.getStatus() == "ZERO_RESULTS"){

        }else if(result.getStatus() == "OVER_DAILY_LIMIT"){

        }else if(result.getStatus() == "REQUEST_DENIED"){

        }else if(result.getStatus() == "INVALID_REQUEST"){

        }else if(result.getStatus() == "UNKNOWN_ERROR"){

        }else{

        }
        return result;
    }

    public GeocodeResult geocodeSync(String query) throws IOException, InterruptedException {

        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?address=" + encodedQuery + "&key=" + API_KEY;
        System.out.println(requestUri);
        GeocodeResult result = requestHttp(requestUri);

        if(result.getStatus() == "OK"){

        }else if(result.getStatus() == "ZERO_RESULTS"){

        }else if(result.getStatus() == "OVER_DAILY_LIMIT"){

        }else if(result.getStatus() == "REQUEST_DENIED"){

        }else if(result.getStatus() == "INVALID_REQUEST"){

        }else if(result.getStatus() == "UNKNOWN_ERROR"){

        }else{

        }
        return result;
    }

    public GeocodeResult reverseGeocode(String latitude,String longitude) throws IOException, InterruptedException {

        String encodedLatitude = URLEncoder.encode(latitude,"UTF-8");
        String encodedLongitude = URLEncoder.encode(longitude,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?latlng=" + encodedLatitude + "," + encodedLongitude + "&key=" + API_KEY;

        GeocodeResult result = requestHttp(requestUri);

        if(result.getStatus() == "OK"){

        }else if(result.getStatus() == "ZERO_RESULTS"){

        }else if(result.getStatus() == "OVER_DAILY_LIMIT"){

        }else if(result.getStatus() == "REQUEST_DENIED"){

        }else if(result.getStatus() == "INVALID_REQUEST"){

        }else if(result.getStatus() == "UNKNOWN_ERROR"){

        }else{

        }
        return result;
    }

    private GeocodeResult requestHttp(String requestUrl) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUrl))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue((String) geocodingResponse.body(),
                GeocodeResult.class);
    }
}
