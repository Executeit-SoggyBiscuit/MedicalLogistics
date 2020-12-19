package distMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistResults {
    @JsonProperty("destination_addresses")
    List<String> destinationAddresses;
    @JsonProperty("origin_addresses")
    String originAddress;
    @JsonProperty("rows")
    List<rows> rowsList;
    String status;

    public DistResults(){

    }

    public List<String> getDestinationAddresses(){
        return destinationAddresses;
    }

    public String getOriginAddress(){
        return originAddress;
    }

    public List<rows> getRows(){
        return rowsList;
    }

    public String getStatus(){
        return status;
    }

    public void setDestinationAddresses(List<String> destination_Addresses){
        this.destinationAddresses = destination_Addresses;
    }

    public void setOriginAddress(String originAddress){
        this.originAddress = originAddress;
    }

    public void setRows(List<rows> rowsList){
        this.rowsList = rowsList;
    }

    public  void setStatus(String status){
        this.status = status;
    }
}
