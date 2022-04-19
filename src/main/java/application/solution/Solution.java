package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static application.constants.ConstantsUtil.*;

public class Solution {
    private static int number = 0;

    public static int getNumberOfCorrectAddressesAfterCorrection(List<TestObject> testObjectList) {
        for (TestObject testObject : testObjectList) {
            if (testObject.getState().equals("bucharest") && testObject.getCity().equals("bucharest")) {
                testObject.setState("bucuresti");
                testObject.setCity("bucuresti");
            }
            TestObject correctedTestObject = getCorrectAddress(testObject);
            if ((testObject.getCity().equals(correctedTestObject.getCity()) && testObject.getState().equals(correctedTestObject.getState()) || testObject.getCity().equals(correctedTestObject.getState()) && testObject.getState().equals(correctedTestObject.getCity())) // am adaugat aceasta conditie deoarece multe adrese generate de site au stateul inversat cu city
                    && testObject.getCountry().equals(correctedTestObject.getCountry())) {
                number++;
            } else {
                System.out.println(testObject + "" + correctedTestObject);//afiseaza adresele diferite
            }
        }
        return number;
    }

    private static TestObject getCorrectAddress(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = new HashMap<>();
        mapWithFieldsValue.put(CITY, getCitiesFromTestObject(testObject));
        mapWithFieldsValue.put(STATE, getStatesFromTestObject(testObject));
        mapWithFieldsValue.put(COUNTRY, getCountriesFromTestObject(testObject));
        if (mapWithFieldsValue.get(STATE).isEmpty()) {
            // add states for founded cities mapWithFieldsValue.put(STATE, getStatesForFoundCities(mapWithFieldsValue.get(CITY)));
        }
        return getTheBestCorrectedAddress(testObject, mapWithFieldsValue);
    }

    private static TestObject getTheBestCorrectedAddress(TestObject testObject, Map<String, Map<String, Set<String>>> mapWithFieldsValue) {
        String streetLine = testObject.getStreet();
        String city = testObject.getStreet();
        String state = testObject.getStreet();
        String phoneNumber = testObject.getStreet();
        String zipCode = testObject.getStreet();
        String countryCallingCode = testObject.getStreet();
        String country = testObject.getStreet();

        List<Pair<Integer, TestObject>> scoredCorrectedAddresses = new ArrayList<>();
        System.out.println(mapWithFieldsValue);
        return new TestObject(null, "brasov", "brasov", null, null, null, "romania");
    }

    private static boolean contains(String string, String substring) {
        List<String> strings = List.of(string.split(" "));
        List<String> substrings = List.of(substring.split(" "));
        for (String substr : substrings) {
            if (!strings.contains(substr)) {
                return false;
            }
        }
        return true;
    }

//    private static List<TestObject> getAllPossibleCorrectedForm(Map<String, PriorityQueue<String>> mapWithFieldsValue) {
//        List<TestObject> testObjectList = new ArrayList<>();
//        List<String> cities = new ArrayList<>(mapWithFieldsValue.get(CITY));
//        List<String> states = new ArrayList<>(mapWithFieldsValue.get(STATE));
//        List<String> countries = new ArrayList<>(mapWithFieldsValue.get(COUNTRY));
//        TestObject testObject = new TestObject(null, null, null, null, null, null, null);
//        if (!cities.isEmpty()) {
//            testObject.setCity(cities.get(0));
//        }
//        if (!states.isEmpty()) {
//            testObject.setState(states.get(0));
//        }
//        if (!countries.isEmpty()) {
//            testObject.setCountry(countries.get(0));
//        }
//        testObjectList.add(testObject);
//        return testObjectList;
//    }
//
//    private static Set<String> getStatesForFoundCities(Set<String> strings) {
//        Set<String> list = new HashSet<>();
//        for (String str : strings) {
//            List<AbstractLocation> abstractLocations = SolutionUtil.multimap.get(str);
//            for (AbstractLocation abstractLocation : abstractLocations) {
//                if (abstractLocation instanceof State) {
//                    list.add(abstractLocation.getName());
//                }
//            }
//        }
//        return list;
//    }

    private static Map<String, Set<String>> getCitiesFromTestObject(TestObject testObject) {
        Map<String, Set<String>> cities = new HashMap<>();
        addElementInCountry(COUNTRY, testObject.getCity(), cities);
        addElementInState(STATE, testObject.getCity(), cities);
        addElementInCity(CITY, testObject.getCity(), cities);
        return cities;
    }

    private static Map<String, Set<String>> getCountriesFromTestObject(TestObject testObject) {
        Map<String, Set<String>> countries = new HashMap<>();
        addElementInCountry(COUNTRY, testObject.getCountry(), countries);
        addElementInState(STATE, testObject.getCountry(), countries);
        addElementInCity(CITY, testObject.getCountry(), countries);
        return countries;
    }

    private static Map<String, Set<String>> getStatesFromTestObject(TestObject testObject) {
        Map<String, Set<String>> states = new HashMap<>();
        addElementInCountry(COUNTRY, testObject.getState(), states);
        addElementInState(STATE, testObject.getState(), states);
        addElementInCity(CITY, testObject.getState(), states);
        return states;
    }

    private static void addElementInCity(String fieldName, String value, Map<String, Set<String>> set) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        Set<String> foundLocations = new HashSet<>();
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof State) {
                    foundLocations.add(subsetFromValue);
                }
            }
        }
        set.put(fieldName, foundLocations);
    }

    private static void addElementInState(String fieldName, String value, Map<String, Set<String>> set) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        Set<String> foundLocations = new HashSet<>();
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof Country) {
                    foundLocations.add(subsetFromValue);
                }
            }
        }
        set.put(fieldName, foundLocations);
    }

    private static void addElementInCountry(String fieldName, String value, Map<String, Set<String>> set) {
        Set<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        Set<String> foundLocations = new HashSet<>();
        for (String subsetFromValue : allSubsetsFromValue) {
            Set<AbstractLocation> list = new HashSet<>(SolutionUtil.multimap.get(subsetFromValue));
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation == null) {
                    foundLocations.add(subsetFromValue);
                }
                set.put(fieldName, foundLocations);
            }
        }
    }

    public static Set<String> getAllSubsetsFromString(String input) {
        String[] set = input.trim().split(" ");
        Set<String> subsetList = new HashSet<>();
        int n = set.length;

        for (int i = 0; i < (1 << n); i++) {
            StringBuilder subset = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    if (subset.isEmpty()) {
                        subset.append(set[j]);
                    } else {
                        subset.append(" ").append(set[j]);
                    }
                }
            }
            if (!subset.isEmpty()) {
                subsetList.add(String.valueOf(subset));
            }
        }
        return subsetList;
    }
}
