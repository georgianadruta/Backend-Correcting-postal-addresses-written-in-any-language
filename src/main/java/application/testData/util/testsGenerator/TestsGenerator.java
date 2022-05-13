package application.testData.util.testsGenerator;

import application.solution.SolutionUtil;
import application.testData.model.TestObject;
import application.testData.util.NameVariationsUtil;
import lombok.Data;

import java.util.*;

import static application.constants.ConstantsUtil.*;

/**
 * helpful class to generate multiple cases of wrong filled address
 */
@Data
public class TestsGenerator {
    static List<Integer> randomNumberListForAddressesWithAllFieldsFilledIncorrectly = new ArrayList<>(FIELDS_NUMBER);
    static List<Integer> randomNumberListForAddressesWithMultipleDataInOneField = new ArrayList<>();
    static int m;

    public static void createRandomNumberListForAddressesWithAllFieldsFilledIncorrectly() {
        Random rand = new Random();
        for (int i = 0; i < FIELDS_NUMBER; i++) {
            int k = rand.nextInt(FIELDS_NUMBER);
            while (randomNumberListForAddressesWithAllFieldsFilledIncorrectly.contains(k)) {
                k = rand.nextInt(FIELDS_NUMBER);
            }
            randomNumberListForAddressesWithAllFieldsFilledIncorrectly.add(k);
        }
    }

    public static void createRandomNumberListForAddressesWithMultipleDataInOneField() {
        Random rand = new Random();
        int n = rand.nextInt(FIELDS_NUMBER);
        while (n < 2) {
            n = rand.nextInt(FIELDS_NUMBER);
        }
        m = rand.nextInt(FIELDS_NUMBER - 1);
        for (int i = 0; i < n; i++) {
            int k = rand.nextInt(FIELDS_NUMBER);
            while (randomNumberListForAddressesWithMultipleDataInOneField.contains(k) || k == m) {
                k = rand.nextInt(FIELDS_NUMBER);
            }
            randomNumberListForAddressesWithMultipleDataInOneField.add(k);
        }
    }

    private static void updateInput(StringBuilder input, TestObject testObject, Integer integer, String fieldName) {
        switch (integer) {
            case 0 -> {
                if (!fieldName.equals(STREET)) {
                    input.append(ONE_WHITESPACE).append(testObject.getStreet());
                    testObject.setStreet(EMPTY_STRING);
                }
            }
            case 1 -> {
                if (!fieldName.equals(ZIP_CODE)) {
                    input.append(ONE_WHITESPACE).append(testObject.getZipCode());
                    testObject.setZipCode(EMPTY_STRING);
                }
            }
            case 2 -> {
                if (!fieldName.equals(STATE)) {
                    input.append(ONE_WHITESPACE).append(testObject.getState());
                    testObject.setState(EMPTY_STRING);
                }
            }
            case 3 -> {
                if (!fieldName.equals(CITY)) {
                    input.append(ONE_WHITESPACE).append(testObject.getCity());
                    testObject.setCity(EMPTY_STRING);
                }
            }
            case 4 -> {
                if (!fieldName.equals(COUNTRY)) {
                    input.append(ONE_WHITESPACE).append(testObject.getCountry());
                    testObject.setCountry(EMPTY_STRING);
                }
            }
        }
    }

    public static TestObject getAddressWithTwoDataInGivenField(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        switch (fieldNames[0]) {
            case STATE -> copy.setState(testObject.getState() + ONE_WHITESPACE + "iasi");
            case CITY -> copy.setCity(testObject.getCity() + ONE_WHITESPACE + "tecuci");
            case COUNTRY -> copy.setCountry(testObject.getCountry() + ONE_WHITESPACE + "germany");
        }
        return copy;
    }

    public static TestObject getAddressWithAGivenFieldToAnother(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        copy.moveAFieldToAnother(fieldNames[0], fieldNames[1]);
        return copy;
    }

    public static TestObject getAddressWithoutAGivenField(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        copy.setTestObjectField(fieldNames[0], EMPTY_STRING);
        return copy;
    }

    public static TestObject getAddressWithAllDataInOneField(TestObject testObject, String... fieldNames) {
        TestObject copy = new TestObject();
        String input = testObject.getStreet() + ONE_WHITESPACE + testObject.getZipCode() + ONE_WHITESPACE + testObject.getState() + ONE_WHITESPACE + testObject.getCity() + ONE_WHITESPACE + testObject.getCountry() + ONE_WHITESPACE;
        copy.setTestObjectField(fieldNames[0], input);
        return copy;
    }

    public static TestObject getAddressWithAWrongCompletedField(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        switch (fieldNames[0]) {
            case STATE -> copy.setState("iasi");
            case CITY -> copy.setCity("tecuci");
            case COUNTRY -> copy.setCountry("germany");
        }
        return copy;
    }

    public static TestObject getAddressWithAllFieldsFilledIncorrectly(TestObject testObject, String... fieldNames) {
        TestObject testObjectToCorrect = new TestObject();
        for (int i = 0; i < randomNumberListForAddressesWithAllFieldsFilledIncorrectly.size(); i++) {
            setValues(testObjectToCorrect, testObject, i, randomNumberListForAddressesWithAllFieldsFilledIncorrectly.get(i));
        }
        return testObjectToCorrect;
    }

    private static void setValues(TestObject testObjectToCorrect, TestObject testObject, int i, Integer integer) {
        String value = getCorrespondentValue(testObject, integer);
        switch (i) {
            case 0 -> testObjectToCorrect.setStreet(value);
            case 1 -> testObjectToCorrect.setZipCode(value);
            case 2 -> testObjectToCorrect.setState(value);
            case 3 -> testObjectToCorrect.setCity(value);
            case 4 -> testObjectToCorrect.setCountry(value);
        }
    }

    private static String getCorrespondentValue(TestObject testObject, Integer integer) {
        switch (integer) {
            case 0 -> {
                return testObject.getStreet();
            }
            case 1 -> {
                return testObject.getZipCode();
            }
            case 2 -> {
                return testObject.getState();
            }
            case 3 -> {
                return testObject.getCity();
            }
            case 4 -> {
                return testObject.getCountry();
            }
        }
        return null;
    }

    private static String getCorrespondentValue(TestObject testObject, String fieldName) {
        switch (fieldName) {
            case STREET -> {
                return testObject.getStreet();
            }
            case ZIP_CODE -> {
                return testObject.getZipCode();
            }
            case STATE -> {
                return testObject.getState();
            }
            case CITY -> {
                return testObject.getCity();
            }
            case COUNTRY -> {
                return testObject.getCountry();
            }
        }
        return null;
    }

    public static TestObject getAddressWithMultipleDataInOneField(TestObject testObject, String... fieldNames) {
        StringBuilder input = new StringBuilder();
        TestObject testObject1 = (TestObject) testObject.clone();
        input.append(getCorrespondentValue(testObject, fieldNames[0]));

        for (Integer integer : randomNumberListForAddressesWithMultipleDataInOneField) {
            updateInput(input, testObject1, integer, fieldNames[0]);
        }
        testObject1.setValueForGivenField(String.valueOf(input), fieldNames[0]);
        return testObject1;
    }

    public static TestObject getAddressWithAlternateName(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        Set<List<String>> list = getAlternateNameForGivenField(testObject, fieldNames[0]);
        for (List<String> sublist : list) {
            for (String str : sublist) {
                if (setNewValueForGivenField(copy, fieldNames[0], str)) {
                    return copy;
                }
            }
        }
        return testObject;
    }

    private static boolean setNewValueForGivenField(TestObject copy, String fieldName, String str) {
        boolean isModified = false;
        if (fieldName.equals(CITY) && !copy.getCity().equals(str)) {
            copy.setCity(str);
            isModified = true;
        }
        if (fieldName.equals(STATE) && !copy.getState().equals(str)) {
            copy.setState(str);
            isModified = true;
        }
        if (fieldName.equals(COUNTRY) && !copy.getCountry().equals(str)) {
            copy.setCountry(str);
            isModified = true;
        }
        return isModified;
    }

    private static Set<List<String>> getAlternateNameForGivenField(TestObject testObject, String fieldName) {
        if (fieldName.equals(CITY)) {
            return SolutionUtil.nameAlternateNamesMultimap.get(testObject.getCity());
        }
        if (fieldName.equals(STATE)) {
            return SolutionUtil.nameAlternateNamesMultimap.get(testObject.getState());
        }
        if (fieldName.equals(COUNTRY)) {
            return SolutionUtil.nameAlternateNamesMultimap.get(testObject.getCountry());
        }
        return new HashSet<>();
    }

    public static TestObject getAddressWithoutPrepositions(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        List<String> newNames = getNameListWithoutPrepositionsForGivenField(testObject, fieldNames[0]);
        if (!newNames.isEmpty()) {
            int rnd = new Random().nextInt(newNames.size());
            setNewValueForGivenField(copy, fieldNames[0], newNames.get(rnd));
        }
        return copy;
    }

    private static List<String> getNameListWithoutPrepositionsForGivenField(TestObject testObject, String fieldName) {
        switch (fieldName) {
            case CITY -> {
                return NameVariationsUtil.getNamesWithoutPrepositions(List.of(testObject.getCity()));
            }
            case STATE -> {
                return NameVariationsUtil.getNamesWithoutPrepositions(List.of(testObject.getState()));
            }
            case COUNTRY -> {
                return NameVariationsUtil.getNamesWithoutPrepositions(List.of(testObject.getCountry()));
            }
        }
        return new ArrayList<>();
    }

    public static TestObject getAddressWithoutDuplicateCharacters(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        List<String> newNames = getNameListWithoutDuplicateCharactersForGivenField(testObject, fieldNames[0]);
        if (!newNames.isEmpty()) {
            int rnd = new Random().nextInt(newNames.size());
            setNewValueForGivenField(copy, fieldNames[0], newNames.get(rnd));
        }
        return copy;
    }

    private static List<String> getNameListWithoutDuplicateCharactersForGivenField(TestObject testObject, String fieldName) {
        switch (fieldName) {
            case CITY -> {
                return NameVariationsUtil.getNamesWithoutDuplicateCharacters(List.of(testObject.getCity()));
            }
            case STATE -> {
                return NameVariationsUtil.getNamesWithoutDuplicateCharacters(List.of(testObject.getState()));
            }
            case COUNTRY -> {
                return NameVariationsUtil.getNamesWithoutDuplicateCharacters(List.of(testObject.getCountry()));
            }
        }
        return new ArrayList<>();
    }

    public static TestObject getAddressWithoutVowels(TestObject testObject, String... fieldNames) {
        TestObject copy = (TestObject) testObject.clone();
        List<String> newNames = getNameListWithoutVowelsForGivenField(testObject, fieldNames[0]);
        if (!newNames.isEmpty()) {
            int rnd = new Random().nextInt(newNames.size());
            setNewValueForGivenField(copy, fieldNames[0], newNames.get(rnd));
        }
        return copy;
    }

    private static List<String> getNameListWithoutVowelsForGivenField(TestObject testObject, String fieldName) {
        switch (fieldName) {
            case CITY -> {
                return NameVariationsUtil.getAllNamesVariationsWithoutVowels(List.of(testObject.getCity()));
            }
            case STATE -> {
                return NameVariationsUtil.getAllNamesVariationsWithoutVowels(List.of(testObject.getState()));
            }
            case COUNTRY -> {
                return NameVariationsUtil.getAllNamesVariationsWithoutVowels(List.of(testObject.getCountry()));
            }
        }
        return new ArrayList<>();
    }
}
