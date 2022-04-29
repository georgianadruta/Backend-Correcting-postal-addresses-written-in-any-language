package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;
import application.testData.util.PairComparator;
import application.testData.util.TestObjectPairComparator;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static application.constants.ConstantsUtil.*;

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
            if (correctedTestObject != null && (testObject.getCity().equals(correctedTestObject.getCity()) &&
                    testObject.getState().equals(correctedTestObject.getState()) && testObject.getCountry().equals(correctedTestObject.getCountry()))) {
                number++;
            } else {
                System.out.println(testObject + "" + correctedTestObject); // afiseaza adresele diferite
            }
        }
        return number;
    }

    /**
     * helpful method to create a list of pairs [location name, score].
     * if the location is in the correct field will receive 5 points.
     * if we have several locations in field each location will receive numbers of location from field - the position points
     * in other words, for city: iasi, bucuresti, timisoara
     * iasi will receive 2 points because it's on first position -> it's more important than bucuresti and timisoara
     * bucuresti will receive 1 point and timisoara 0
     */
    private static List<Pair<String, Integer>> getPairsForGivenField(String fieldName, Map<String, Map<String, Set<String>>> map) {
        List<Pair<String, Integer>> list = new ArrayList<>();

        int score;
        for (String key : map.keySet()) {
            if (key.equals(fieldName)) {
                score = 5;
            } else {
                score = 0;
            }
            for (String subKey : map.get(key).keySet()) {
                if (subKey.equals(fieldName) && !map.get(key).get(subKey).isEmpty()) {
                    int length = map.get(key).get(subKey).size();
                    for (String value : map.get(key).get(subKey)) {
                        list.add(new Pair(value, score + length));
                        length--;
                    }
                }
            }
        }

        return getMinimalList(list);
    }

    /**
     * helpful method to remove the pairs with the same location and lower score
     * eg: [ (iasi, 5), (iasi, 2), (bucuresti, 1) ] -> [ (iasi, 5), (bucuresti, 1) ]
     */
    private static List<Pair<String, Integer>> getMinimalList(List<Pair<String, Integer>> list) {
        list.sort(new PairComparator());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getKey().equals(list.get(j).getKey())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    /**
     * helpful method to find the best solution
     */
    private static TestObject getTheBestCorrectedAddress(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = SolutionUtil.getMapWithFieldsValue(testObject);
        List<Pair<String, Integer>> scoredCountryList = getPairsForGivenField(COUNTRY, mapWithFieldsValue);
        List<Pair<String, Integer>> scoredStateList = getPairsForGivenField(STATE, mapWithFieldsValue);
        List<Pair<String, Integer>> scoredCityList = getPairsForGivenField(CITY, mapWithFieldsValue);

        if (scoredCityList.isEmpty() || scoredCountryList.isEmpty() || scoredStateList.isEmpty()) {
            return null;
        } else {
            System.out.println(scoredCountryList + "\n" + scoredStateList + "\n" + scoredCityList);
            List<Pair<TestObject, Integer>> scoredCorrectedAddresses = getScoredAllPossibleCorrectAddresses(scoredCountryList, scoredStateList, scoredCityList);

            return new TestObject(null, scoredCorrectedAddresses.get(0).getKey().getCity(), scoredCorrectedAddresses.get(0).getKey().getState(), null, null, null, scoredCorrectedAddresses.get(0).getKey().getCountry());
        }
    }

    /**
     * helpful method to create a list with pairs with possible correct addresses with corresponding scores
     */
    private static List<Pair<TestObject, Integer>> getScoredAllPossibleCorrectAddresses(List<Pair<String, Integer>> scoredCountryList, List<Pair<String, Integer>> scoredStateList, List<Pair<String, Integer>> scoredCityList) {
        List<Pair<TestObject, Integer>> list = new ArrayList<>();

        for (Pair<String, Integer> countryPair : scoredCountryList) {
            for (Pair<String, Integer> statePair : scoredStateList) {
                for (Pair<String, Integer> cityPair : scoredCityList) {
                    int score = countryPair.getValue() + statePair.getValue() + cityPair.getValue();
                    TestObject testObject = new TestObject(null, cityPair.getKey(), statePair.getKey(), null, null, null, countryPair.getKey());
                    if (isValidAddress(testObject)) {
                        list.add(new Pair<>(testObject, score));
                    }
                }
            }
        }
        list.sort(new TestObjectPairComparator());
        return list;
    }

    /**
     * helpful method to check if a generated address is valid, correct
     */
    private static boolean isValidAddress(TestObject testObject) {
        for (AbstractLocation abstractLocation : SolutionUtil.multimap.get(testObject.getCity())) {
            if (abstractLocation instanceof State && testObject.getState().equals(abstractLocation.getName())) {
                for (AbstractLocation abstractLocation1 : SolutionUtil.multimap.get(testObject.getState())) {
                    if (abstractLocation1 instanceof Country && testObject.getCountry().equals(abstractLocation1.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
