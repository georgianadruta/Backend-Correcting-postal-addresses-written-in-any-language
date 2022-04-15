package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;

import java.io.*;
import java.util.*;

import static application.constants.ConstantsUtil.INPUT_DATA_FILE;
import static application.constants.ConstantsUtil.SERIALIZED_OBJECT_PATH;

public class DataStorage implements Serializable {
    public static DataStorage dataStorage;

    public static Set<AbstractLocation> abstractLocationSet = new HashSet<>();
    public static Set<Integer> foundGeoNameIds;

    public static void createDataStorage() {
        initializeDataStorage();
    }

    private static void initializeDataStorage() {
        foundGeoNameIds = new HashSet<>();
        addCountries();
    }

    public static void addCountries() {
        try {
            File file = new File(INPUT_DATA_FILE); //RO.txt
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
                    String featureClass = splitData[6];
                    String featureCode = splitData[7];
                    String code = splitData[8];
                    String admin1 = splitData[10];
                    if (featureClass.equals("A") && featureCode.equals("PCLI")) { // este tara
                        AbstractLocation.addAllVariationsOfAnAddress(name, asciiName, alternateNames, null);
                        Country country = new Country(geoNameId, name, asciiName, alternateNames, code, admin1);
                        country.addStates(filePath, country); // adaugam toate stateurile la tara
                        abstractLocationSet.add(country); // adaugam tara la world
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    public static void addAllCountriesInToDoFile() {
        try {
            File countriesFolder = new File("./files/dataset/countries");
            new FileWriter(INPUT_DATA_FILE, false).close();
            for (File fileEntry : Objects.requireNonNull(countriesFolder.listFiles())) {
                FileWriter fw = new FileWriter(INPUT_DATA_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println("./files/dataset/countries/" + fileEntry.getName());
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDataStorage() {
        try {
            var fileOut = new FileOutputStream(SERIALIZED_OBJECT_PATH);
            var out = new ObjectOutputStream(fileOut);
            System.out.println(DataStorage.abstractLocationSet);
            out.writeObject(DataStorage.abstractLocationSet);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved at:" + SERIALIZED_OBJECT_PATH);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadDataStorage() {
        try {
            var fileIn = new FileInputStream(SERIALIZED_OBJECT_PATH);
            var in = new ObjectInputStream(fileIn);
            abstractLocationSet = (Set<AbstractLocation>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Location class not found");
            c.printStackTrace();
        }
    }

    /**
     * Debug method
     */
    public static int calculateNumberOfLocationsInWorld(AbstractLocation abstractLocation) {
        int sum = 0;
        for (var location : abstractLocation.getSubRegions()) {
            sum = sum + calculateNumberOfLocationsInWorld(location) + 1;
        }
        return sum;
    }
}