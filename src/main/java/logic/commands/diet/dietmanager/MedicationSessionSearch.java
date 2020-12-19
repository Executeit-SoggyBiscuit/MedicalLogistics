package logic.commands.diet.dietmanager;

import Medication.Medicationmanager.Medication;
import classes.LocationInfo;
import exceptions.InvalidDateFormatException;
import logic.commands.Command;
import logic.commands.CommandResult;
import logic.parser.DateParser;
import logic.parser.MedicationManagerParser;

import exceptions.diet.InvalidSearchDateException;
import storage.Storage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static seedu.duke.Constants.PATH_TO_DIET_FOLDER;
import static ui.CommonUi.LS;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_DATE_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_NO_SESSIONS_SAVED;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_SEARCH_EMPTY_TAG;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_SEARCH_RESULTS_MESSAGE;
import static ui.diet.dietmanager.MedicationManagerUi.EMPTY_STRING;

@SuppressWarnings("checkstyle:MissingJavadocType")
public class MedicationSessionSearch extends Command {
    private final MedicationManagerParser parser = new MedicationManagerParser();

    /*public CommandResult execute(String input, Storage storage) throws InvalidDateFormatException,
            InvalidSearchDateException {
        String message = EMPTY_STRING;
        File folder = new File(PATH_TO_DIET_FOLDER);
        File[] listOfFiles = folder.listFiles();
        StringBuilder searchResult = new StringBuilder();
        try {
            HashMap<String, String> parsedParams = parser.extractDietManagerCommandNameAndQuantity("search", input);

            String tag = parser.extractSearchTag(parsedParams, searchResult);
            //check for empty tag in search parameter
            checkEmptyTag(searchResult, tag);
            searchResult.append(DIET_SEARCH_RESULTS_MESSAGE + "\n\t ");
            //check for presence of files in diet session save folder
            checkEmptyFolder(listOfFiles, searchResult);
            //addToSearchResult(listOfFiles, searchResult, startDate, endDate, tag, storage);
            message = searchResult.toString();
        } catch (NullPointerException e) {
            message = "Wrong format, please enter in the format:\n\t "
                    + "search </s [STARTING_DATE]> </e [END_DATE]> </t [TAG]>";
        } catch (NoSuchElementException e) {
            logger.log(Level.WARNING, "No such element in diet session search");
            ui.showToUser("Sorry, there is nothing found in your diet menu.");
        }
        return new CommandResult(message.trim());
    }*/

    private void checkEmptyFolder(File[] listOfFiles, StringBuilder searchResult) {
        if (Objects.requireNonNull(listOfFiles).length == 0) {
            searchResult.append(DIET_NO_SESSIONS_SAVED + "\n\t ");
        }
    }

    private void checkEmptyTag(StringBuilder searchResult, String tag) {
        if (tag.isEmpty()) {
            searchResult.append(DIET_SEARCH_EMPTY_TAG + "\n\t ");
        }
    }

    /*private void addToSearchResult(File[] listOfFiles, StringBuilder searchResult, LocalDateTime startDate,
                                   LocalDateTime endDate, String tag, Storage storage)
            throws InvalidDateFormatException {
        //convert the file array to an arraylist for easier manipulation
        ArrayList<File> fileArrayList = new ArrayList<>();
        Collections.addAll(fileArrayList, listOfFiles);

        ArrayList<String> fileNames = (ArrayList<String>) fileArrayList.stream()
                .map(f -> f.getName().split(" ", 2)[1].trim()).collect(Collectors.toList());
        // get column boundaries for the table format for printing
        int descriptionMaxLenInt = Math.max(8,
                fileNames.stream().max(Comparator.comparingInt(String::length)).get().length());

        String descriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt + 1) + "s";
        String returnString = String.format("%-8s", "Index") + String.format(descriptionFormat, "Date")
                + String.format("%-12s", "Tag") + String.format("%-10s", "Calories") + LS;
        searchResult.append(returnString);
        String listDescriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt) + "s %-11s %s";
        int numberOfResult = 0;
        numberOfResult = addRow(listOfFiles, searchResult, startDate, endDate, tag, storage, fileArrayList,
                listDescriptionFormat, numberOfResult);
        String dietSessionSearchSize = "\n\t You have " + numberOfResult + " record(s)" + LS;
        if (numberOfResult == 0) {
            searchResult.setLength(0);
            searchResult.append("Sorry, there is nothing found in your diet menu.");
        }
        searchResult.append(dietSessionSearchSize);
    }*/

    /*private int addRow(File[] listOfFiles, StringBuilder searchResult, LocalDateTime startDate,
                       LocalDateTime endDate, String tag, Storage storage, ArrayList<File> fileArrayList,
                       String listDescriptionFormat, int numberOfResult) throws InvalidDateFormatException {
        for (int i = 0; i < fileArrayList.size(); i++) {
            //instantiates stored diet session to get total calorie count
            LocationInfo location = storage.readLocation(PATH_TO_DIET_FOLDER, listOfFiles[i].getName());
            //extract tags and dates and assigns to string from filename
            String fileTag = getFileTag(fileArrayList, i);
            String fileDate = getFileDate(fileArrayList, i);
            // converts extracted date string to java dateFormat
            LocalDateTime dateFormat = DateParser.parseDate(fileDate);
            if (startDate.compareTo(dateFormat) <= 0 && endDate.compareTo(dateFormat) >= 0 && fileTag.contains(tag)) {
                String rowContent = String.format(listDescriptionFormat, fileDate, fileTag);
                String row = String.format("%-8s", ++numberOfResult) + rowContent + LS;
                searchResult.append(row);
            }
        }
        return numberOfResult;
    }*/

    /**
     * Reads file name and returns date.
     *
     * @param fileArrayList list of files from local storage into array list
     * @param i index of file from search result
     * @return date as string
     */
    private String getFileDate(ArrayList<File> fileArrayList, int i) {
        String fileDate = fileArrayList.get(i).getName().replaceFirst("[.][^.]+$", "")
                .split(" ", 2)[0];
        return fileDate;
    }

    /**
     * Reads file name and returns tags.
     *
     * @param fileArrayList list of files from local storage into array list
     * @param i index of file from search result
     * @return date as string
     */
    private String getFileTag(ArrayList<File> fileArrayList, int i) {
        return fileArrayList.get(i).getName().replaceFirst("[.][^.]+$", "")
                        .split(" ", 2)[1];
    }
}
