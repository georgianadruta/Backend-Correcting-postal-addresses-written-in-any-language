package application.solution;

import application.dataset.structure.AbstractLocation;
import application.testData.model.TestObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static application.constants.ConstantsUtil.*;
import static application.solution.SolutionUtil.getAllSubsetsFromString;

public class Solution {
    private static int number = 0;

    /**
     * get the number of corrected addresses which correspond with the generated addresses from randomaddresses.com
     */
    public static int getNumberOfCorrectAddressesAfterCorrection(List<TestObject> testObjectList) {
        for (TestObject testObject : testObjectList) {
            if (testObject.getState().equals("bucharest") && testObject.getCity().equals("bucharest")) {
                testObject.setState("bucuresti");
                testObject.setCity("bucuresti");
            }
            TestObject correctedTestObject = getTheBestCorrectedAddress(testObject);
            if ((testObject.getCity().equals(correctedTestObject.getCity()) && testObject.getState().equals(correctedTestObject.getState()) || testObject.getCity().equals(correctedTestObject.getState()) && testObject.getState().equals(correctedTestObject.getCity())) // am adaugat aceasta conditie deoarece multe adrese generate de site au stateul inversat cu city
                    && testObject.getCountry().equals(correctedTestObject.getCountry())) {
                number++;
            } else {
                System.out.println(testObject + "" + correctedTestObject); // afiseaza adresele diferite
            }
        }
        return number;
    }

    /**
     * helpful method to find the best solution
     */
    private static TestObject getTheBestCorrectedAddress(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = getMapWithFieldsValue(testObject);
        String streetLine = testObject.getStreet();
        String city = testObject.getStreet();
        String state = testObject.getStreet();
        String phoneNumber = testObject.getStreet();
        String zipCode = testObject.getStreet();
        String countryCallingCode = testObject.getStreet();
        String country = testObject.getStreet();

        List<Pair<Integer, TestObject>> scoredCorrectedAddresses = new ArrayList<>();

        return new TestObject(null, "brasov", "brasov", null, null, null, "romania");
    }

    /**
     * helpful method to create a map with field name as key and a map with field names and corresponding locations names as value
     * eg for country : romania, iasi, state: brasov, iasi, city: com. bran, podoleni ->
     * {    country = { country = [romania], city = [iasi], state = [iasi] },
     * city = { country = [], city = [bran, podoleni], state = []},
     * state = { country = [], city = [brasov, iasi], state = [brasov, iasi]}
     * }
     */
    private static Map<String, Map<String, Set<String>>> getMapWithFieldsValue(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = new HashMap<>();
        Map<String, Set<String>> cities = new HashMap<>();
        mapWithFieldsValue.put(CITY, getValuesFieldsFromTestObject(testObject.getCity(), cities));
        Map<String, Set<String>> states = new HashMap<>();
        mapWithFieldsValue.put(STATE, getValuesFieldsFromTestObject(testObject.getState(), states));
        Map<String, Set<String>> countries = new HashMap<>();
        mapWithFieldsValue.put(COUNTRY, getValuesFieldsFromTestObject(testObject.getCountry(), countries));
        System.out.println(mapWithFieldsValue);
        return mapWithFieldsValue;
    }

    /**
     * helpful method to create a map for given value field
     * eg for country : romania, iasi -> { country = [romania], city = [iasi], state = [iasi] }
     */
    private static Map<String, Set<String>> getValuesFieldsFromTestObject(String name, Map<String, Set<String>> map) {
        try {
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
        if (className != null) {
            set.put(fieldName, foundLocations);
        }
    }
}
