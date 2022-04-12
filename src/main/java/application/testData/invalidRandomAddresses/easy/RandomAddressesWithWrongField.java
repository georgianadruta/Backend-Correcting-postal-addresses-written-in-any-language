package application.testData.invalidRandomAddresses.easy;

import application.dataset.storage.DataStorage;
import application.testData.TestUtil;
import application.testData.correctRandomAddresses.TestObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

import static application.testData.correctRandomAddresses.TestObjectValues.*;

public class RandomAddressesWithWrongField {
    // se extrage fieldul care trebuie completat gresit. prima adresa e pt acest field

    public static void createRandomAddressesWithWrongFieldForAGivenFilePath(String filePath) {
        try {
            String countryCode = filePath.replace(".txt", "").split("/")[4];
            String url = "https://www.bestrandoms.com/random-address-in-" + countryCode + "?quantity=1";
            TestObject testObject = getTestObjectFromWebsite(url);
            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
            String fileName = RandomAddressesWithWrongField.class.getSimpleName() + ".ser";
            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
            File file = new File(newFilePath);
            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
            file.getParentFile().mkdirs();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            FileOutputStream fileOut = new FileOutputStream(newFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
            List<TestObject> testObjectList = getListObjectWithWrongField(serializedPath, testObject, countryCode);
            objectOut.writeObject(testObjectList);
            objectOut.close();
            fileWriter.close();
            System.out.println("test:\n" + testObject + "\n" + testObjectList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<TestObject> getListObjectWithWrongField(String serializedPath, TestObject givenTestObject, String countryCode) {
        List<TestObject> testObjectList = TestUtil.readFromSerializedFile(serializedPath);
        assert testObjectList != null;
        String countryName = getAnotherCountryName(countryCode);
        for (TestObject testObject : testObjectList) {
            int n = getRandomNumber();
            switch (n) {
                case 0 -> testObject.setStreet(givenTestObject.getStreet());
                case 1 -> testObject.setCity(givenTestObject.getCity());
                case 2 -> testObject.setState(givenTestObject.getState());
                case 4 -> testObject.setZipCode(givenTestObject.getZipCode());
                case 6 -> testObject.setCountry(countryName); // adresa retinuta are aceeasi tara. trb o alta tara
                default -> {
                }
            }
        }
        return testObjectList;
    }

    private static String getAnotherCountryName(String countryCode) {
        try {
            File file = new File(DataStorage.INPUT_DATA_FILE); //RO.txt
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) { // citim linie cu linie din fisierul cu pathurile catre fisierul pt fiecare tara
                String filePath = reader.nextLine();
                String currentCountryCode = filePath.split("/")[4].replace(".txt", "");
                if (!currentCountryCode.equals(countryCode)) {
                    String url = "https://www.bestrandoms.com/random-address-in-" + currentCountryCode + "?quantity=1";
                    TestObject testObject = getTestObjectFromWebsite(url);
                    assert testObject != null;
                    return testObject.getCountry();
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
        return null;
    }

    private static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(7); // 0, ... , 6 fara 3 si 5. nr de tel si country calling code nu s de interes
        while (n == 3 || n == 5) {
            n = rand.nextInt(7);
        }
        return n;
    }

    private static TestObject getTestObjectFromWebsite(String url) { // quantity = 1
        try {
            Document document = Jsoup.connect(url).get();
            Elements countryList = document.select("li.col-sm-6 > span"); //country
            Elements addressWithoutCountryList = document.select("li.col-sm-6 > p > span"); //street,city,state,phoneNumber,[zipCode],countryCallingCode
            Map<String, ArrayList<String>> randomAddress = getRandomAddressesMap(countryList, addressWithoutCountryList);
            return new TestObject(randomAddress.get(STREET).get(0), randomAddress.get(CITY).get(0), randomAddress.get(STATE).get(0),
                    randomAddress.get(PHONE_NUMBER).get(0), randomAddress.get(ZIP_CODE).get(0),
                    randomAddress.get(COUNTRY_CALLING_CODE).get(0), randomAddress.get(COUNTRY).get(0));
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
        return null;
    }

    private static Map<String, ArrayList<String>> getRandomAddressesMap(Elements countryList, Elements addressWithoutCountryList) {
        Map<String, ArrayList<String>> randomAddressMap = new HashMap<>();

        initialiseRandomAddressMap(randomAddressMap);

        randomAddressMap.get(COUNTRY).add(countryList.get(0).text().replace(COUNTRY_KEY, ""));

        if (addressWithoutCountryList.size() == 5) { // cate informatii are o adresa random. daca are 5 info => lipseste zip code
            randomAddressMap.get(STREET).add(addressWithoutCountryList.get(0).text().replace(STREET_KEY, ""));
            randomAddressMap.get(CITY).add(addressWithoutCountryList.get(1).text().replace(CITY_KEY, "").trim());
            randomAddressMap.get(STATE).add(addressWithoutCountryList.get(2).text().replace(STATE_KEY, ""));
            randomAddressMap.get(PHONE_NUMBER).add(addressWithoutCountryList.get(3).text().replace(PHONE_NUMBER_KEY, ""));
            randomAddressMap.get(ZIP_CODE).add(null);
            randomAddressMap.get(COUNTRY_CALLING_CODE).add(addressWithoutCountryList.get(4).text().replace(COUNTRY_CALLING_CODE_KEY, ""));
        } else {
            randomAddressMap.get(STREET).add(addressWithoutCountryList.get(0).text().replace(STREET_KEY, ""));
            randomAddressMap.get(CITY).add(addressWithoutCountryList.get(1).text().replace(CITY_KEY, "").trim());
            randomAddressMap.get(STATE).add(addressWithoutCountryList.get(2).text().replace(STATE_KEY, ""));
            randomAddressMap.get(PHONE_NUMBER).add(addressWithoutCountryList.get(3).text().replace(PHONE_NUMBER_KEY, ""));
            randomAddressMap.get(ZIP_CODE).add(addressWithoutCountryList.get(4).text().replace(ZIP_CODE_KEY, ""));
            randomAddressMap.get(COUNTRY_CALLING_CODE).add(addressWithoutCountryList.get(5).text().replace(COUNTRY_CALLING_CODE_KEY, ""));
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
}
