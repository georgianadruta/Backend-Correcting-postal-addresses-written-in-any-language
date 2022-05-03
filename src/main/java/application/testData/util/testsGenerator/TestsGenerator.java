package application.testData.util.testsGenerator;

import application.testData.model.TestObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static application.constants.ConstantsUtil.*;

/**
 * helpful class to generate multiple cases of wrong filled address
 */
public class TestsGenerator {
    static List<Integer> randomNumberListForAddressesWithAllFieldsFilledIncorrectly = new ArrayList<>(5);
    static List<Integer> randomNumberListForAddressesWithMultipleDataInOneField = new ArrayList<>();
    static int m;

    public static void createRandomNumberListForAddressesWithAllFieldsFilledIncorrectly() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int k = rand.nextInt(5);
            while (randomNumberListForAddressesWithAllFieldsFilledIncorrectly.contains(k)) {
                k = rand.nextInt(5);
            }
            randomNumberListForAddressesWithAllFieldsFilledIncorrectly.add(k);
        }
    }

    public static void createRandomNumberListForAddressesWithMultipleDataInOneField() {
        Random rand = new Random();
        int n = rand.nextInt(5);
        while (n < 2) {
            n = rand.nextInt(5);
        }
        m = rand.nextInt(5);
        for (int i = 0; i < n; i++) {
            int k = rand.nextInt(5);
            while (randomNumberListForAddressesWithMultipleDataInOneField.contains(k) || k == m) {
                k = rand.nextInt(5);
            }
            randomNumberListForAddressesWithMultipleDataInOneField.add(k);
        }
    }

    private static void getNewInput(StringBuilder input, TestObject testObject, Integer integer) {
        switch (integer) {
            case 0 -> {
                input.append(ONE_WHITESPACE).append(testObject.getStreet());
                testObject.setStreet(EMPTY_STRING);
            }
            case 1 -> {
                input.append(ONE_WHITESPACE).append(testObject.getZipCode());
                testObject.setZipCode(EMPTY_STRING);
            }
            case 2 -> {
                input.append(ONE_WHITESPACE).append(testObject.getState());
                testObject.setState(EMPTY_STRING);
            }
            case 3 -> {
                input.append(ONE_WHITESPACE).append(testObject.getCity());
                testObject.setCity(EMPTY_STRING);
            }
            case 4 -> {
                input.append(ONE_WHITESPACE).append(testObject.getCountry());
                testObject.setCountry(EMPTY_STRING);
            }
        }
    }

    public static TestObject getAddressWithTwoDataInGivenField(TestObject testObject, String fieldName) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            switch (fieldName) {
                case STATE -> {
                    copy.setState(testObject.getState() + ONE_WHITESPACE + " iasi");
                }
                case CITY -> {
                    copy.setCity(testObject.getCity() + ONE_WHITESPACE + " tecuci");
                }
                case COUNTRY -> {
                    copy.setCountry(testObject.getCountry() + ONE_WHITESPACE + " germany");
                }
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithAGivenFieldToAnother(TestObject testObject, String fromField, String toField) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.moveAFieldToAnother(fromField, toField);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithoutAGivenField(TestObject testObject, String fieldName) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setTestObjectField(fieldName, EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithAllDataInOneField(TestObject testObject, String fieldName) {
        TestObject copy = new TestObject();
        String input = testObject.getStreet() + ONE_WHITESPACE + testObject.getZipCode() + ONE_WHITESPACE + testObject.getState() + ONE_WHITESPACE + testObject.getCity() + ONE_WHITESPACE + testObject.getCountry() + ONE_WHITESPACE;
        copy.setTestObjectField(fieldName, input);
        return copy;
    }

    public static TestObject getAddressWithAWrongCompletedField(TestObject testObject, String fieldName) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            switch (fieldName) {
                case STATE -> {
                    copy.setState("iasi");
                }
                case CITY -> {
                    copy.setCity("tecuci");
                }
                case COUNTRY -> {
                    copy.setCountry("germany");
                }
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressesWithAllFieldsFilledIncorrectly(TestObject testObject) {
        String street = null;
        String zipCode = null;
        String state = null;
        String city = null;
        String country = null;
        for (int i = 0; i < randomNumberListForAddressesWithAllFieldsFilledIncorrectly.size(); i++) {
            switch (i) {
                case 0 -> {
                    street = testObject.getStreet();
                }
                case 1 -> {
                    zipCode = testObject.getZipCode();
                }
                case 2 -> {
                    state = testObject.getState();
                }
                case 3 -> {
                    city = testObject.getCity();
                }
                case 4 -> {
                    country = testObject.getCountry();
                }
            }
        }
        return new TestObject(street, zipCode, state, city, country);
    }

    public static TestObject getAddressesWithMultipleDataInOneField(TestObject testObject) {
        StringBuilder input = new StringBuilder();
        try {
            TestObject testObject1 = (TestObject) testObject.clone();
            input.append(testObject.getValueForNoField(m));

            for (Integer integer : randomNumberListForAddressesWithMultipleDataInOneField) {
                getNewInput(input, testObject1, integer);
            }
            testObject1.setValueForNoField(String.valueOf(input), m);
            return testObject1;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
