package application.solution;

import application.testData.model.TestObject;

import static application.constants.ConstantsUtil.*;

public class SolutionUtil {
    public static TestObject getCanonicalForm(TestObject testObject) {
        String street = getCanonicalForm(testObject.getStreet());
        String city = getCanonicalForm(testObject.getStreet());
        String state = getCanonicalForm(testObject.getStreet());
        String phoneNumber = getCanonicalForm(testObject.getStreet());
        String zipCode = getCanonicalForm(testObject.getStreet());
        String countryCallingCode = getCanonicalForm(testObject.getStreet());
        String country = getCanonicalForm(testObject.getStreet());

        return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
    }

    public static String getCanonicalForm(String input) {
        //lowercase
        input = input.toLowerCase();
        //special characters
        return input
                .replaceAll("(\\D)\\.(\\d)", "$1 $2")
                .replaceAll("[!@€£¢$¥%^*\"`~><()\\-.=_;,\\\\+?{}\\[\\]:|\\s]+", ONE_WHITESPACE)
                .replaceAll("[–\\-]+", ONE_WHITESPACE)
                .replaceAll("#+", ONE_WHITESPACE)
                .replaceAll("/+", ONE_WHITESPACE)
                .replaceAll("&+", " and ")
                .replaceAll("º+", "o ") //nº => No
                .replaceAll("[`']+", EMPTY_STRING)
                .replaceAll("[.]+", ONE_WHITESPACE)
                .replaceAll("\\s+", ONE_WHITESPACE);//white space
    }

    public static boolean isPhoneNumberOrZipCodeOrCountryCallingCode(String input) {
        return !input.contains("[a-zA-Z]+");
    }
}
