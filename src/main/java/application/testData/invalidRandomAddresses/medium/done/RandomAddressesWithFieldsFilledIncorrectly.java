package application.testData.invalidRandomAddresses.medium.done;

import application.testData.TestUtil;
import application.testData.model.TestObject;

import java.io.*;
import java.util.*;

public class RandomAddressesWithFieldsFilledIncorrectly {

    // nerespectarea sensului fieldului. fac shuffle datelor din adresa

    public static void createRandomAddressesWithFieldsFilledIncorrectlyForAGivenFilePath(String filePath) {
        try {
            String countryCode = filePath.replace(".txt", "").split("/")[4];
            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
            String fileName = RandomAddressesWithFieldsFilledIncorrectly.class.getSimpleName() + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
            List<TestObject> testObjectList = getShuffledListObject(serializedPath);
            objectOut.writeObject(testObjectList);
            objectOut.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<TestObject> getShuffledListObject(String serializedPath) {
        List<TestObject> testObjectList = TestUtil.readFromSerializedFile(serializedPath);
        assert testObjectList != null;
        List<TestObject> randomizedFieldsTestObjectList = new ArrayList<>();
        for (TestObject testObject : testObjectList) {
            randomizedFieldsTestObjectList.add(getTestObjectWithShuffledFields(testObject));
        }
        return randomizedFieldsTestObjectList;
    }

    private static TestObject getTestObjectWithShuffledFields(TestObject testObject) {
        List<String> list = Arrays.asList(testObject.getStreet(), testObject.getCity(), testObject.getState(),
                testObject.getPhoneNumber(), testObject.getZipCode(), testObject.getCountryCallingCode(), testObject.getCountry());
        Collections.shuffle(list);
        return new TestObject(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
    }
}
