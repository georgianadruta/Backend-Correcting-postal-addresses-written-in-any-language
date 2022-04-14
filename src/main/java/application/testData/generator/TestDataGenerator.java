package application.testData.generator;

import application.testData.crawler.WebCrawler;

public class TestDataGenerator {
    public static void createCorrectAddressesTestDataForEachCountry(String url, String filePath) {
        String fileName = filePath.split("/")[4];
        WebCrawler.addRandomAddressesFromLinkInFile(url, fileName);
    }

    public static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        AddressesThatNeedToBeCorrectedGenerator.createAddressesThatNeedToBeCorrectedForAGivenFilePath(filePath);
    }
}
