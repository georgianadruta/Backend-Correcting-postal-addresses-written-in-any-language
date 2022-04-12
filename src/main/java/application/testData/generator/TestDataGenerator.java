package application.testData.generator;

import application.testData.crawler.WebCrawler;
import application.testData.invalidRandomAddresses.easy.done.*;

public class TestDataGenerator {
    public static void createCorrectAddressesTestDataForEachCountry(String filePath) {
        String fileName = filePath.split("/")[4];
        String countryCode = fileName.replace(".txt", "");
        WebCrawler.addRandomAddressesFromLinkInFile("https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=20", fileName);
    }
    public static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        RandomAddressesWithoutAField.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath); // unele adrese pot avea 2 nuluri, pt ca le lipsesc deja zip codeul
        RandomAddressesWithWrongField.createRandomAddressesWithWrongFieldForAGivenFilePath(filePath);
        RandomAddressesWithoutTwoFields.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath);
    }
}
