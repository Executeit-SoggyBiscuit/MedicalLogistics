package distMatrix;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Distance {

    String text;
    String value;

    public void setText(String text){
        this.text = text;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getText(){
        return text;
    }

    public String getValue(){
        return value;
    }
}
