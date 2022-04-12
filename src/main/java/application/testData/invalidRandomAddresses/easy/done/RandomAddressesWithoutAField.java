package application.testData.invalidRandomAddresses.easy.done;

import application.testData.TestUtil;
import application.testData.model.TestObject;

import java.io.*;
import java.util.List;
import java.util.Random;

public class RandomAddressesWithoutAField {
    // se extrage un field intamplator si se elimina

    public static void createRandomAddressesWithoutAFieldForAGivenFilePath(String filePath) {
        try {
            String countryCode = filePath.replace(".txt", "").split("/")[4];
            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
            String fileName = RandomAddressesWithoutAField.class.getSimpleName() + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
            List<TestObject> testObjectList = getListObjectWithoutAField(serializedPath);
            objectOut.writeObject(testObjectList);
            objectOut.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(7); // 0, ... , 6
        while (n == 3) {
            n = rand.nextInt(7);
        }
        return n;
    }

    public static List<TestObject> getListObjectWithoutAField(String pathName) {
        List<TestObject> testObjectList = TestUtil.readFromSerializedFile(pathName);
        assert testObjectList != null;
        for (TestObject testObject : testObjectList) {
            int n = getRandomNumber();
            switch (n) {
                case 0 -> testObject.setStreet(null);
                case 1 -> testObject.setCity(null);
                case 2 -> testObject.setState(null);
                case 4 -> testObject.setZipCode(null);
                case 5 -> testObject.setCountryCallingCode(null);
                case 6 -> testObject.setCountry(null);
                default -> {
                }
            }
        }
        return testObjectList;
    }
}
