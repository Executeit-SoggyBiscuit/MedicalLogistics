package classes;

import Location.Medication;

import java.util.ArrayList;

public class ArrayListMedication {

    private static ArrayListMedication medications;
    private ArrayList<Medication> list = null;

    public static ArrayListMedication getInstance() {
        if(medications == null) {
            medications = new ArrayListMedication();
        }

        return medications;
    }

    private ArrayListMedication() {
        list = new ArrayList<Medication>();
    }
    public ArrayList<Medication> getArray() {
        return this.list;
    }
}
