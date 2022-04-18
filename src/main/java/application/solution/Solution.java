package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;

import java.util.*;

import static application.constants.ConstantsUtil.*;

public class Solution {
    private static int number = 0;

    public static int getNumberOfCorrectAddressesAfterCorrection(List<TestObject> testObjectList) {
        for (TestObject testObject : testObjectList) {
            TestObject correctedTestObject = getCorrectAddress(testObject);
            System.out.println(testObject + "" + correctedTestObject);//afiseaza adresele diferite
            if (testObject.getCity().equals(correctedTestObject.getCity()) && testObject.getState().equals(correctedTestObject.getState()) && testObject.getCountry().equals(correctedTestObject.getCountry())) {
                number++;
            }
        }
        return number;
    }

    private static TestObject getCorrectAddress(TestObject testObject) {
        Map<String, Set<String>> mapWithFieldsValue = new HashMap<>();
        mapWithFieldsValue.put(STATE, getStatesFromTestObject(testObject));
        mapWithFieldsValue.put(CITY, getCitiesFromTestObject(testObject));
        mapWithFieldsValue.put(COUNTRY, getCountriesFromTestObject(testObject));

        List<TestObject> correctedAddressList = getAllPossibleCorrectedForm(mapWithFieldsValue);

        return correctedAddressList.get(0);
    }

    private static List<TestObject> getAllPossibleCorrectedForm(Map<String, Set<String>> mapWithFieldsValue) {
        List<TestObject> testObjectList = new ArrayList<>();
        List<String> cities = new ArrayList<>(mapWithFieldsValue.get(CITY));
        List<String> states = new ArrayList<>(mapWithFieldsValue.get(STATE));
        List<String> countries = new ArrayList<>(mapWithFieldsValue.get(COUNTRY));
        TestObject testObject = new TestObject(null, null, null, null, null, null, null);
        if (!cities.isEmpty()) {
            testObject.setCity(cities.get(0));
        }
        if (!states.isEmpty()) {
            testObject.setState(states.get(0));
        }
        if (!countries.isEmpty()) {
            testObject.setCountry(countries.get(0));
        }
        testObjectList.add(testObject);
        return testObjectList;
    }

    private static Set<String> getCitiesFromTestObject(TestObject testObject) {
        Set<String> cities = new HashSet<>();
        addElementInCity(testObject.getCountry(), cities);
        addElementInCity(testObject.getState(), cities);
        addElementInCity(testObject.getCity(), cities);
        return cities;
    }

    private static Set<String> getCountriesFromTestObject(TestObject testObject) {
        Set<String> countries = new HashSet<>();
        addElementInCountry(testObject.getCountry(), countries);
        addElementInCountry(testObject.getState(), countries);
        addElementInCountry(testObject.getCity(), countries);
        return countries;
    }

    private static Set<String> getStatesFromTestObject(TestObject testObject) {
        Set<String> states = new HashSet<>();
        addElementInState(testObject.getCountry(), states);
        addElementInState(testObject.getState(), states);
        addElementInState(testObject.getCity(), states);
        return states;
    }

    private static void addElementInCity(String value, Set<String> set) {
        List<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        for (String subsetFromValue : allSubsetsFromValue) {
            List<AbstractLocation> list = SolutionUtil.multimap.get(subsetFromValue);
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof State) {
                    set.add(subsetFromValue);
                }
            }
        }
    }

    private static void addElementInState(String value, Set<String> set) {
        List<String> allSubsetsFromValue = getAllSubsetsFromString(value);
        for (String subsetFromValue : allSubsetsFromValue) {
            List<AbstractLocation> list = SolutionUtil.multimap.get(subsetFromValue);
            for (AbstractLocation abstractLocation : list) {
                if (abstractLocation instanceof Country) {
                    set.add(subsetFromValue);
                } else {
                    if (abstractLocation instanceof State && abstractLocation.contains(subsetFromValue)) {
                        set.add(abstractLocation.getName());
                    }
                }
            }
        }
    }

    private static void addElementInCountry(String value, Set<String> set) {
        List<AbstractLocation> list = SolutionUtil.multimap.get(value);
        if (SolutionUtil.multimap.containsKey(value) && list.parallelStream().allMatch(Objects::isNull)) {
            set.add(value);
        }
    }

    public static List<String> getAllSubsetsFromString(String input) {
        String[] set = input.trim().split(" ");
        List<String> subsetList = new ArrayList<>();
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
        Collections.reverse(subsetList);
        return subsetList;
    }
}
