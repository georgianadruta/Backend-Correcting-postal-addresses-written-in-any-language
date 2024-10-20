package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;
import application.solution.SolutionUtil;
import application.testData.util.NameVariationsUtil;

import java.io.*;
import java.util.*;

import static application.constants.ConstantsUtil.*;

/**
 * singleton pattern to prevent creating multiple databases
 */
public class DataStorage implements Serializable {
    public static Set<AbstractLocation> abstractLocationSet = new HashSet<>();
    public static Set<Integer> foundGeoNameIds;

    private DataStorage() {
    }

    /**
     * helpful method to start the creating the data storage. first step is to add the countries
     */
    public static void createDataStorage() {
        foundGeoNameIds = new HashSet<>();
        addCountries();
    }

    /**
     * helpful method to add all the countries founded in the paths from INPUT_DATA_FILE
     */
    public static void addCountries() {
        try {
            File file = new File(INPUT_DATA_FILE);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) { // read line by line from countries paths file
                String filePath = reader.nextLine();
                System.out.println(filePath);
                File currentFile = new File(filePath);
                Scanner currentReader = new Scanner(currentFile);
                while (currentReader.hasNext()) { // read line by line from the current country file
                    String dataFromFile = currentReader.nextLine();
                    String[] splitData = dataFromFile.split(TAB);
                    int geoNameId = Integer.parseInt(splitData[0]);
                    String name = splitData[1];
                    String asciiName = splitData[2];
                    List<String> alternateNames = List.of(splitData[3].split(SEPARATOR_DB_FILES));
                    String featureClass = splitData[6];
                    String featureCode = splitData[7];
                    String code = splitData[8];
                    String admin1 = splitData[10];
                    if (featureClass.equals(A_CODE) && featureCode.equals(PCLI_CODE)) { // is country
                        name = SolutionUtil.getCanonicalForm(List.of(name)).get(0);
                        asciiName = SolutionUtil.getCanonicalForm(List.of(asciiName)).get(0);
                        alternateNames = SolutionUtil.getCanonicalForm(alternateNames);
                        NameVariationsUtil.addAllVariationsOfAnAddress(name, asciiName, alternateNames, null);
                        SolutionUtil.addAlternateNameInMap(name, asciiName, alternateNames);
                        Country country = new Country(geoNameId, name, asciiName, alternateNames, code, admin1);
                        country.addStates(filePath, country); // add all the states from the current country
                        abstractLocationSet.add(country); // add the country in the data storage set
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * helpful to add automatically all the country paths in INPUT_DATA_FILE
     */
    public static void addAllCountriesInToDoFile() {
        try {
            File countriesFolder = new File(COUNTRY_DIRECTORY_PATH);
            new FileWriter(INPUT_DATA_FILE, false).close();
            for (File fileEntry : Objects.requireNonNull(countriesFolder.listFiles())) {
                FileWriter fw = new FileWriter(INPUT_DATA_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println(COUNTRY_DIRECTORY_PATH + fileEntry.getName());
                out.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * helpful method to save the data storage set with all countries in datastorage.ser file
     */
    public static void saveDataStorage() {
        try {
            FileOutputStream fileOut = new FileOutputStream(SERIALIZED_DATA_STORAGE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(DataStorage.abstractLocationSet);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved at:" + SERIALIZED_DATA_STORAGE_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * get the data storage from the datastorage.ser file
     */
    public static void loadDataStorage() {
        try {
            FileInputStream fileIn = new FileInputStream(SERIALIZED_DATA_STORAGE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            abstractLocationSet = (Set<AbstractLocation>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * debug method
     * eg: for Romania this method should return 21443
     */
    public static int calculateNumberOfLocationsInWorld(AbstractLocation abstractLocation) {
        int sum = 0;
        for (var location : abstractLocation.getSubRegions()) {
            sum = sum + calculateNumberOfLocationsInWorld(location) + 1;
        }
        return sum;
    }
}