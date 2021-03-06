package logic.commands.diet.dietmanager;


import classes.LocationInfo;
import geocoder.GeocodeResult;
import geocoder.Geocoder;
import logic.parser.MedicationManagerParser;
import logic.commands.Command;
import logic.commands.CommandResult;
import org.apache.commons.lang3.StringUtils;
import storage.Storage;

import java.io.IOException;
import java.util.HashMap;

import static ui.diet.dietmanager.MedicationManagerUi.DIET_IO_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_NEW_SUCCESS;
import static ui.diet.dietmanager.MedicationManagerUi.EMPTY_STRING;

public class MedicationSessionCreate extends Command {

    private final MedicationManagerParser parser = new MedicationManagerParser();

    @Override
    public CommandResult execute(String input, Storage storage) {
        String result = EMPTY_STRING;
        try {
            StringBuilder message = new StringBuilder();
            HashMap<String, String> parsedParams = parser.extractDietManagerCommandNameAndQuantity("add", input);
            // extract the date and tags and assigns it to the string
            String address = parser.extractNewName(parsedParams, message);
            String country = parser.extractNewAddress(parsedParams, message);
            if (message.length() != 0) {
                ui.showToUser(message.toString().trim());
            }
            Geocoder geocoder = new Geocoder();
            GeocodeResult response = null;
            if (country != null) {
                response = geocoder.geocodeSync(address, country);
            } else {
                response = geocoder.geocodeSync(address);
            }
            String formattedAddress = "";
            if (StringUtils.isNumeric(address)) {
                for (int i = 0; i < response.getResults().size(); i++) {
                    for (int j = 0; j < response.getResults().get(i).getAddressComponents().size(); j++) {
                        formattedAddress += response.getResults().get(i).getAddressComponents().get(j).getLongName() + " ";
                    }
                }
                formattedAddress.trim();
            } else {
                formattedAddress = response.getResults().get(0).getFormattedAddress();
            }
            LocationInfo location = new LocationInfo(address, formattedAddress, response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude(),response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());

            location.start(address);
            result = DIET_NEW_SUCCESS;
        } catch (IOException | InterruptedException e) {
            result = DIET_IO_WRONG_FORMAT;
        }
        return new CommandResult(result);
    }
}
