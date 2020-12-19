package logic.commands.diet.dietmanager;

import classes.LocationInfo;
import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;
import ui.diet.dietmanager.MedicationManagerUi;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static seedu.duke.Constants.PATH_TO_DIET_FOLDER;
import static ui.CommonUi.LS;
import static ui.diet.dietmanager.MedicationManagerUi.EMPTY_STRING;

public class MedicationSessionList extends Command {

    @Override
    public CommandResult execute(String input, Storage storage) {
        String message = EMPTY_STRING;
        File folder = new File(PATH_TO_DIET_FOLDER);
        File[] listOfFiles = folder.listFiles();
        StringBuilder listResult = new StringBuilder();
        assert folder.exists();
        try {
            String dietSessionListSize = "You have " + listOfFiles.length + " location(s)" + LS;
            String dietSessionList = formatList(listOfFiles, storage);
            listResult.append(dietSessionListSize);
            listResult.append(dietSessionList);
            message = listResult.toString();
            logger.log(Level.INFO, "Listed all available locations");
        } catch (NullPointerException | NoSuchElementException e) {
            message = MedicationManagerUi.DIET_NO_SESSION_SAVED;
            logger.log(Level.WARNING, "No instances of locations saved");
        }
        return new CommandResult(message);
    }

    private String formatList(File[] listOfFiles, Storage storage) {
        ArrayList<File> fileArrayList = new ArrayList<>();
        // converts all files in the array to an arraylist format
        Collections.addAll(fileArrayList, listOfFiles);
        // converts the file names into a stream
        ArrayList<String> fileNames = (ArrayList<String>) fileArrayList.stream()
                .map(f -> f.getName().split(" ", 2)[1].trim()).collect(Collectors.toList());
        // determine length of column for dynamic resizing
        int descriptionMaxLenInt = Math.max(8,
                fileNames.stream().max(Comparator.comparingInt(String::length)).get().length());

        String descriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt + 1) + "s";
        String returnString = String.format("%-8s", "Index") + String.format(descriptionFormat, "Tags")
                + String.format("%-12s", "Date") + String.format("%-10s", "Calories") + LS;

        StringBuilder infoBuilder = new StringBuilder(returnString);
        String listDescriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt) + "s %-11s %s";
        // adds the contents of each diet session and consolidates it into table format
        for (int i = 0; i < fileArrayList.size(); i++) {
            LocationInfo location = storage.readLocation(PATH_TO_DIET_FOLDER, listOfFiles[i].getName());
            // formats each diet session entry into column form
            String rowContent = formatRow(fileArrayList, listDescriptionFormat, i);
            String row = String.format("%-8s", i + 1) + rowContent + LS;
            infoBuilder.append(row);
        }
        returnString = infoBuilder.toString().trim();
        return returnString;
    }

    /**
     * Formats the row of text for each file.
     *
     * @param fileArrayList list of files in the folder converted to arraylist
     * @param listDescriptionFormat description of the file
     * @param i iterator integer for the file
     * @return formatted string of a row
     */
    private String formatRow(ArrayList<File> fileArrayList, String listDescriptionFormat, int i) {
        String rowContent = String.format(listDescriptionFormat,
                fileArrayList.get(i).getName().replaceFirst("[.][^.]+$", "").split(" ", 2)[1],
                fileArrayList.get(i).getName().replaceFirst("[.][^.]+$", "").split(" ", 2)[0]);
        return rowContent;
    }
}
