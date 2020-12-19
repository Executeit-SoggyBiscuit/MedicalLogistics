package distMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class elements {
    @JsonProperty("distance")
    Distance distance;
    @JsonProperty("duration")
    Duration duration;
    String status;
    public void setDistance(Distance distance){
        this.distance = distance;
    }

    public void setDuration(Duration duration){
        this.duration = duration;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Distance getDistance(){
        return distance;
    }

    public Duration getDuration(){
        return duration;
    }

    public String string(){
        return status;
    }
}
