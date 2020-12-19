package classes;

import java.util.ArrayList;

public class ArrayListLocation {

    private static ArrayListLocation locations;
    private ArrayList<LocationInfo> list = null;

    public static ArrayListLocation getInstance() {
        if(locations == null) {
            locations = new ArrayListLocation();
        }

        return locations;
    }

    private ArrayListLocation() {
        list = new ArrayList<LocationInfo>();
    }
    public ArrayList<LocationInfo> getArray() {
        return this.list;
    }

}
