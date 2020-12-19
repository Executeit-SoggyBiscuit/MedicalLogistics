package distMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class elements {
    @JsonProperty("distance")
    List<distance> distance;
    @JsonProperty("duration")
    List<duration> duration;
    String status;
    public void setDistance(List<distance> distance){
        this.distance = distance;
    }

    public void setDuration(List<duration> duration){
        this.duration = duration;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public List<distance> getDistance(){
        return distance;
    }

    public List<duration> getDuration(){
        return duration;
    }

    public String string(){
        return status;
    }
}
