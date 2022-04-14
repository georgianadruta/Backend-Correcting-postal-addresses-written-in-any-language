package application.testData.invalidRandomAddresses.easy.done;

import application.testData.crawler.WebCrawler;
import application.testData.model.TestObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import static application.constants.ConstantsUtil.*;

public class RandomAddressesWithWrongFieldCompleted {
    // se extrage fieldul care trebuie completat gresit si se inlocuieste cu fieldul corespunzator dintr-o alta adresa

    public static TestObject getAddress(TestObject testObject, String countryCode) {
        String url = "https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=1";
        int n = getRandomNumber();
        return getNewValueForGivenField(n, testObject, getNewTestObjectFromWebsite(url));
    }

    private static TestObject getNewValueForGivenField(int n, TestObject testObject, TestObject givenTestObject) {
        switch (n) {
            case 1 -> testObject.setCity(givenTestObject.getCity());
            case 2 -> testObject.setState(givenTestObject.getState());
            case 6 -> testObject.setCountry(givenTestObject.getCountry()); // adresa retinuta are aceeasi tara. trb o alta tara
            default -> {
            }
        }
        return testObject;
    }

    private static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(7); //street line, zip code, nr de tel si country calling code nu s de interes
        while (n == 0 || n == 3 || n == 4 || n == 5) {
            n = rand.nextInt(7);
        }
        return n;
    }

    private static TestObject getNewTestObjectFromWebsite(String url) { // quantity = 1
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span"); //street,city,state,phoneNumber,[zipCode],countryCallingCode
            Map<String, ArrayList<String>> randomAddress = WebCrawler.getRandomAddressesMap(countryList, addressWithoutCountryList);
            String street = WebCrawler.getCorrespondentValueField(STREET, randomAddress, 0);
            String city = WebCrawler.getCorrespondentValueField(CITY, randomAddress, 0);
            String state = WebCrawler.getCorrespondentValueField(STATE, randomAddress, 0);
            String phoneNumber = WebCrawler.getCorrespondentValueField(PHONE_NUMBER, randomAddress, 0);
            String zipCode = WebCrawler.getCorrespondentValueField(ZIP_CODE, randomAddress, 0);
            String countryCallingCode = WebCrawler.getCorrespondentValueField(COUNTRY_CALLING_CODE, randomAddress, 0);
            String country = WebCrawler.getCorrespondentValueField(COUNTRY, randomAddress, 0);
            return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
        return null;
    }
}
