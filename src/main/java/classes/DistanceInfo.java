package classes;

import java.util.ArrayList;

public class DistanceInfo {
    private ArrayList<String> distance;
    private ArrayList<String> origin;
    private ArrayList<String> destination;
    private int count = 0;

    public void setValues(String distance, String origin, String destination){
        count++;
        this.distance.add(distance);
        this.origin.add(distance);
        this.destination.add(destination);
    }

    public String getDistance(int dist){
        return distance.get(dist);
    }

    public String getOrigin(int ori){
        return origin.get(ori);
    }

    public String getDestination(int dest){
        return destination.get(dest);
    }

    public int getCount(){
        return count;
    }
}
