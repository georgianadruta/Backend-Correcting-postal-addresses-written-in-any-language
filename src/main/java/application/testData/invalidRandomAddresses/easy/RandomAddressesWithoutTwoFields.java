package application.testData.invalidRandomAddresses.easy;

import application.testData.TestUtil;
import application.testData.correctRandomAddresses.TestObject;

import java.io.*;
import java.util.List;
import java.util.Random;

public class RandomAddressesWithoutTwoFields {
    // se extrag doua fielduri si se elimina
    public static void createRandomAddressesWithoutAFieldForAGivenFilePath(String filePath) {
        try {
            String countryCode = filePath.replace(".txt", "").split("/")[4];
            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
            String fileName = RandomAddressesWithoutTwoFields.class.getSimpleName() + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
            List<TestObject> testObjectList = getListObjectWithoutTwoFields(serializedPath);
            objectOut.writeObject(testObjectList);
            objectOut.close();
            fileWriter.close();
            System.out.println(testObjectList);
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

    public static List<TestObject> getListObjectWithoutTwoFields(String pathName) {
        List<TestObject> testObjectList = TestUtil.readFromSerializedFile(pathName);
        assert testObjectList != null;
        for (TestObject testObject : testObjectList) {
            int n = getRandomNumber();
            int m = getRandomNumber();
            while (n == m) {
                m = getRandomNumber();
            }
            removeFieldForGivenPosition(testObject, n);
            removeFieldForGivenPosition(testObject, m);
        }
        return testObjectList;
    }

    private static void removeFieldForGivenPosition(TestObject testObject, int n) {
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
}
