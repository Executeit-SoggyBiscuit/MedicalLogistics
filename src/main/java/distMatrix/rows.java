package distMatrix;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class rows {
    List<elements> elements;

    public void setElements(ArrayList<elements> elements){
        this.elements = elements;
    }

    public List<elements> getElements(){
        return  elements;
    }
}
