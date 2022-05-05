package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.City;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;
import application.testData.util.comparator.LengthComparator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.io.*;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Predicate;

import static application.constants.ConstantsUtil.*;

public class SolutionUtil {
    /**
     * helpful data structure for searching the solution
     * we have a better complexity for searching starting with the lowest level
     * we'll get the corresponding node from tree with searched the name(complexity O(1))
     */
    public static SetMultimap<String, Map.Entry<String, AbstractLocation>> childNameParentMultimap = HashMultimap.create();
    public static SetMultimap<String, List<String>> nameAlternateNamesMultimap = HashMultimap.create();

    /**
     * helpful method to increase the precision of the algorithm
     * transform input in lowercase, remove diacritics and accents, remove special characters, transform nº in No and remove multiple white spaces
     */
    public static List<String> getCanonicalForm(List<String> inputList) {
        List<String> newList = new ArrayList<>();
        for (String input : inputList) {
            input = input.toLowerCase();
            input = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", EMPTY_STRING);
            newList.add(input.replaceAll("(\\D)\\.(\\d)", "$1 $2").replaceAll("[!@€£¢$¥%^*\"`~><()\\-.=_;,\\\\+?{}\\[\\]:|\\s]+", ONE_WHITESPACE).replaceAll("[–\\-]+", ONE_WHITESPACE).replaceAll("#+", ONE_WHITESPACE).replaceAll("/+", ONE_WHITESPACE).replaceAll("&+", " and ").replaceAll("º+", "o ").replaceAll("[`']+", EMPTY_STRING).replaceAll("[.]+", ONE_WHITESPACE).replaceAll("\\s+", ONE_WHITESPACE).trim());
        }
        return newList;
    }

    /**
     * check if the input is a phone number/zip code/country calling code
     * with other words the input does not have letters
     */
    public static boolean isZipCode(String input) {
        return !input.contains("[a-zA-Z]+");
    }

    /**
     * save multimaps to serialized files
     */
    public static void saveMultimaps() {
        try {
            saveChildParentMultimap(SolutionUtil.childNameParentMultimap);
            saveNameAlternateNamesMultimap(SolutionUtil.nameAlternateNamesMultimap);
            System.out.println("Serialized multimaps are saved at: " + SERIALIZED_CHILD_PARENT_MULTIMAP_PATH + ONE_WHITESPACE + SERIALIZED_NAME_ALTERNATE_NAMES_MULTIMAP_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * save childParentMultimap to serialized file
     */
    private static void saveChildParentMultimap(SetMultimap<String, Map.Entry<String, AbstractLocation>> childNameParentMultimap) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(SERIALIZED_CHILD_PARENT_MULTIMAP_PATH);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(childNameParentMultimap);
        out.close();
        fileOut.close();
    }

    /**
     * save nameAlternateNamesMultimap to serialized file
     */
    private static void saveNameAlternateNamesMultimap(SetMultimap<String, List<String>> childNameParentMultimap) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(SERIALIZED_NAME_ALTERNATE_NAMES_MULTIMAP_PATH);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(childNameParentMultimap);
        out.close();
        fileOut.close();
    }

    /**
     * load multimaps from serialized files
     */
    public static void loadMultimaps() {
        try {
            loadChildParentMultimap();
            loadNameAlternateNamesMultimap();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * load childParentMultimap from serialized file
     */
    private static void loadChildParentMultimap() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(SERIALIZED_CHILD_PARENT_MULTIMAP_PATH);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        childNameParentMultimap = (SetMultimap<String, Map.Entry<String, AbstractLocation>>) in.readObject();
        in.close();
        fileIn.close();
    }

    /**
     * load nameAlternateNamesMultimap from serialized file
     */
    private static void loadNameAlternateNamesMultimap() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(SERIALIZED_NAME_ALTERNATE_NAMES_MULTIMAP_PATH);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        nameAlternateNamesMultimap = (SetMultimap<String, List<String>>) in.readObject();
        in.close();
        fileIn.close();
    }

    /**
     * helpful method to get all the subsets from string
     */
    public static Set<String> getAllSubsetsFromString(String input) {
        if (input != null) {
            String[] set = input.trim().split(ONE_WHITESPACE);
            set = new LinkedHashSet<>(Arrays.asList(set)).toArray(new String[0]);
            int n = set.length;

            List<String> list = new ArrayList<>();
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
                        list.add(String.valueOf(subset));
                    }
                }
            }
            list.sort(new LengthComparator());
            return new LinkedHashSet<>(list);
        } else {
            return new LinkedHashSet<>();
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
        mapWithFieldsValue.put(ZIP_CODE, getValuesFieldsFromTestObject(testObject.getZipCode(), new HashMap<>()));
        mapWithFieldsValue.put(COUNTRY, getValuesFieldsFromTestObject(testObject.getCountry(), new HashMap<>()));

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
        Set<String> foundLocations = new LinkedHashSet<>();
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<Map.Entry<String, AbstractLocation>> list = new HashSet<>(SolutionUtil.childNameParentMultimap.get(subsetFromValue));
            for (Map.Entry<String, AbstractLocation> abstractLocation : list) {
                if (className == null) {
                    if (abstractLocation.getValue() == null) {
                        foundLocations.add(subsetFromValue);
                    }
                    set.put(fieldName, foundLocations);
                } else {
                    if (Class.forName(className).isInstance(abstractLocation.getValue())) {
                        foundLocations.add(abstractLocation.getKey());
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
    private static void addStatesNameWhichHaveTheGivenCityName(Set<String> foundStates, String cityName) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(cityName);
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<Map.Entry<String, AbstractLocation>> list = new HashSet<>(SolutionUtil.childNameParentMultimap.get(subsetFromValue));
            for (Map.Entry<String, AbstractLocation> abstractLocation : list) {
                if (abstractLocation.getValue() instanceof City) {
                    Set<Map.Entry<String, AbstractLocation>> secondList = new HashSet<>(SolutionUtil.childNameParentMultimap.get(abstractLocation.getValue().getName()));
                    for (Map.Entry<String, AbstractLocation> abstractLocation1 : secondList) {
                        if (abstractLocation1.getValue() instanceof State && !foundStates.contains(abstractLocation1.getValue().getName())) {
                            foundStates.add(abstractLocation1.getValue().getName() + STAR);
                        }
                    }
                } else {
                    if (abstractLocation.getValue() instanceof State && !foundStates.contains(abstractLocation.getValue().getName())) {
                        foundStates.add(abstractLocation.getValue().getName() + STAR);
                    }
                }
            }
        }
    }

    /**
     * helpful method to add extra data for given data
     * eg: for iasi will add country: romania*
     * note: each new data will be finished with '*' because is not explicit data from user
     */
    private static void addCountriesNameWhichHaveTheGivenLocation(Set<String> foundCountries, String locationName) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(locationName);
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<Map.Entry<String, AbstractLocation>> list = new HashSet<>(SolutionUtil.childNameParentMultimap.get(subsetFromValue));
            for (Map.Entry<String, AbstractLocation> abstractLocation : list) {
                if (abstractLocation.getValue() instanceof State) {
                    Set<Map.Entry<String, AbstractLocation>> secondList = new HashSet<>(SolutionUtil.childNameParentMultimap.get(abstractLocation.getValue().getName()));
                    for (Map.Entry<String, AbstractLocation> abstractLocation1 : secondList) {
                        if (abstractLocation1.getValue() instanceof Country && !foundCountries.contains(abstractLocation1.getValue().getName())) {
                            foundCountries.add(abstractLocation1.getValue().getName() + STAR);
                        }
                    }
                } else {
                    if (abstractLocation.getValue() instanceof Country && !foundCountries.contains(abstractLocation.getValue().getName())) {
                        foundCountries.add(abstractLocation.getValue().getName() + STAR);
                    }
                }
            }
        }
    }

    /**
     * helpful method to add for a name in multimap a list of alternate names
     */
    public static void addAlternateNameInMap(String name, String asciiName, List<String> alternateNames) {
        List<String> list = new ArrayList<>(alternateNames);
        list.addAll(List.of(name, asciiName));
        List<String> filteredList = list.stream()
                .distinct()
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isBlank))
                .toList();
        for (String element : filteredList) {
            Set<String> set = new HashSet<>();
            if (nameAlternateNamesMultimap.containsKey(element)) {
                Set<List<String>> strings = nameAlternateNamesMultimap.get(element);
                for (List<String> stringList : strings) {
                    nameAlternateNamesMultimap.remove(element, stringList);
                    set.addAll(stringList);
                }
            }
            set.addAll(filteredList);
            nameAlternateNamesMultimap.put(element, new ArrayList<>(set));
        }
    }
}
