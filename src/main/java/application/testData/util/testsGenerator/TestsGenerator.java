package application.testData.util.testsGenerator;

import application.testData.model.TestObject;

import static application.constants.ConstantsUtil.EMPTY_STRING;
import static application.constants.ConstantsUtil.ONE_WHITESPACE;

/**
 * helpful class to generate multiple cases of wrong filled address
 */
public class TestsGenerator {
    public static TestObject getAddressWithTwoStates(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setState(testObject.getState() + ONE_WHITESPACE + " iasi");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithTwoCities(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setCity(testObject.getCity() + ONE_WHITESPACE + " tecuci");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithTwoCountries(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setCountry(testObject.getCountry() + ONE_WHITESPACE + " germany");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithStateInStreetField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setStreet(testObject.getStreet() + ONE_WHITESPACE + testObject.getState());
            copy.setState(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithCityInStreetField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setStreet(testObject.getStreet() + ONE_WHITESPACE + testObject.getCity());
            copy.setCity(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithCountryInStreetField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setStreet(testObject.getStreet() + ONE_WHITESPACE + testObject.getCountry());
            copy.setCountry(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithStateInZipCodeField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setZipCode(testObject.getZipCode() + ONE_WHITESPACE + testObject.getState());
            copy.setState(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithCityInZipCodeField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setZipCode(testObject.getZipCode() + ONE_WHITESPACE + testObject.getCity());
            copy.setCity(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public static TestObject getAddressWithCountryInZipCodeField(TestObject testObject) {
        TestObject copy;
        try {
            copy = (TestObject) testObject.clone();
            copy.setZipCode(testObject.getZipCode() + ONE_WHITESPACE + testObject.getCountry());
            copy.setCountry(EMPTY_STRING);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }
}
