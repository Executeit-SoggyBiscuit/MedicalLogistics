package distMatrix;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class distance {
    String text;
    Double value;

    public void setText(String text){
        this.text = text;
    }

    public void setValue(String value){
        this.value = Double.parseDouble(value);
    }

    public String getText(){
        return text;
    }

    public Double getValue(){
        return value;
    }
}
