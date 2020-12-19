package classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationInfo {
    private String address;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
    private String latlong;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        if(longitude!=null){
            latlong = latitude + "," + longitude;
        }
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        if(latitude!=null){
            latlong = latitude + "," + longitude;
        }
    }

    public String getAddress(){
        return address;
    }

    public String getLatitude(){
        return  latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public String getLatlong(){
        return latlong;
    }

}
