package application.testData.generator;

import application.testData.invalidRandomAddresses.easy.done.RandomAddressesWithWrongFieldCompleted;
import application.testData.invalidRandomAddresses.easy.done.RandomAddressesWithAnEmptyField;
import application.testData.invalidRandomAddresses.medium.done.RandomAddressesWithShuffledFields;
import application.testData.invalidRandomAddresses.medium.done.RandomAddressesWithMultipleInfoInSeveralFields;
import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressesThatNeedToBeCorrectedGenerator {
    public static void createAddressesThatNeedToBeCorrectedForAGivenFilePath(String filePath) {
        String countryCode = filePath.replace(".txt", "").split("/")[4];
        new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();

        String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
        List<TestObject> correctRandomAddressList = TestUtil.readFromSerializedFile(serializedPath);
        assert correctRandomAddressList != null;
        List<TestObject> randomAddressesWithoutAFieldList = new ArrayList<>();
        List<TestObject> randomAddressesWithAllWrongFieldList = new ArrayList<>();
        List<TestObject> randomAddressesWithFieldsFilledIncorrectlyList = new ArrayList<>();
        List<TestObject> randomAddressesWithMultipleDataInOneFieldList = new ArrayList<>();
        for (TestObject testObject : correctRandomAddressList) {
            randomAddressesWithoutAFieldList.add(getTheNewAddressForGivenCase(RandomAddressesWithAnEmptyField.class.getSimpleName(), testObject, countryCode));
            randomAddressesWithAllWrongFieldList.add(getTheNewAddressForGivenCase(RandomAddressesWithWrongFieldCompleted.class.getSimpleName(), testObject, countryCode));
            randomAddressesWithFieldsFilledIncorrectlyList.add(getTheNewAddressForGivenCase(RandomAddressesWithShuffledFields.class.getSimpleName(), testObject, countryCode));
            randomAddressesWithMultipleDataInOneFieldList.add(getTheNewAddressForGivenCase(RandomAddressesWithMultipleInfoInSeveralFields.class.getSimpleName(), testObject, countryCode));
        }
        addAddressesInFile(RandomAddressesWithAnEmptyField.class.getSimpleName(), countryCode, randomAddressesWithoutAFieldList);
        addAddressesInFile(RandomAddressesWithWrongFieldCompleted.class.getSimpleName(), countryCode, randomAddressesWithAllWrongFieldList);
        addAddressesInFile(RandomAddressesWithShuffledFields.class.getSimpleName(), countryCode, randomAddressesWithFieldsFilledIncorrectlyList);
        addAddressesInFile(RandomAddressesWithMultipleInfoInSeveralFields.class.getSimpleName(), countryCode, randomAddressesWithMultipleDataInOneFieldList);
    }

    private static void addAddressesInFile(String className, String countryCode, List<TestObject> testObjectList) {
        try {
            String fileName = className + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, true).close();
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(testObjectList);
            objectOut.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TestObject getTheNewAddressForGivenCase(String className, TestObject testObject, String countryCode) {
        try {
            TestObject copyTestObject = (TestObject) testObject.clone();
            switch (className) {
                case "RandomAddressesWithoutAField" -> {
                    return RandomAddressesWithAnEmptyField.getAddress(copyTestObject);
                }
                case "RandomAddressesWithWrongField" -> {
                    return RandomAddressesWithWrongFieldCompleted.getAddress(copyTestObject, countryCode);
                }
                case "RandomAddressesWithFieldsFilledIncorrectly" -> {
                    return RandomAddressesWithShuffledFields.getAddress(copyTestObject);
                }
                case "RandomAddressesWithMultipleDataInOneField" -> {
                    return RandomAddressesWithMultipleInfoInSeveralFields.getAddress(copyTestObject);
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
