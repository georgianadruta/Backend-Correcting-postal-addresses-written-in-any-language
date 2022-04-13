package application.testData.generator;

import application.testData.crawler.WebCrawler;
import application.testData.invalidRandomAddresses.easy.done.*;
import application.testData.invalidRandomAddresses.medium.done.RandomAddressesWithFieldsFilledIncorrectly;
import application.testData.invalidRandomAddresses.medium.done.RandomAddressesWithMultipleDataInOneField;

public class TestDataGenerator {
    public static void createCorrectAddressesTestDataForEachCountry(String url, String filePath) {
        String fileName = filePath.split("/")[4];
        WebCrawler.addRandomAddressesFromLinkInFile(url, fileName);
    }

    public static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        RandomAddressesWithoutAField.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath); // unele adrese pot avea 2 nuluri, pt ca le lipsesc deja zip codeul
        RandomAddressesWithWrongField.createRandomAddressesWithWrongFieldForAGivenFilePath(filePath);
        RandomAddressesWithoutTwoFields.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath);
        RandomAddressesWithFieldsFilledIncorrectly.createRandomAddressesWithFieldsFilledIncorrectlyForAGivenFilePath(filePath);
        RandomAddressesWithMultipleDataInOneField.createRandomAddressesWithMultipleDataInOneFieldForAGivenFilePath(filePath);
    }
}
