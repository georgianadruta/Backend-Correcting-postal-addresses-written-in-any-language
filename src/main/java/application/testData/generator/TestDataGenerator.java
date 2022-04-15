package application.testData.generator;

import application.testData.crawler.WebCrawler;

/**
 * helpful class to generate correct and incorrect addresses files for testing
 */
public class TestDataGenerator {
    public static void createCorrectAddressesTestDataForEachCountry(String url, String filePath) {
        String fileName = filePath.split("/")[4];
        WebCrawler.addRandomAddressesFromLinkInFile(url, fileName);
    }

    public static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        AddressesThatNeedToBeCorrectedGenerator.createAddressesThatNeedToBeCorrectedForAGivenFilePath(filePath);
    }
}
