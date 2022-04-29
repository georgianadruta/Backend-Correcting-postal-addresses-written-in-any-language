package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.City;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

import static application.constants.ConstantsUtil.*;

public class SolutionUtil {
    /**
     * helpful data structure for searching the solution
     * we have a better complexity for searching starting with the lowest level
     * we'll get the corresponding node from tree with searched the name(complexity O(1))
     */
    public static ListMultimap<String, AbstractLocation> multimap = ArrayListMultimap.create();

    public static TestObject getCanonicalFormTestObject(TestObject testObject) {
        String street = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String city = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String state = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String phoneNumber = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String zipCode = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String countryCallingCode = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String country = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));

        return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
    }

    /**
     * helpful method to increase the precision of the algorithm
     * transform input in lowercase, remove diacritics and accents, remove special characters, transform nº in No and remove multiple white spaces
     */
    public static String[] getCanonicalForm(String[] inputList) {
        List<String> newList = new ArrayList<>();
        for (String input : inputList) {
            input = input.toLowerCase();
            input = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            newList.add(input.replaceAll("(\\D)\\.(\\d)", "$1 $2").replaceAll("[!@€£¢$¥%^*\"`~><()\\-.=_;,\\\\+?{}\\[\\]:|\\s]+", ONE_WHITESPACE).replaceAll("[–\\-]+", ONE_WHITESPACE).replaceAll("#+", ONE_WHITESPACE).replaceAll("/+", ONE_WHITESPACE).replaceAll("&+", " and ").replaceAll("º+", "o ").replaceAll("[`']+", EMPTY_STRING).replaceAll("[.]+", ONE_WHITESPACE).replaceAll("\\s+", ONE_WHITESPACE).trim());
        }
        return newList.toArray(new String[0]);
    }

    /**
     * check if the input is a phone number/zip code/country calling code
     * with other words the input does not have letters
     */
    public static boolean isPhoneNumberOrZipCodeOrCountryCallingCode(String input) {
        return !input.contains("[a-zA-Z]+");
    }

    public static void saveMultimap() {
        try {
            FileOutputStream fileOut = new FileOutputStream(SERIALIZED_MAP_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(multimap);
            out.close();
            fileOut.close();
            System.out.println("Serialized multimap is saved at:" + SERIALIZED_MAP_PATH);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * load multimap from serialized file
     */
    public static void loadMultimap() {
        try {
            FileInputStream fileIn = new FileInputStream(SERIALIZED_MAP_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            multimap = (ListMultimap<String, AbstractLocation>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * helpful method to check if the words from substring exist in string
     */
    public static boolean contains(String string, String substring) {
        List<String> strings = List.of(string.split(ONE_WHITESPACE));
        List<String> substrings = List.of(substring.split(ONE_WHITESPACE));
        for (String substr : substrings) {
            if (!strings.contains(substr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * helpful method to get all the subsets from string
     */
    public static Set<String> getAllSubsetsFromString(String input) {
        if (input != null) {
            String[] set = input.trim().split(ONE_WHITESPACE);
            set = new LinkedHashSet<>(Arrays.asList(set)).toArray(new String[0]);
            Set<String> subsetList = new HashSet<>();
            int n = set.length;

            for (int j = 0; j < set.length; j++) {
                for (int i = j; i < (1 << n); i++) {
                    StringBuilder subset = new StringBuilder();
                    for (int k = j; k < n; k++) {
                        if ((i & (1 << k)) > 0) {
                            if (subset.isEmpty()) {
                                subset.append(set[k]);
                            } else {
                                subset.append(ONE_WHITESPACE).append(set[k]);
                            }
                        }
                    }
                    if (!subset.isEmpty()) {
                        subsetList.add(String.valueOf(subset));
                    }
                }
            }
            return subsetList;
        } else {
            return new HashSet<>();
        }
    }

    /**
     * helpful method to create a map with field name as key and a map with field names and corresponding locations names as value
     * eg for country : romania, iasi, state: brasov, iasi, city: com. bran, podoleni ->
     * {    country = { country = [romania], city = [iasi], state = [iasi] },
     * city = { country = [], city = [bran, podoleni], state = []},
     * state = { country = [], city = [brasov, iasi], state = [brasov, iasi]}
     * }
     */
    public static Map<String, Map<String, Set<String>>> getMapWithFieldsValue(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = new HashMap<>();
        mapWithFieldsValue.put(STREET, getValuesFieldsFromTestObject(testObject.getStreet(), new HashMap<>()));
        mapWithFieldsValue.put(CITY, getValuesFieldsFromTestObject(testObject.getCity(), new HashMap<>()));
        mapWithFieldsValue.put(STATE, getValuesFieldsFromTestObject(testObject.getState(), new HashMap<>()));
        mapWithFieldsValue.put(PHONE_NUMBER, getValuesFieldsFromTestObject(testObject.getPhoneNumber(), new HashMap<>()));
        mapWithFieldsValue.put(ZIP_CODE, getValuesFieldsFromTestObject(testObject.getZipCode(), new HashMap<>()));
        mapWithFieldsValue.put(COUNTRY_CALLING_CODE, getValuesFieldsFromTestObject(testObject.getCountryCallingCode(), new HashMap<>()));
        mapWithFieldsValue.put(COUNTRY, getValuesFieldsFromTestObject(testObject.getCountry(), new HashMap<>()));
        System.out.println(mapWithFieldsValue);
        return mapWithFieldsValue;
    }

    /**
     * helpful method to create a map for given value field
     * eg for country : romania, iasi -> { country = [romania], city = [iasi], state = [iasi] }
     */
    private static Map<String, Set<String>> getValuesFieldsFromTestObject(String name, Map<String, Set<String>> map) {
        try {
            map.put(COUNTRY, new HashSet<>());
            map.put(STATE, new HashSet<>());
            map.put(CITY, new HashSet<>());
            addElementInGivenField(COUNTRY, null, name, map);
            addElementInGivenField(STATE, "application.dataset.structure.Country", name, map);
            addElementInGivenField(CITY, "application.dataset.structure.State", name, map);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return map;
    }

    /**
     * helpful method to add each location in the corresponding field
     * eg romania iasi -> country="romania", state="iasi", city="iasi"
     */
    private static void addElementInGivenField(String fieldName, String className, String value, Map<String, Set<String>> set) throws ClassNotFoundException {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        Set<String> foundLocations = new HashSet<>();
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (className == null) {
                    if (abstractLocation == null) {
                        foundLocations.add(subsetFromValue);
                    }
                    set.put(fieldName, foundLocations);
                } else {
                    if (Class.forName(className).isInstance(abstractLocation)) {
                        foundLocations.add(subsetFromValue);
                    }
                }
            }
        }
        if (fieldName.equals(COUNTRY)) {
            addCountriesNameWhichHaveTheGivenLocation(foundLocations, value);
        }
        if (fieldName.equals(STATE)) {
            addStatesNameWhichHaveTheGivenCityName(foundLocations, value);
        }
        if (className != null || value == null) {
            set.put(fieldName, foundLocations);
        }
    }

    /**
     * helpful method to add extra data for given data
     * eg: for bran will add states: iasi*, brasov*
     * note: each new data will be finished with '*' because is not explicit data from user
     */
    private static Set<String> addStatesNameWhichHaveTheGivenCityName(Set<String> foundStates, String cityName) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(cityName);
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof City) {
                    Set<AbstractLocation> secondList = new HashSet<>(SolutionUtil.multimap.get(abstractLocation.getName()));
                    for (AbstractLocation abstractLocation1 : secondList) {
                        if (abstractLocation1 instanceof State && !foundStates.contains(abstractLocation1.getName())) {
                            foundStates.add(abstractLocation1.getName() + STAR);
                        }
                    }
                } else {
                    if (abstractLocation instanceof State && !foundStates.contains(abstractLocation.getName())) {
                        foundStates.add(abstractLocation.getName() + STAR);
                    }
                }
            }
        }
        return foundStates;
    }

    /**
     * helpful method to add extra data for given data
     * eg: for iasi will add country: romania*
     * note: each new data will be finished with '*' because is not explicit data from user
     */
    private static Set<String> addCountriesNameWhichHaveTheGivenLocation(Set<String> foundCountries, String locationName) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(locationName);
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof State) {
                    Set<AbstractLocation> secondList = new HashSet<>(SolutionUtil.multimap.get(abstractLocation.getName()));
                    for (AbstractLocation abstractLocation1 : secondList) {
                        if (abstractLocation1 instanceof Country && !foundCountries.contains(abstractLocation1.getName())) {
                            foundCountries.add(abstractLocation1.getName() + STAR);
                        }
                    }
                } else {
                    if (abstractLocation instanceof Country && !foundCountries.contains(abstractLocation.getName())) {
                        foundCountries.add(abstractLocation.getName() + STAR);
                    }
                }
            }
        }
        return foundCountries;
    }
}
