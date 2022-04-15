package application.testData.util;

import application.testData.model.TestObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Random;

public class TestUtil {
    /**
     * helpful method to read list of TestObject from .ser files
     */
    public static List<TestObject> readFromSerializedFile(String pathName) {
        ObjectInputStream objectinputstream;
        try {
            FileInputStream streamIn = new FileInputStream(pathName);
            objectinputstream = new ObjectInputStream(streamIn);
            List<TestObject> testObjectList = (List<TestObject>) objectinputstream.readObject();
            objectinputstream.close();
            return testObjectList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * helpful method to generate a random number corresponding to the number of a field from address
     */
    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(7);
    }
}
