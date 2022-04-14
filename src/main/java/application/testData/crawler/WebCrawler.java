package application.testData.crawler;

import application.testData.generator.TestDataGenerator;
import application.testData.model.TestObject;
import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import static application.constants.ConstantsUtil.*;


/**
 * Sometimes, we need a random address from the country we never been to, just for checking the address format or getting address information
 * to register some sites. we have provide addresses from 128 countries and region.
 * Now this page show 20 addresses from Belgium, all these addresses follow the correct address format, it usually includes street, city,
 * state(some countries called province or oblast), phone number, zipcode(if have) and country calling code. Some countries with relatively small
 * land areas such as Singapore do not have provincial or state administrative areas, while others are not divided by state/province, but towns and
 * counties, but most countries follow the ”streets, City, state" hierarchical format.
 * You can generate addresses from other country by using the generator locate at the top of this page, just specify the country and quantity.
 **/

@Data
@EqualsAndHashCode
@ToString
public class WebCrawler {
    public static void addRandomAddressesFromLinkInFile(String url, String fileName) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span"); //street,city,state,phoneNumber,[zipCode],countryCallingCode

            Map<String, ArrayList<String>> randomAddressMap = getRandomAddressesMap(countryList, addressWithoutCountryList);

            List<TestObject> testObjectList = new ArrayList<>();
            for (int i = 0; i < randomAddressMap.get(COUNTRY).size(); i++) { // country nu lipseste niciodata din adresa postala
                String street = getCorrespondentValueField(STREET, randomAddressMap, i);
                String city = getCorrespondentValueField(CITY, randomAddressMap, i);
                String state = getCorrespondentValueField(STATE, randomAddressMap, i);
                String phoneNumber = getCorrespondentValueField(PHONE_NUMBER, randomAddressMap, i);
                String zipCode = getCorrespondentValueField(ZIP_CODE, randomAddressMap, i);
                String countryCallingCode = getCorrespondentValueField(COUNTRY_CALLING_CODE, randomAddressMap, i);
                String country = getCorrespondentValueField(COUNTRY, randomAddressMap, i);
                TestObject testObject = new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
                testObjectList.add(testObject);
            }
            insertRandomAddressesInFile(testObjectList, fileName);
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
    }

    public static boolean isValidLink(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span");
            return !(countryList.isEmpty() && addressWithoutCountryList.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, ArrayList<String>> getRandomAddressesMap(Elements countryList, Elements addressWithoutCountryList) {
        Map<String, ArrayList<String>> randomAddressMap = new HashMap<>();

        initialiseRandomAddressMap(randomAddressMap);

        for (Element element : countryList) {
            randomAddressMap.get(COUNTRY).add(element.text().replace(COUNTRY_KEY, ""));
        }

        for (Element element : addressWithoutCountryList) {
            if (element.text().toLowerCase().contains(STREET)) {
                randomAddressMap.get(STREET).add(element.text().replace(STREET_KEY, ""));
            } else {
                if (element.text().toLowerCase().contains(CITY)) {
                    randomAddressMap.get(CITY).add(element.text().replace(CITY_KEY, ""));
                } else {
                    if (element.text().toLowerCase().contains(STATE)) {
                        randomAddressMap.get(STATE).add(element.text().replace(STATE_KEY, ""));
                    } else {
                        if (element.text().toLowerCase().contains(PHONE_NUMBER_KEY.toLowerCase().trim())) {
                            randomAddressMap.get(PHONE_NUMBER).add(element.text().replace(PHONE_NUMBER_KEY, ""));
                        } else {
                            if (element.text().toLowerCase().contains(COUNTRY_CALLING_CODE_KEY.toLowerCase().trim())) {
                                randomAddressMap.get(COUNTRY_CALLING_CODE).add(element.text().replace(COUNTRY_CALLING_CODE_KEY, ""));
                            } else {
                                if (element.text().toLowerCase().contains(ZIP_CODE_KEY.toLowerCase().trim())) {
                                    randomAddressMap.get(ZIP_CODE).add(element.text().replace(ZIP_CODE_KEY, ""));
                                } else {
                                    if (element.text().toLowerCase().contains(COUNTRY)) {
                                        randomAddressMap.get(COUNTRY).add(element.text().replace(COUNTRY_KEY, ""));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return randomAddressMap;
    }

    private static void initialiseRandomAddressMap(Map<String, ArrayList<String>> randomAddressMap) {
        randomAddressMap.put(STREET, new ArrayList<>());
        randomAddressMap.put(CITY, new ArrayList<>());
        randomAddressMap.put(STATE, new ArrayList<>());
        randomAddressMap.put(PHONE_NUMBER, new ArrayList<>());
        randomAddressMap.put(ZIP_CODE, new ArrayList<>());
        randomAddressMap.put(COUNTRY_CALLING_CODE, new ArrayList<>());
        randomAddressMap.put(COUNTRY, new ArrayList<>());
    }

    public static void insertRandomAddressesInFile(List<TestObject> list, String fileName) {
        try {
            String filePath = "./files/test/correctRandomAddresses/" + fileName.replace("txt", "ser");
            File file = new File(filePath);
            new FileWriter(filePath, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(list);
            objectOut.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createANewSetForEachCountryFromToDoFile(int number) {
        try {
            File file = new File(INPUT_DATA_FILE); //RO.txt
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                int copyNumber = number;
                String filePath = reader.nextLine();
                String url = getCorespondentUrl(filePath);
                while (copyNumber > 0) {
                    if (isValidLink(url)) {
                        TestDataGenerator.createCorrectAddressesTestDataForEachCountry(url, filePath); // fisiere cu adrese corecte
                    }
                    copyNumber--;
                }
                if (isValidLink(url)) {
                    TestDataGenerator.createIncorrectAddressesTestDataForEachCountry(filePath); // fisiere cu adrese gresite care sa acopere cazurile din metoda
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getCorespondentUrl(String filePath) {
        String fileName = filePath.split("/")[4];
        String countryCode = fileName.replace(".txt", "");
        return "https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=20";
    }

    public static String getCorrespondentValueField(String nameField, Map<String, ArrayList<String>> randomAddress, int index) {
        String value = null;
        if (!randomAddress.get(nameField).isEmpty()) {
            value = randomAddress.get(nameField).get(index);
        }
        return value;
    }
}
