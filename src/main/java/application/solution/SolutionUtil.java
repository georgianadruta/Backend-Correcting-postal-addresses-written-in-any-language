package application.solution;

import application.dataset.structure.AbstractLocation;
import application.testData.model.TestObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static application.constants.ConstantsUtil.*;

public class SolutionUtil {
    public static ListMultimap<String, AbstractLocation> multimap = ArrayListMultimap.create(); // va fi de ajutor in cautarea solutiilor
    // caut in multimap dupa numele din adresa sa vad unde se gaseste acel nume, in ce country/state

    public static TestObject getCanonicalFormTestObject(TestObject testObject) {
        String street = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String city = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String state = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String phoneNumber = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String zipCode = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String countryCallingCode = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));
        String country = Arrays.toString(getCanonicalForm(new String[]{testObject.getStreet()}));

        return new TestObject(street, city, state, phoneNumber, zipCode, countryCallingCode, country);
    }

    public static String[] getCanonicalForm(String[] inputList) {
        List<String> newList = new ArrayList<>();
        for (String input : inputList) {
            //lowercase
            input = input.toLowerCase();
            //special characters
            newList.add(input
                    .replaceAll("(\\D)\\.(\\d)", "$1 $2")
                    .replaceAll("[!@€£¢$¥%^*\"`~><()\\-.=_;,\\\\+?{}\\[\\]:|\\s]+", ONE_WHITESPACE)
                    .replaceAll("[–\\-]+", ONE_WHITESPACE)
                    .replaceAll("#+", ONE_WHITESPACE)
                    .replaceAll("/+", ONE_WHITESPACE)
                    .replaceAll("&+", " and ")
                    .replaceAll("º+", "o ") //nº => No
                    .replaceAll("[`']+", EMPTY_STRING)
                    .replaceAll("[.]+", ONE_WHITESPACE)
                    .replaceAll("\\s+", ONE_WHITESPACE)//white space
                    .trim());
        }
        return newList.toArray(new String[0]);
    }

    public static boolean isPhoneNumberOrZipCodeOrCountryCallingCode(String input) {
        return !input.contains("[a-zA-Z]+");
    }
}
