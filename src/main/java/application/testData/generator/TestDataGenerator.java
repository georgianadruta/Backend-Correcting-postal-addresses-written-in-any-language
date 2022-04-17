package application.testData.generator;

import application.testData.crawler.WebCrawler;

/**
 * helpful class to generate correct and incorrect addresses files for testing
 */
public class TestDataGenerator {
    public static void createCorrectAddressesTestDataForEachCountry(int number, String url, String filePath, boolean isAppend) {
        String fileName = filePath.split("/")[4];
        WebCrawler.addRandomAddressesFromLinkInFile(number, url, fileName, isAppend);
    }

    public static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        AddressesThatNeedToBeCorrectedGenerator.createAddressesThatNeedToBeCorrectedForAGivenFilePath(filePath);
    }
}
