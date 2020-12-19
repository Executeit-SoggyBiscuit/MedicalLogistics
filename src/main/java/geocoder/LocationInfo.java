package geocoder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationInfo {
    private String address;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
}
