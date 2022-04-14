package application.testData.crawler;

import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.List;

public class WebCrawlerLoader {
    public static void main(String[] args) {
        // trebuie sa reduc numele la ascii name in adresele corecte. si solutia sa fie tot n ascii name
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.createANewSetForEachCountryFromToDoFile(1); // un set cu 5 * 20 adrese random


        // test ca se creeaza fisierele pt fiecare case
//        List<TestObject> list = TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");
//        System.out.println("correct" + list.get(0));
//        System.out.println("filled incorrectly" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithFieldsFilledIncorrectly.ser").get(0));
//        System.out.println("multiple data in one field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithMultipleDataInOneField.ser").get(0));
//        System.out.println("without a field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithoutAField.ser").get(0));
//        System.out.println("with wrong field" + TestUtil.readFromSerializedFile("./files/test/incorrectRandomAddresses/RO/RandomAddressesWithWrongField.ser").get(0));
    }
}
