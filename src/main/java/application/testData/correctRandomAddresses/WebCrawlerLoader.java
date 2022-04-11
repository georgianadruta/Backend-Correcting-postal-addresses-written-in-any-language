package application.testData.correctRandomAddresses;

import application.testData.invalidRandomAddresses.easy.RandomAddressesWithoutAField;

public class WebCrawlerLoader {
    public static void main(String[] args) {
        // trebuie sa reduc numele la ascii name in adresele corecte. si solutia sa fie tot n ascii name
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.createANewSetForEachCountryFromToDoFile(5); // un set cu 5 * 20 adrese random
        RandomAddressesWithoutAField.createRandomAddressesWithoutAField(); // unele adrese pot avea 2 nuluri, pt ca le lipsesc deja zip codeul
    }
}
