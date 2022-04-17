package application.testData.crawler;

import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.List;

public class WebCrawlerLoader {
    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.createANewSetForEachCountryFromToDoFile(5, true);


        List<TestObject> list = TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");
        System.out.println("correct" + list.size());
//        System.out.println("filled incorrectly" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithShuffledFields.ser").get(0));
//        System.out.println("multiple info in one field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithMultipleInfoInSeveralFields.ser").get(0));
//        System.out.println("empty field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithAnEmptyField.ser").get(0));
//        System.out.println("wrong completed field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithWrongFieldCompleted.ser").get(0));
    }
}
