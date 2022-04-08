package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.World;

import java.io.*;
import java.util.*;

public class DataStorage implements Serializable {
//    public static DataStorage dataStorage;
//    public static final String ALL_COUNTRIES_DATA_FILE = "./dataset/files/countries/RO.txt";
//    public static final String ADMIN1_CODES_ASCII_DATA_FILE = "./dataset/files/adminFiles/admin1CodesAscii.txt";
//    public static final String ADMIN2_CODES_ASCII_DATA_FILE = "./dataset/files/adminFiles/admin2Codes.txt";
//
//    public static Set<Integer> foundGeoNameIds = new HashSet<>();
//
//    public static World world = new World();
//
//    public static void createDataStorage() {
//        insertCountries();
//    }
//
//    private static void insertCountries() {
//        try {
//            File file = new File(ALL_COUNTRIES_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                int geoNameId = Integer.parseInt(splitData[0]);
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String[] alternateNames = splitData[3].split(",");
//                float latitude = Float.parseFloat(splitData[4]);
//                float longitude = Float.parseFloat(splitData[5]);
//                String featureClass = splitData[6];
//                String featureCode = splitData[7];
//                String code = splitData[8];
//                String cc2 = splitData[9];
//                String admin1 = splitData[10];
//                String admin2 = splitData[11];
//                String admin3 = splitData[12];
//                String admin4 = splitData[13];
//                float population = Float.parseFloat(splitData[14]);
//                String deviation = splitData[15];
//                String dem = splitData[16];
//                String timezone = splitData[17];
//                String modificationDate = splitData[18];
//                if (code.equals("RO") && featureClass.equals("A") && featureCode.equals("PCLI")) { // the location is Romania
//                    Country country = new Country(geoNameId, name, asciiName, alternateNames, latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
//                    world.addCountry(country);
//                    addStatesForCurrentCountry(country);
//                    return;
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//    }
//
//    private static void addStatesForCurrentCountry(Country country) {
//        String countryCode = country.getCode();
//        try {
//            File file = new File(ADMIN1_CODES_ASCII_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                String codeAndAdmin1 = splitData[0]; //RO.33
//                String code = codeAndAdmin1.split("\\.")[0]; //RO
//                String admin1 = codeAndAdmin1.split("\\.")[1]; //33
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String geoNameId = splitData[3]; //geoNameId fin allCountries.txt
//                if (code.equals(countryCode)) { // the location is from Romania
//                    State state = getStateWithGivenGeoNameId(country, Integer.parseInt(geoNameId));
//                    if (state != null) {
//                        country.addState(state);
//                        addSubStatesForCurrentState(country, state);
//                    }
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//    }
//
//    private static void addSubStatesForCurrentState(Country country, State state) {
//        try {
//            File file = new File(ADMIN2_CODES_ASCII_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                String codeAndAdmin1AndAdmin2 = splitData[0]; //RO.33.144116
//                String code = codeAndAdmin1AndAdmin2.split("\\.")[0]; //RO
//                String admin1 = codeAndAdmin1AndAdmin2.split("\\.")[1]; //33
//                String admin2 = codeAndAdmin1AndAdmin2.split("\\.")[2]; //144116
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String geoNameId = splitData[3];
//                if (code.equals(state.getCode()) && admin1.equals(state.getAdmin1())) {
//                    State subState = getStateWithGivenAdmin2Code(country, admin2);
//                    state.addSubStates(subState);
//                    addSubSubStatesForCurrentSubState(country, subState);
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//    }
//
//    private static void addSubSubStatesForCurrentSubState(Country country, State subState) {
//        try {
//            File file = new File(ALL_COUNTRIES_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                int geoNameId = Integer.parseInt(splitData[0]);
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String[] alternateNames = splitData[3].split(",");
//                float latitude = Float.parseFloat(splitData[4]);
//                float longitude = Float.parseFloat(splitData[5]);
//                String featureClass = splitData[6];
//                String featureCode = splitData[7];
//                String code = splitData[8];
//                String cc2 = splitData[9];
//                String admin1 = splitData[10];
//                String admin2 = splitData[11];
//                String admin3 = splitData[12];
//                String admin4 = splitData[13];
//                float population = Float.parseFloat(splitData[14]);
//                String deviation = splitData[15];
//                String dem = splitData[16];
//                String timezone = splitData[17];
//                String modificationDate = splitData[18];
//                if ((featureClass.equals("A") || featureClass.equals("P")) && admin1.equals(subState.getAdmin1()) && admin2.equals(subState.getAdmin2())) {
//                    State subSubState = new State(country, geoNameId, name, asciiName, alternateNames, latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
//                    subState.addSubStates(subSubState);
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//    }
//
//    private static State getStateWithGivenAdmin2Code(Country country, String givenAdmin2) {
//        try {
//            File file = new File(ALL_COUNTRIES_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                int geoNameId = Integer.parseInt(splitData[0]);
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String[] alternateNames = splitData[3].split(",");
//                float latitude = Float.parseFloat(splitData[4]);
//                float longitude = Float.parseFloat(splitData[5]);
//                String featureClass = splitData[6];
//                String featureCode = splitData[7];
//                String code = splitData[8];
//                String cc2 = splitData[9];
//                String admin1 = splitData[10];
//                String admin2 = splitData[11];
//                String admin3 = splitData[12];
//                String admin4 = splitData[13];
//                float population = Float.parseFloat(splitData[14]);
//                String deviation = splitData[15];
//                String dem = splitData[16];
//                String timezone = splitData[17];
//                String modificationDate = splitData[18];
//                if ((featureClass.equals("A") || featureClass.equals("P")) && admin2.equals(givenAdmin2)) {
//                    return new State(country, geoNameId, name, asciiName, alternateNames, latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//        return null;
//    }
//
//    private static State getStateWithGivenGeoNameId(Country country, int givenGeoNameId) {
//        try {
//            File file = new File(ALL_COUNTRIES_DATA_FILE);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNext()) {
//                String dataFromFile = reader.nextLine();
//                String[] splitData = dataFromFile.split("\t");
//                int geoNameId = Integer.parseInt(splitData[0]);
//                String name = splitData[1];
//                String asciiName = splitData[2];
//                String[] alternateNames = splitData[3].split(",");
//                float latitude = Float.parseFloat(splitData[4]);
//                float longitude = Float.parseFloat(splitData[5]);
//                String featureClass = splitData[6];
//                String featureCode = splitData[7];
//                String code = splitData[8];
//                String cc2 = splitData[9];
//                String admin1 = splitData[10];
//                String admin2 = splitData[11];
//                String admin3 = splitData[12];
//                String admin4 = splitData[13];
//                float population = Float.parseFloat(splitData[14]);
//                String deviation = splitData[15];
//                String dem = splitData[16];
//                String timezone = splitData[17];
//                String modificationDate = splitData[18];
//                if ((featureClass.equals("A") || featureClass.equals("P")) && geoNameId == givenGeoNameId) { // am gasit stateul cu geonameidul dat
//                    return new State(country, geoNameId, name, asciiName, alternateNames, latitude, longitude,
//                            featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                            admin4, population, deviation, dem, timezone, modificationDate);
//                }
//            }
//        } catch (FileNotFoundException exception) {
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//        }
//        return null;
//    }
//
//    public static int calculateNumberOfLocationsInWorld(AbstractLocation searchLocation) {
//        int sum = 0;
//        for (var location : searchLocation.getSubRegions()) {
//            sum = sum + calculateNumberOfLocationsInWorld(location) + 1;
//        }
//        return sum;
//    }

    public static DataStorage dataStorage;
    public static final String INPUT_DATA_FILE = "./dataset/files/todo/todo.txt";
    public static final String SERIALIZED_OBJECT_PATH = "#";
    public static final String ADMIN_1_CODES_FILE_ASCII = "./dataset/files/adminFiles/admin1CodesAscii.txt";

    public static World world;
    public static Set<Integer> foundGeoNameIds;

    public static void createDataStorage() {
        initializeDataStorage();
    }

    private static void initializeDataStorage() {
        foundGeoNameIds = new HashSet<>();
        world = new World();
        world.addCountries();
    }

    public void saveDataStorage() {
        try {
            var fileOut = new FileOutputStream(SERIALIZED_OBJECT_PATH);
            var out = new ObjectOutputStream(fileOut);
            System.out.println(world.getSubRegions());
            out.writeObject(world);
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
            world = (World) in.readObject();
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