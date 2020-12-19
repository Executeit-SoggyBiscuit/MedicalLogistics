package classes;

import Medication.Medicationmanager.Medication;
import com.fasterxml.jackson.annotation.JsonProperty;
import logic.commands.CommandLib;
import storage.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class LocationInfo {
    private String name;
    private String address;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
    private String latlong;

    private final Storage storage;
    private transient CommandLib cl;
    private final ArrayList<Medication> medList;

    public LocationInfo(String name, String address, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        storage = new Storage();
        this.medList = new ArrayList<>();
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        if(longitude!=null){
            latlong = latitude + "," + longitude;
        }
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        if(latitude!=null){
            latlong = latitude + "," + longitude;
        }
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getLatitude(){
        return  latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public String getLatlong(){
        return latlong;
    }

    public void start(String name) throws IOException {

        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        this.name = name;
        // save the file upon creation
        saveToFile(storage, this);
        dietSessionInputLoop();
    }

    /**
     * Starts reading user input for dietSession commands.
     */
    private void dietSessionInputLoop() {
        String input = "";

        input = dietSessionUi.getCommand("Diet Menu > Diet Session " + name);

        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
                break;
            } catch (InvalidCommandWordException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            }
            input = dietSessionUi.getCommand("Diet Menu > Diet Session " + name);
        }
    }

    /**
     * Processes user input for dietSession commands.
     *
     * @param input user input for command
     * @throws NullPointerException handles null pointer exception
     */
    private void processCommand(String input) throws NullPointerException, InvalidCommandWordException {
        String[] commParts = parser.parse(input);
        Command command = cl.getCommand(commParts[0]);
        command.execute(commParts[1].trim(), medList, storage);
        saveToFile(storage, this);
    }

    public double getTotalCalories() {
        double totalCalories = 0;
        for (int i = 0; i < medList.size(); i++) {
            totalCalories += medList.get(i).getCalories();
        }
        return totalCalories;
    }


    /**
     * Constructs method to save changes to storage file.
     *
     * @param storage storage for diet manager
     * @param ds dietSession that is being changed
     */
    private void saveToFile(Storage storage, LocationInfo ds) {
        try {
            storage.init(ds.getName().toString());
            storage.writeToStorageDietSession(ds.getName().toString(), ds);
            logger.log(Level.INFO, "Diet session successfully saved");
        } catch (IOException e) {
            logger.log(Level.WARNING, "save profile session failed");
            dietSessionUi.showToUser("Failed to save your diet session!");
        }
    }

}
