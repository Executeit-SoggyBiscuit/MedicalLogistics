package classes;

import Location.Medication;
import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidCommandWordException;
import exceptions.InvalidDateFormatException;
import exceptions.diet.InvalidSearchDateException;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.DietSessionParser;
import storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.Constants.PATH_TO_DIET_FOLDER;

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
    private final DietSessionParser parser = new DietSessionParser();

    public LocationInfo(String name, String address, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        storage = new Storage();
        this.medList = new ArrayList<>();
        this.latitude = latitude;
        this.longitude = longitude;
        latlong = latitude + "," + longitude;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        if (longitude != null) {
            latlong = latitude + "," + longitude;
        }
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        if (latitude != null) {
            latlong = latitude + "," + longitude;
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatlong() {
        return latlong;
    }

    public void start(String name) throws IOException {

        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        if (name.endsWith(".json")) {
            name =  name.substring(0,name.length() - ".json".length());
        }
        this.name = name;
        // save the file upon creation
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
        dietSessionInputLoop();
    }


    /**
     * Starts reading user input for dietSession commands.
     */
    private void dietSessionInputLoop() {
        String input = "";

        input = getCommand("Location Menu > "+ name);
        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                //dietSessionUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
                break;
            } catch (InvalidCommandWordException | InvalidSearchDateException | InvalidDateFormatException e) {
                //dietSessionUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            }
            input = getCommand("Location Menu > " + name);
        }
    }

    public String getCommand(String menuName) {
        System.out.print(menuName + " >>>>> ");
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();

        // Silently consume all blank lines
        while (inputLine.trim().isEmpty()) {
            System.out.print(menuName + " >>>>> ");
            inputLine = sc.nextLine();
        }

        return inputLine.replaceAll("\\s+", " ").trim();
    }

    /**
     * Processes user input for dietSession commands.
     *
     * @param input user input for command
     * @throws NullPointerException handles null pointer exception
     */
    private void processCommand(String input) throws NullPointerException, InvalidCommandWordException,
            InvalidSearchDateException, InvalidDateFormatException {


        String[] commParts = parser.parseCommand(input);
        Command command = cl.getCommand(commParts[0]);
        CommandResult commandResult = command.execute(commParts[1].trim(), medList, storage);
        //dietSessionUi.showToUser(commandResult.getFeedbackMessage());
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
    }


    /**
     * Constructs method to save changes to storage file.
     *
     * @param filePath string for file path
     * @param storage storage for diet manager
     * @param ds dietSession that is being changed
     */
    public void saveToFile(String filePath, Storage storage, LocationInfo ds) {
        try {
            storage.init(filePath, ds.getName());
            storage.writeToStorageDietSession(filePath, ds.getName(), ds);
            //logger.log(Level.INFO, "Diet session successfully saved");
        } catch (IOException e) {
            //logger.log(Level.WARNING, "save profile session failed");
            //dietSessionUi.showToUser("Failed to save your diet session!");
        }
    }
}