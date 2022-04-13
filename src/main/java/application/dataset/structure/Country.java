package application.dataset.structure;

import application.dataset.storage.DataStorage;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static application.constants.Constants.ADMIN_1_CODES_FILE_ASCII;

@Getter
@Setter
public class Country extends AbstractLocation {
    final World worldRoot;

    public Country(int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
//                   float latitude, float longitude,
//                   String featureClass, String featureCode, String code, String cc2, String admin1, String admin2, String admin3,
//                   String admin4, float population, String deviation, String dem, String timezone, String modificationDate) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
//        latitude, longitude,
//                featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                admin4, population, deviation, dem, timezone, modificationDate);
        worldRoot = DataStorage.world;
    }

    public void addStates(String filePath, Country country) { // primeste pathul catre fisierul tarii curente
        try {
            File file = new File(ADMIN_1_CODES_FILE_ASCII); // in admin1 avem doar judetele cu codul de forma RO.18
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String dataFromFile = reader.nextLine();
                String[] splitData = dataFromFile.split("\t");
                String[] codeAndAdmin1 = splitData[0].split("\\.");
                String code = codeAndAdmin1[0];
                String admin1 = codeAndAdmin1[1];
                String name = splitData[1];
                String asciiName = splitData[2];
                String admin2 = splitData[3];
                if (code.equals(this.code)) {
                    searchStateInCountryFile(filePath, country, admin1);
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    private void searchStateInCountryFile(String filePath, Country country, String givenAdmin1) { // cauta in RO.txt judetul cu admin1
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String dataFromFile = reader.nextLine();
                String[] splitData = dataFromFile.split("\t");
                int geoNameId = Integer.parseInt(splitData[0]);
                String name = splitData[1];
                String asciiName = splitData[2];
                String[] alternateNames = splitData[3].split(",");
                float latitude = Float.parseFloat(splitData[4]);
                float longitude = Float.parseFloat(splitData[5]);
                String featureClass = splitData[6];
                String featureCode = splitData[7];
                String code = splitData[8];
                String cc2 = splitData[9];
                String admin1 = splitData[10];
                String admin2 = splitData[11];
                String admin3 = splitData[12];
                String admin4 = splitData[13];
                float population = Float.parseFloat(splitData[14]);
                String deviation = splitData[15];
                String dem = splitData[16];
                String timezone = splitData[17];
                String modificationDate = splitData[18];
                if (code.equals(country.getCode()) && admin1.equals(givenAdmin1) && featureClass.equals("A") && featureCode.equals("ADM1")) {
                    State state = new State(country, geoNameId, name, asciiName, alternateNames, code, admin1);
//                    latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
                    country.addSubRegion(state);
                    state.addSubStates(filePath, country, state);
                    return;
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    /**
     * @return null because this is the first level
     */
    @Override
    public AbstractLocation getRoot() {
        return worldRoot;
    }
}
