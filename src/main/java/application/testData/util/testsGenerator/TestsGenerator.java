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
    static List<Integer> randomNumberList = new ArrayList<>(6);

    public static void createRandomNumberList() {
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int k = rand.nextInt(6);
            while (randomNumberList.contains(k)) {
                k = rand.nextInt(6);
            }
            randomNumberList.add(k);
        }
        System.out.println(randomNumberList);
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
        String input = testObject.getStreet() + ONE_WHITESPACE + testObject.getZipCode() + ONE_WHITESPACE + testObject.getState() +
                ONE_WHITESPACE + testObject.getCity() + ONE_WHITESPACE + testObject.getCountry() + ONE_WHITESPACE;
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

    public static TestObject getRandomizedAddress(TestObject testObject) {
        String street = null;
        String zipCode = null;
        String state = null;
        String city = null;
        String country = null;
        for (int i = 0; i < randomNumberList.size(); i++) {
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
}
