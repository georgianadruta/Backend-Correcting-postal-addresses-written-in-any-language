package application.solution;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.Country;
import application.dataset.structure.State;
import application.testData.model.TestObject;
import application.testData.util.TestObjectPairComparator;
import application.testData.util.comparator.PairComparator;
import application.testData.util.comparator.StarListComparator;
import application.testData.util.testsGenerator.TestsGenerator;
import org.apache.commons.math3.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.*;

import static application.constants.ConstantsUtil.*;

public class Solution {

    /**
     * get the number of corrected addresses which correspond with the generated addresses from <a href="https://generate.plus/">https://generate.plus/</a>
     */
    public static int getNumberOfCorrectAddressesAfterCorrection(String path, String methodName, String... fieldNames) {
        int number = 0;
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String dataFromFile = reader.nextLine();
                String[] splitData = dataFromFile.split(SEPARATOR_CONVENTION);
                System.out.println(dataFromFile);
                TestObject testObject = new TestObject(splitData[0], splitData[1], splitData[2], splitData[3], splitData[4]);
                TestObject testObjectToCorrect = getTestObjectToCorrect(testObject, methodName, fieldNames);
                TestObject correctedTestObject = getTheBestCorrectedAddress(testObjectToCorrect);
                if (correctedTestObject != null && testObject.getCity().equals(correctedTestObject.getCity()) && testObject.getState().equals(correctedTestObject.getState()) && testObject.getCountry().equals(correctedTestObject.getCountry())) {
                    number++;
                } else {
                    System.out.println(testObject + EMPTY_STRING + testObjectToCorrect + EMPTY_STRING + correctedTestObject); // display the corrected addresses which are different from the initial addresses
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return number;
    }

    private static TestObject getTestObjectToCorrect(TestObject testObject, String methodName, String... fieldNames) {
        try {
            if (!methodName.equals("getCorrectAddress")) {
                Method method = TestsGenerator.class.getDeclaredMethod(methodName, TestObject.class, String[].class);
                return (TestObject) method.invoke(new TestsGenerator(), testObject, fieldNames);
            } else {
                return testObject;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return testObject;
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
        List<String> orderedList = new ArrayList<>();

        for (String key : map.keySet()) {
            for (String subKey : map.get(key).keySet()) {
                if (subKey.equals(fieldName)) {
                    if (!map.get(key).get(subKey).isEmpty()) {
                        orderedList.addAll(map.get(key).get(subKey));
                    }
                }
            }
            orderedList.sort(new StarListComparator());
            int length = orderedList.size();
            for (String value : orderedList) {
                int score = getCorrespondentScore(key, fieldName, value);
                list.add(new Pair(value, score + length));
                length--;
            }
        }
        return getMinimalList(list);
    }

    /**
     * helpful method to calculate the score
     */
    private static int getCorrespondentScore(String key, String fieldName, String value) {
        int score = 0;
        if (key.equals(fieldName)) {
            score = 5;
        }
        if (fieldName.equals(CITY) && isACityDifferentOfState(value)) {
            score += 3;
        }
        if (value.endsWith(STAR)) {
            score -= 3;
        }
        return score;
    }

    /**
     * helpful method to check if a city has a different name of state where it is found
     */
    private static boolean isACityDifferentOfState(String value) {
        Set<Map.Entry<String, AbstractLocation>> multimap = new HashSet<>(SolutionUtil.childNameParentMultimap.get(value));
        for (Map.Entry<String, AbstractLocation> abstractLocation : multimap) {
            if (abstractLocation.getValue() != null && abstractLocation.getValue().getName().equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * helpful method to remove the pairs with the same location and lower score
     * eg: [ (iasi, 5), (iasi, 2), (bucuresti, 1) ] -> [ (iasi, 5), (bucuresti, 1) ]
     */
    private static List<Pair<String, Integer>> getMinimalList(List<Pair<String, Integer>> list) {
        list.sort(new PairComparator());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (getStringWithoutStar(list.get(i).getKey()).equals(getStringWithoutStar(list.get(j).getKey()))) {
                    list.set(i, new Pair<>(list.get(i).getKey(), list.get(i).getValue() > list.get(j).getValue() ? list.get(i).getValue() : list.get(j).getValue()));
                    list.remove(j);
                    j--;
                }
            }
        }
        return list;
    }

    /**
     * helpful method to remove the last character if it is a star
     */
    private static String getStringWithoutStar(String name) {
        if (name.endsWith(STAR)) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    /**
     * helpful method to find the best solution
     */
    private static TestObject getTheBestCorrectedAddress(TestObject testObject) {
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = SolutionUtil.getMapWithFieldsValue(testObject);
        System.out.println(mapWithFieldsValue);

        List<Pair<String, Integer>> scoredCountryList = getPairsForGivenField(COUNTRY, mapWithFieldsValue);
        List<Pair<String, Integer>> scoredStateList = getPairsForGivenField(STATE, mapWithFieldsValue);
        List<Pair<String, Integer>> scoredCityList = getPairsForGivenField(CITY, mapWithFieldsValue);

        if (scoredCityList.isEmpty() || scoredCountryList.isEmpty() || scoredStateList.isEmpty()) {
            return null;
        } else {
            List<Pair<TestObject, Integer>> scoredCorrectedAddresses = getScoredAllPossibleCorrectAddresses(scoredCountryList, scoredStateList, scoredCityList);
            System.out.println(COUNTRY + scoredCountryList + NEW_LINE + STATE + scoredStateList + NEW_LINE + CITY + scoredCityList);
            return new TestObject(testObject.getStreet(), testObject.getZipCode(), scoredCorrectedAddresses.get(0).getKey().getState(), scoredCorrectedAddresses.get(0).getKey().getCity(), scoredCorrectedAddresses.get(0).getKey().getCountry());
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
                    TestObject testObject = new TestObject(null, null, statePair.getKey(), cityPair.getKey(), countryPair.getKey());
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
        for (Map.Entry<String, AbstractLocation> abstractLocation : SolutionUtil.childNameParentMultimap.get(testObject.getCity())) {
            if (abstractLocation.getValue() instanceof State && testObject.getState().equals(abstractLocation.getValue().getName())) {
                for (Map.Entry<String, AbstractLocation> abstractLocation1 : SolutionUtil.childNameParentMultimap.get(testObject.getState())) {
                    if (abstractLocation1.getValue() instanceof Country && testObject.getCountry().equals(abstractLocation1.getValue().getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
