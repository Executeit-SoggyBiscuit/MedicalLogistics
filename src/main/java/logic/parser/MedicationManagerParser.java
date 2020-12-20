package logic.parser;

import exceptions.InvalidDateFormatException;
import logger.SchwarzeneggerLogger;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that is responsible for parsing user inputs in Diet Manager.
 */
public class MedicationManagerParser extends CommonParser {

    public Logger logger = SchwarzeneggerLogger.getInstanceLogger();

    /**
     * Extracts out date and time by looking for date strings in YYYY-MM-DD format.
     *
     * @param parsedParams user input for new diet session
     * @param extractDateMessage string builder that appends warnings and messages
     * @return date in MMM dd yyyy if the user inputs date in YYYY-MM-DD format;
     *         else returns original string
     * @throws IllegalStateException if the date is in wrong state
     */
    public int extractNewQuantity(HashMap<String, String> parsedParams, StringBuilder extractDateMessage)
            throws IllegalStateException {
        try {
            String quantityString = parsedParams.get("/q").trim();
            if (!quantityString.isEmpty()) {
                return Integer.parseInt(quantityString);
            } else {
                extractDateMessage.append("Quantity input is empty.\n");
            }
        } catch (NullPointerException e) {
            extractDateMessage.append("No quantity input is detected.\n");
        } catch (NumberFormatException e) {
            extractDateMessage.append("Quantity input is wrong.\n");
        }
        return 0;
    }

    /**
     * Extracts out tag of the diet session.
     *
     * @param parsedParams user input for new diet session
     * @param extractMealMessage string builder that appends warnings and messages
     * @return tag input if there is any;
     *         else returns "unspecified"
     * @throws NullPointerException if there is nothing in tag input
     */
    public String extractNewName(HashMap<String, String> parsedParams, StringBuilder extractMealMessage)
            throws NullPointerException {
        try {
            String tag = parsedParams.get("/n").trim();
            if (tag.isEmpty()) {
                extractMealMessage.append("Name input is empty\n");
                return null;
            } else {
                return tag;
            }
        } catch (NullPointerException e) {
            extractMealMessage.append("No Name is detected\n");
            return null;
        }
    }

    public String extractNewAddress(HashMap<String, String> parsedParams, StringBuilder extractMealMessage)
            throws NullPointerException {
        try {
            String tag = parsedParams.get("/a").trim();
            if (tag.isEmpty()) {
                extractMealMessage.append("Address input is empty\n");
                return null;
            } else {
                return tag;
            }
        } catch (NullPointerException e) {
            extractMealMessage.append("No Address input is detected\n");
            return null;
        }
    }

    /**
     * Extracts out starting date, end date and tag information.
     *
     * @param cmd user command
     * @param commandArgs user input
     * @return a hashmap where each information corresponds to the correct separator
     */
    @SuppressWarnings("checkstyle:CommentsIndentation")
    public HashMap<String, String> extractDietManagerCommandNameAndQuantity(String cmd, String commandArgs) {

        HashMap<String, String> parsedParams = new HashMap<>();
        int startIndex = 0;
        int endIndex = 0;

        try {
            while (commandArgs.indexOf("/", startIndex) != -1) {
                endIndex = commandArgs.indexOf("/", startIndex + 1);

                if (endIndex == -1) {
                    endIndex = commandArgs.length();
                }

                String parsedOption = commandArgs.substring(startIndex + 2, endIndex).trim();
                String optionIndicator = commandArgs.substring(startIndex, startIndex + 2).trim().toLowerCase();
                parsedParams.put(optionIndicator, parsedOption);

                startIndex = endIndex;
            }

            return parsedParams;
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Extracts out search tag.
     *
     * @param parsedParams a hashmap that contains information about tag
     * @param searchResult string build that contains warning messages
     * @return search tag
     */
    public String extractSearchTag(HashMap<String, String> parsedParams, StringBuilder searchResult) {
        try {
            String tag = parsedParams.get("/t").trim();
            return tag;
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "It looks like there is no input for search tag");
        }
        return "";
    }

    /**
     * Extracts out starting date.
     *
     * @param parsedParams a hashmap that contains information about starting date
     * @param searchResult string build that contains warning messages
     * @return starting date
     */
    public LocalDateTime extractStartDates(HashMap<String, String> parsedParams, StringBuilder searchResult) throws InvalidDateFormatException {

        try {
            String startDate = parsedParams.get("/s");
            if (!startDate.isEmpty()) {
                return DateParser.parseDate(startDate);
            }
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "It looks like there is no date input in start date");
        }
        searchResult.append("Starting date is empty, "
                + "and it is replaced with the earliest date.\n\t ");
        return DateParser.parseDate("0001-01-01");
    }

    /**
     * Extracts out end date.
     *
     * @param parsedParams a hashmap that contains information about end date
     * @param searchResult string build that contains warning messages
     * @return end date
     */
    public LocalDateTime extractEndDates(HashMap<String, String> parsedParams, StringBuilder searchResult) throws InvalidDateFormatException {

        try {
            String endDate = parsedParams.get("/e");
            if (!endDate.isEmpty()) {
                return DateParser.parseDate(endDate);
            }
        } catch (NullPointerException | InvalidDateFormatException e) {
            logger.log(Level.WARNING, "It looks like there is no date input in end date");
        }
        searchResult.append("End date is empty, "
                + "and it is replaced with the latest date.\n\t ");
        return DateParser.parseDate("9999-12-12");
    }

}
