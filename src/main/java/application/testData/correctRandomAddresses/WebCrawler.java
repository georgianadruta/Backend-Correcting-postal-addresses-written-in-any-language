package application.testData.correctRandomAddresses;

import application.dataset.storage.DataStorage;
import application.testData.invalidRandomAddresses.easy.RandomAddressesWithWrongField;
import application.testData.invalidRandomAddresses.easy.RandomAddressesWithoutAField;
import application.testData.invalidRandomAddresses.easy.RandomAddressesWithoutTwoFields;
import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import static application.testData.correctRandomAddresses.TestObjectValues.*;


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
    public void addRandomAddressesFromLinkInFile(String url, String fileName) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span"); //street,city,state,phoneNumber,[zipCode],countryCallingCode

            Map<String, ArrayList<String>> randomAddressMap = getRandomAddressesMap(countryList, addressWithoutCountryList);

            List<TestObject> testObjectList = new ArrayList<>();
            for (int i = 0; i < randomAddressMap.get(STREET).size(); i++) {
                TestObject testObject = new TestObject(randomAddressMap.get(STREET).get(i), randomAddressMap.get(CITY).get(i), randomAddressMap.get(STATE).get(i), randomAddressMap.get(PHONE_NUMBER).get(i),
                        randomAddressMap.get(ZIP_CODE).get(i), randomAddressMap.get(COUNTRY_CALLING_CODE).get(i), randomAddressMap.get(COUNTRY).get(i));
                testObjectList.add(testObject);
            }
            insertRandomAddressesInFile(testObjectList, fileName);
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
    }

    private Map<String, ArrayList<String>> getRandomAddressesMap(Elements countryList, Elements addressWithoutCountryList) {
        Map<String, ArrayList<String>> randomAddressMap = new HashMap<>();

        initialiseRandomAddressMap(randomAddressMap);

        for (Element element : countryList) {
            randomAddressMap.get(COUNTRY).add(element.text().replace(COUNTRY_KEY, ""));
        }

        if ((addressWithoutCountryList.size() / 20) == 5) { // cate informatii are o adresa random. daca are 5 info => lipseste zip code
            for (int i = 0; i < addressWithoutCountryList.size() - 5; i += 5) {
                randomAddressMap.get(STREET).add(addressWithoutCountryList.get(i).text().replace(STREET_KEY, ""));
                randomAddressMap.get(CITY).add(addressWithoutCountryList.get(i + 1).text().replace(CITY_KEY, "").trim());
                randomAddressMap.get(STATE).add(addressWithoutCountryList.get(i + 2).text().replace(STATE_KEY, ""));
                randomAddressMap.get(PHONE_NUMBER).add(addressWithoutCountryList.get(i + 3).text().replace(PHONE_NUMBER_KEY, ""));
                randomAddressMap.get(ZIP_CODE).add(null);
                randomAddressMap.get(COUNTRY_CALLING_CODE).add(addressWithoutCountryList.get(i + 4).text().replace(COUNTRY_CALLING_CODE_KEY, ""));
            }
        } else {
            for (int i = 0; i < addressWithoutCountryList.size() - 6; i += 6) {
                randomAddressMap.get(STREET).add(addressWithoutCountryList.get(i).text().replace(STREET_KEY, ""));
                randomAddressMap.get(CITY).add(addressWithoutCountryList.get(i + 1).text().replace(CITY_KEY, "").trim());
                randomAddressMap.get(STATE).add(addressWithoutCountryList.get(i + 2).text().replace(STATE_KEY, ""));
                randomAddressMap.get(PHONE_NUMBER).add(addressWithoutCountryList.get(i + 3).text().replace(PHONE_NUMBER_KEY, ""));
                randomAddressMap.get(ZIP_CODE).add(addressWithoutCountryList.get(i + 4).text().replace(ZIP_CODE_KEY, ""));
                randomAddressMap.get(COUNTRY_CALLING_CODE).add(addressWithoutCountryList.get(i + 5).text().replace(COUNTRY_CALLING_CODE_KEY, ""));
            }
        }
        return randomAddressMap;
    }

    private void initialiseRandomAddressMap(Map<String, ArrayList<String>> randomAddressMap) {
        randomAddressMap.put(STREET, new ArrayList<>());
        randomAddressMap.put(CITY, new ArrayList<>());
        randomAddressMap.put(STATE, new ArrayList<>());
        randomAddressMap.put(PHONE_NUMBER, new ArrayList<>());
        randomAddressMap.put(ZIP_CODE, new ArrayList<>());
        randomAddressMap.put(COUNTRY_CALLING_CODE, new ArrayList<>());
        randomAddressMap.put(COUNTRY, new ArrayList<>());
    }

    public void insertRandomAddressesInFile(List<TestObject> list, String fileName) {
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

    public void createCorrectAddressesTestDataForEachCountry(String filePath) {
        String fileName = filePath.split("/")[4];
        String countryCode = fileName.replace(".txt", "");
        addRandomAddressesFromLinkInFile("https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=20", fileName);
    }


    public void createANewSetForEachCountryFromToDoFile(int number) {
        try {
            File file = new File(DataStorage.INPUT_DATA_FILE); //RO.txt
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                int copyNumber = number;
                String filePath = reader.nextLine();
                while (copyNumber > 0) {
                    createCorrectAddressesTestDataForEachCountry(filePath); // fisiere cu adrese corecte
                    copyNumber--;
                }
                createIncorrectAddressesTestDataForEachCountry(filePath); // fisiere cu adrese gresite care sa acopere cazurile din metoda
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createIncorrectAddressesTestDataForEachCountry(String filePath) {
        RandomAddressesWithoutAField.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath); // unele adrese pot avea 2 nuluri, pt ca le lipsesc deja zip codeul
        RandomAddressesWithWrongField.createRandomAddressesWithWrongFieldForAGivenFilePath(filePath);
        RandomAddressesWithoutTwoFields.createRandomAddressesWithoutAFieldForAGivenFilePath(filePath);
    }
}
