package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.World;

import java.io.*;
import java.util.*;

import static application.constants.Constants.INPUT_DATA_FILE;
import static application.constants.Constants.SERIALIZED_OBJECT_PATH;

public class DataStorage implements Serializable {
    public static DataStorage dataStorage;

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