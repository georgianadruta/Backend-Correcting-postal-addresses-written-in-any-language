package application.dataset.structure;

import application.dataset.storage.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class World extends AbstractLocation {

    public World() {
        super(1, "World", "World", new String[]{}, 0, 0, null, null, null,
                null, null, null, null, null, 0, null, null, null, null);
        this.subRegions = new HashSet<>();
    }

    public void addCountries() {
        try {
            File file = new File(DataStorage.INPUT_DATA_FILE); //RO.txt
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) { // citim linie cu linie din fisierul cu pathurile catre fisierul pt fiecare tara
                String filePath = reader.nextLine();
                File currentFile = new File(filePath);
                Scanner currentReader = new Scanner(currentFile);
                while (currentReader.hasNext()) { // citim linie cu linie din fisierul tarii
                    String dataFromFile = currentReader.nextLine();
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
                    if (featureClass.equals("A") && featureCode.equals("PCLI")) { // este tara
                        Country country = new Country(geoNameId, name, asciiName, alternateNames, latitude, longitude,
                                featureClass, featureCode, code, cc2, admin1, admin2, admin3,
                                admin4, population, deviation, dem, timezone, modificationDate);
                        this.addSubRegion(country); // adaugam tara la world
                        country.addStates(filePath, country); // adaugam toate stateurile la tara
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    @Override
    public AbstractLocation getRoot() {
        return null;
    }
}
