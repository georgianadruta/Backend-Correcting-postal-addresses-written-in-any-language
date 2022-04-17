package application.solution;

import application.dataset.structure.AbstractLocation;
import application.testData.model.TestObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static application.constants.ConstantsUtil.*;

public class SolutionUtil {
    /**
     * helpful data structure for searching the solution
     * we have a better complexity for searching starting with the lowest level
     * we'll get the corresponding node from tree with searched the name(complexity O(1))
     */
    public static ListMultimap<String, AbstractLocation> multimap = ArrayListMultimap.create();

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

    /**
     * helpful method to increase the precision of the algorithm
     * transform input in lowercase, remove special characters, transform nº in No and remove multiple white spaces
     */
    public static String[] getCanonicalForm(String[] inputList) {
        List<String> newList = new ArrayList<>();
        for (String input : inputList) {
            input = input.toLowerCase();
            newList.add(input
                    .replaceAll("(\\D)\\.(\\d)", "$1 $2")
                    .replaceAll("[!@€£¢$¥%^*\"`~><()\\-.=_;,\\\\+?{}\\[\\]:|\\s]+", ONE_WHITESPACE)
                    .replaceAll("[–\\-]+", ONE_WHITESPACE)
                    .replaceAll("#+", ONE_WHITESPACE)
                    .replaceAll("/+", ONE_WHITESPACE)
                    .replaceAll("&+", " and ")
                    .replaceAll("º+", "o ")
                    .replaceAll("[`']+", EMPTY_STRING)
                    .replaceAll("[.]+", ONE_WHITESPACE)
                    .replaceAll("\\s+", ONE_WHITESPACE)
                    .trim());
        }
        return newList.toArray(new String[0]);
    }

    /**
     * check if the input is a phone number/zip code/country calling code
     * with other words the input does not have letters
     */
    public static boolean isPhoneNumberOrZipCodeOrCountryCallingCode(String input) {
        return !input.contains("[a-zA-Z]+");
    }

    public static void saveMultimap() {
        try {
            FileOutputStream fileOut = new FileOutputStream(SERIALIZED_MAP_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(multimap);
            out.close();
            fileOut.close();
            System.out.println("Serialized multimap is saved at:" + SERIALIZED_MAP_PATH);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void loadMultimap() {
        try {
            FileInputStream fileIn = new FileInputStream(SERIALIZED_MAP_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            multimap = (ListMultimap<String, AbstractLocation>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
