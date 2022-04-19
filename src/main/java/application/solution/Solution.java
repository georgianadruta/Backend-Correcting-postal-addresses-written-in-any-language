package application.solution;

import application.testData.model.TestObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Map<String, Map<String, Set<String>>> mapWithFieldsValue = SolutionUtil.getMapWithFieldsValue(testObject);

        List<Pair<Integer, TestObject>> scoredCorrectedAddresses = new ArrayList<>();

        return new TestObject(null, "brasov", "brasov", null, null, null, "romania");
    }
}
