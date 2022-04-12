package application.testData.invalidRandomAddresses.medium.done;

import application.testData.util.TestUtil;
import application.testData.model.TestObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomAddressesWithMultipleDataInOneField {
    // clasa include adrese cu mai multe info intr un singur field

    public static void createRandomAddressesWithMultipleDataInOneFieldForAGivenFilePath(String filePath) {
        try {
            String countryCode = filePath.replace(".txt", "").split("/")[4];
            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
            String fileName = application.testData.invalidRandomAddresses.medium.done.RandomAddressesWithFieldsFilledIncorrectly.class.getSimpleName() + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
            List<TestObject> testObjectList = getMultipleInfoInOneFieldObjectList(serializedPath);
            objectOut.writeObject(testObjectList.get(0));
            objectOut.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(7); // 0, ... , 6;
    }

    private static List<TestObject> getMultipleInfoInOneFieldObjectList(String serializedPath) {
        List<TestObject> testObjectList = TestUtil.readFromSerializedFile(serializedPath);
        assert testObjectList != null;
        List<TestObject> multipleInfoInOneFieldObjectList = new ArrayList<>();
        for (TestObject testObject : testObjectList) {
            TestObject multipleInfoInOneFieldObject = getMultipleInfoInOneFieldObject(testObject);
            multipleInfoInOneFieldObjectList.add(multipleInfoInOneFieldObject);
        }
        return multipleInfoInOneFieldObjectList;
    }

    private static TestObject getMultipleInfoInOneFieldObject(TestObject testObject) {
        List<String> list = Arrays.asList(testObject.getStreet(), testObject.getCity(), testObject.getState(),
                testObject.getPhoneNumber(), testObject.getZipCode(), testObject.getCountryCallingCode(), testObject.getCountry());
        String street = null;
        String city = null;
        String state = null;
        String phoneNumber = null;
        String zipCode = null;
        String countryCallingCode = null;
        String country = null;
        for (String s : list) {
            int n = getRandomNumber();
            switch (n) {
                case 0 -> street = getNewValue(street, s);
                case 1 -> city = getNewValue(city, s);
                case 2 -> state = getNewValue(state, s);
                case 3 -> phoneNumber = getNewValue(phoneNumber, s);
                case 4 -> zipCode = getNewValue(zipCode, s);
                case 5 -> countryCallingCode = getNewValue(countryCallingCode, s);
                case 6 -> country = getNewValue(country, s);
                default -> {
                }
            }
        }
        return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
    }

    private static String getNewValue(String fieldValue, String valueToAdd) {
        if (valueToAdd != null) {
            if (fieldValue == null) {
                fieldValue = valueToAdd;
            } else {
                fieldValue = fieldValue + ", " + valueToAdd;
            }
        }
        return fieldValue;
    }
}
