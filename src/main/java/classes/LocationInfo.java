package classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import logic.commands.CommandLib;

public class LocationInfo {
    private String name;
    private String address;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
    private String latlong;

    private transient CommandLib cl;

    public LocationInfo(String name, String address) {
        this.name = name;
        this.address = address;
        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
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
        cl.initDietSessionCL();
        this.isNew = isNew;
        this.index = index;
        // save the file upon creation
        saveToFile(storage, this);
        dietSessionInputLoop();
        setEndDietSession(true);
    }

    /**
     * Starts reading user input for dietSession commands.
     */
    private void dietSessionInputLoop() {
        String input = "";

        if (isNew) {
            input = dietSessionUi.getCommand("Diet Menu > New Diet Session");
        } else {
            input = dietSessionUi.getCommand("Diet Menu > Diet Session " + index);
        }

        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
                break;
            } catch (InvalidCommandWordException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            }
            if (isNew) {
                input = dietSessionUi.getCommand("Diet Menu > New Diet Session");
            } else {
                input = dietSessionUi.getCommand("Diet Menu > Diet Session " + index);
            }
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
        command.execute(commParts[1].trim(), foodList, storage, index);
        saveToFile(storage, this);
    }

    public double getTotalCalories() {
        double totalCalories = 0;
        for (int i = 0; i < foodList.size(); i++) {
            totalCalories += foodList.get(i).getCalories();
        }
        return totalCalories;
    }


    /**
     * Constructs method to save changes to storage file.
     *
     * @param storage storage for diet manager
     * @param ds dietSession that is being changed
     */
    private void saveToFile(DietStorage storage, DietSession ds) {
        try {
            storage.init(ds.getDate().toString() + " " + ds.getTypeInput());
            storage.writeToStorageDietSession(ds.getDate().toString() + " " + ds.getTypeInput(), ds);
            logger.log(Level.INFO, "Diet session successfully saved");
        } catch (IOException e) {
            logger.log(Level.WARNING, "save profile session failed");
            dietSessionUi.showToUser("Failed to save your diet session!");
        }
    }

}
