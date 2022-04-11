package application.testData;

import application.testData.correctRandomAddresses.TestObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class TestUtil {
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

    public static void displayData(String pathName) {
        ObjectInputStream objectinputstream;
        try {
            FileInputStream streamIn = new FileInputStream(pathName);
            objectinputstream = new ObjectInputStream(streamIn);
            List<TestObject> testObjectList = (List<TestObject>) objectinputstream.readObject();
            objectinputstream.close();
            for (TestObject testObject : testObjectList) {
                System.out.println(testObject.toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
