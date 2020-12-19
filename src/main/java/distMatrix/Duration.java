package distMatrix;

public class Duration {
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
