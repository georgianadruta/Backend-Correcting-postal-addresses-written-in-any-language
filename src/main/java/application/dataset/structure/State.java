package application.dataset.structure;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
@Setter
public class State extends AbstractLocation {
    final Country countryRoot;

    public State(Country countryRoot, int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
//        float latitude, float longitude,
//                 String featureClass, String featureCode, String code, String cc2, String admin1, String admin2, String admin3,
//                 String admin4, float population, String deviation, String dem, String timezone, String modificationDate) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
//        latitude, longitude,
//                featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                admin4, population, deviation, dem, timezone, modificationDate);
        this.countryRoot = countryRoot;
    }

    public void addSubStates(String filePath, Country country, State state) {
        String stateCode = this.getCode();
        String stateAdmin1 = this.getAdmin1();
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
                if (code.equals(stateCode) && admin1.equals(stateAdmin1) && (featureClass.equals("P") || featureClass.equals("A"))) {
                    addAlternateNamesInMap(alternateNames, state);
                    City city = new City(state, geoNameId, name, asciiName, alternateNames, code, admin1);
//                    latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
                    state.addSubRegion(city);
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    @Override
    public AbstractLocation getRoot() {
        return countryRoot;
    }

    @Override
    public String toString() {
        return "AbstractLocation{" +
                "geoNameId=" + geoNameId +
                ", name='" + name + '\'' +
                ", asciiName='" + asciiName + '\'' +
                ", alternateNames=" + alternateNames +
//                ", latitude=" + latitude +
//                ", longitude=" + longitude +
//                ", featureClass='" + featureClass + '\'' +
//                ", featureCode='" + featureCode + '\'' +
                ", code='" + code + '\'' +
//                ", cc2='" + cc2 + '\'' +
//                ", admin1='" + admin1 + '\'' +
//                ", admin2='" + admin2 + '\'' +
//                ", admin3='" + admin3 + '\'' +
//                ", admin4='" + admin4 + '\'' +
//                ", population=" + population +
//                ", deviation='" + deviation + '\'' +
//                ", dem='" + dem + '\'' +
//                ", timezone='" + timezone + '\'' +
//                ", modificationDate='" + modificationDate + '\'' +
                ", subRegions=" + subRegions +
                '}';
    }
}
