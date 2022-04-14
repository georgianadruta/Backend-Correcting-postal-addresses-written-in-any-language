package application.testData.invalidRandomAddresses.easy.done;

import application.testData.model.TestObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import static application.constants.Constants.*;

public class RandomAddressesWithWrongField {
    // se extrage fieldul care trebuie completat gresit si se inlocuieste cu fieldul corespunzator dintr-o alta adresa

    public static TestObject getAddressWithWrongField(TestObject testObject, String countryCode) {
        String url = "https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=1";
        int n = getRandomNumber();
        return getNewValueForGivenField(n, testObject, getTestObjectFromWebsite(url));
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

    private static TestObject getTestObjectFromWebsite(String url) { // quantity = 1
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span"); //street,city,state,phoneNumber,[zipCode],countryCallingCode
            Map<String, String> randomAddress = getRandomAddress(countryList, addressWithoutCountryList);
            return new TestObject(randomAddress.get(STREET), randomAddress.get(CITY), randomAddress.get(STATE),
                    randomAddress.get(PHONE_NUMBER), randomAddress.get(ZIP_CODE),
                    randomAddress.get(COUNTRY_CALLING_CODE), randomAddress.get(COUNTRY));
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
        return null;
    }

    private static Map<String, String> getRandomAddress(Elements countryList, Elements addressWithoutCountryList) {
        Map<String, String> randomAddressMap = new HashMap<>();

        randomAddressMap.put(COUNTRY, countryList.text().replace(COUNTRY_KEY, ""));

        for (Element element : addressWithoutCountryList) {
            if (element.text().toLowerCase().contains(STREET)) {
                randomAddressMap.put(STREET, element.text().replace(STREET_KEY, ""));
            } else {
                if (element.text().toLowerCase().contains(CITY)) {
                    randomAddressMap.put(CITY, element.text().replace(CITY_KEY, ""));
                } else {
                    if (element.text().toLowerCase().contains(STATE)) {
                        randomAddressMap.put(STATE, element.text().replace(STATE_KEY, ""));
                    } else {
                        if (element.text().toLowerCase().contains(PHONE_NUMBER_KEY.toLowerCase().trim())) {
                            randomAddressMap.put(PHONE_NUMBER, element.text().replace(PHONE_NUMBER_KEY, ""));
                        } else {
                            if (element.text().toLowerCase().contains(COUNTRY_CALLING_CODE_KEY.toLowerCase().trim())) {
                                randomAddressMap.put(COUNTRY_CALLING_CODE, element.text().replace(COUNTRY_CALLING_CODE_KEY, ""));
                            } else {
                                if (element.text().toLowerCase().contains(COUNTRY)) {
                                    randomAddressMap.put(COUNTRY, element.text().replace(COUNTRY_KEY, ""));
                                }
                            }
                        }
                    }
                }
            }
        }
        return randomAddressMap;
    }
}
