package logic.commands;

import logic.commands.diet.dietmanager.MedicationSessionClear;
import logic.commands.diet.dietmanager.MedicationSessionCreate;
import logic.commands.diet.dietmanager.MedicationSessionDelete;
import logic.commands.diet.dietmanager.MedicationSessionEdit;
import logic.commands.diet.dietmanager.MedicationSessionHelp;
import logic.commands.diet.dietmanager.MedicationSessionList;
import logic.commands.diet.dietmanager.MedicationSessionSearch;
import logic.commands.diet.dietmanager.MedicationSessionWrong;
import logic.commands.diet.dietsession.MedicationAdd;
import logic.commands.diet.dietsession.MedicationClear;
import logic.commands.diet.dietsession.MedicationDelete;
import logic.commands.diet.dietsession.MedicationHelp;
import logic.commands.diet.dietsession.MedicationList;
import logic.commands.diet.dietsession.MedicationSearch;
import logic.commands.diet.dietsession.MedicationWrong;
import logic.commands.main.MainEnd;
import logic.commands.main.MainHelp;
import logic.commands.main.MainWrong;
import logic.commands.main.ToDiet;
import logic.commands.main.ToProfile;
import logic.commands.main.ToWorkout;
import java.util.Hashtable;

import static seedu.duke.Constants.COMMAND_WORD_ADD;
import static seedu.duke.Constants.COMMAND_WORD_CLEAR;
import static seedu.duke.Constants.COMMAND_WORD_DELETE;
import static seedu.duke.Constants.COMMAND_WORD_DIET;
import static seedu.duke.Constants.COMMAND_WORD_EDIT;
import static seedu.duke.Constants.COMMAND_WORD_END;
import static seedu.duke.Constants.COMMAND_WORD_HELP;
import static seedu.duke.Constants.COMMAND_WORD_LIST;
import static seedu.duke.Constants.COMMAND_WORD_NEW;
import static seedu.duke.Constants.COMMAND_WORD_PROFILE;
import static seedu.duke.Constants.COMMAND_WORD_SEARCH;
import static seedu.duke.Constants.COMMAND_WORD_WORKOUT;
import static seedu.duke.Constants.COMMAND_WORD_WRONG;

public class CommandLib {

    public Hashtable<String, Command> library;

    public CommandLib() {
        library = new Hashtable<>();
    }
    public void initMainMenuCl() {
        library.put(COMMAND_WORD_WRONG, new MainWrong());
        library.put(COMMAND_WORD_HELP, new MainHelp());
        library.put(COMMAND_WORD_DIET, new ToDiet());
        library.put(COMMAND_WORD_PROFILE, new ToProfile());
        library.put(COMMAND_WORD_WORKOUT, new ToWorkout());
        library.put(COMMAND_WORD_END, new MainEnd());
    }

    public void initMedicationManagerCl() {
        library.put(COMMAND_WORD_LIST, new MedicationSessionList());
        library.put(COMMAND_WORD_NEW, new MedicationSessionCreate());
        library.put(COMMAND_WORD_HELP, new MedicationSessionHelp());
        library.put(COMMAND_WORD_CLEAR, new MedicationSessionClear());
        library.put(COMMAND_WORD_SEARCH, new MedicationSessionSearch());
        library.put(COMMAND_WORD_EDIT, new MedicationSessionEdit());
        library.put(COMMAND_WORD_DELETE, new MedicationSessionDelete());
        library.put(COMMAND_WORD_WRONG, new MedicationSessionWrong());
    }
    public void initMedicationSessionCl() {
        library.put(COMMAND_WORD_ADD, new MedicationAdd());
        library.put(COMMAND_WORD_DELETE, new MedicationDelete());
        library.put(COMMAND_WORD_HELP, new MedicationHelp());
        library.put(COMMAND_WORD_CLEAR, new MedicationClear());
        library.put(COMMAND_WORD_SEARCH, new MedicationSearch());
        library.put(COMMAND_WORD_LIST, new MedicationList());
        library.put(COMMAND_WORD_WRONG, new MedicationWrong());
    }

    public Command getCommand(String keyword) {
        if (keyword == null || !library.containsKey(keyword.toLowerCase())) {
            return library.get(COMMAND_WORD_WRONG);
        } else {
            return library.get(keyword.toLowerCase());
        }
    }
}
