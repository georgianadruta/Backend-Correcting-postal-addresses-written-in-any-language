package application.testData.invalidRandomAddresses.medium.done;

import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.Arrays;
import java.util.List;

/**
 * helpful class to create addresses with multiple data in several fields
 */
public class RandomAddressesWithMultipleInfoInSeveralFields {
    /**
     * helpful method to build the address
     */
    public static TestObject getAddress(TestObject testObject) {
        List<String> list = Arrays.asList(testObject.getStreet(), testObject.getCity(), testObject.getState(),
                testObject.getPhoneNumber(), testObject.getZipCode(), testObject.getCountryCallingCode(), testObject.getCountry());
        String street = null;
        String city = null;
        String state = null;
        String phoneNumber = null;
        String zipCode = null;
        String countryCallingCode = null;
        String country = null;
        for (String s : list) {
            int n = TestUtil.getRandomNumber();
            switch (n) {
                case 0 -> street = getNewValue(street, s);
                case 1 -> city = getNewValue(city, s);
                case 2 -> state = getNewValue(state, s);
                case 3 -> phoneNumber = getNewValue(phoneNumber, s);
                case 4 -> zipCode = getNewValue(zipCode, s);
                case 5 -> countryCallingCode = getNewValue(countryCallingCode, s);
                case 6 -> country = getNewValue(country, s);
                default -> {
                }
            }
        }
        return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
    }

    /**
     * helpful method to concatenate two strings
     */
    private static String getNewValue(String fieldValue, String valueToAdd) {
        if (valueToAdd != null) {
            if (fieldValue == null) {
                fieldValue = valueToAdd;
            } else {
                fieldValue = fieldValue + ", " + valueToAdd;
            }
        }
        return fieldValue;
    }
}
