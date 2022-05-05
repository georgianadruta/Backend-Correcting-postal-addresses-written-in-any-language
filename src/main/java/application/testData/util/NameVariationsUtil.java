package application.testData.util;

import application.dataset.structure.AbstractLocation;
import application.solution.SolutionUtil;

import java.util.*;

import static application.constants.ConstantsUtil.*;

/**
 * helpful class to create more names for a better precision
 */
public class NameVariationsUtil {
    /**
     * helpful method to add in multimap the pairs [possible name, the corresponding node from tree]
     */
    public static void addAllVariationsOfAnAddress(String name, String asciiName, List<String> alternateNames, AbstractLocation abstractLocation) {
        addAlternateNamesInMap(List.of(name, asciiName), name, abstractLocation);
        addAlternateNamesInMap(alternateNames, name, abstractLocation);
        addAlternateNamesInMap(getMoreAlternateNames(name, asciiName, alternateNames), name, abstractLocation);
    }

    /**
     * add in multimap each alternate name as key with the corresponding node from tree
     * note: the countries do not have parents -> parent = null
     */
    public static void addAlternateNamesInMap(List<String> alternateNames, String childName, AbstractLocation parent) {
        for (String alternateName : alternateNames) {
            SolutionUtil.childNameParentMultimap.put(alternateName, new AbstractMap.SimpleEntry<>(childName, parent));
        }
    }

    /**
     * helpful method to create more variations of names
     * like names without prepositions, names without duplicate characters, names without vowels
     */
    public static List<String> getMoreAlternateNames(String name, String asciiName, List<String> alternateNames) {
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>(alternateNames);
        list.addAll(List.of(name, asciiName));

        set.addAll(getNamesWithoutPrepositions(list));
        set.addAll(getNamesWithoutDuplicateCharacters(list));
        set.addAll(getAllNamesVariationsWithoutVowels(list));
        return new ArrayList<>(set);
    }

    /**
     * helpful method to return the list of names after removing duplicate characters
     */
    public static List<String> getNamesWithoutDuplicateCharacters(List<String> givenList) {
        List<String> list = new ArrayList<>();
        for (String name : givenList) {
            String str = removeDuplicateCharacters(name);
            if (str != null) list.add(String.valueOf(getStringWithoutMultipleWhitespaces(List.of(str))));
        }
        return list;
    }

    /**
     * helpful method to return the given input without duplicate characters
     */
    public static String removeDuplicateCharacters(String input) {
        char[] characterList = input.toCharArray();
        int n = characterList.length;
        if (n < 2) {
            return null;
        }
        int j = 0;
        for (int i = 1; i < n; i++) {
            if (characterList[j] != characterList[i]) {
                j++;
                characterList[j] = characterList[i];
            }
        }
        return String.valueOf(Arrays.copyOfRange(characterList, 0, j + 1));
    }

    /**
     * helpful method to get the list of names without vowels
     */
    public static List<String> getAllNamesVariationsWithoutVowels(List<String> givenList) {
        Set<String> allNamesVariationsWithoutVowels = new HashSet<>();
        List<String> vowelSubset = getAllSubsetsFromAString(VOWELS);
        for (String vowels : vowelSubset) {
            String regex = "[" + vowels + "]";
            for (String alternateName : givenList) {
                allNamesVariationsWithoutVowels.add(String.valueOf(getStringWithoutMultipleWhitespaces(List.of(alternateName.replaceAll(regex, EMPTY_STRING)))));
            }
        }
        return new ArrayList<>(allNamesVariationsWithoutVowels);
    }

    /**
     * helpful method to create the all possibilities of names without some vowels
     */
    public static List<String> getAllSubsetsFromAString(String input) {
        int len = input.length();
        List<String> arr = new ArrayList<>(len * (len + 1) / 2);
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                arr.add(input.substring(i, j + 1));
            }
        }
        return arr;
    }

    /**
     * helpful method to get the list of names after removing the prepositions
     */
    public static List<String> getNamesWithoutPrepositions(List<String> givenList) {
        List<String> newNameList = new ArrayList<>();
        for (String name : givenList) {
            addNonNullElement(newNameList, getStringWithoutMultipleWhitespaces(Objects.requireNonNull(removePreposition(name))));
        }
        return newNameList;
    }

    /**
     * helpful method to prevent the adding of null elements
     */
    public static void addNonNullElement(List<String> list, List<String> inputList) {
        if (inputList != null && !inputList.isEmpty()) {
            for (String input : inputList) {
                if (input != null) {
                    list.add(input);
                }
            }
        }
    }

    /**
     * helpful method to get the all variations of name without some prepositions
     * eg: din, the, de, pe, le, la, l'
     */
    public static List<String> removePreposition(String input) {
        List<String> newWords = new ArrayList<>();
        String[] words = input.split("\\s+");
        for (String word : words) {
            if (word.length() <= 3) {
                List<String> list = getStringWithoutMultipleWhitespaces(List.of(input.replace(word, EMPTY_STRING)));
                if (!list.isEmpty()) {
                    input = list.get(0);
                    newWords.add(input);
                }
            }
        }
        newWords.add(input);
        return newWords;
    }

    private static List<String> getStringWithoutMultipleWhitespaces(List<String> nameList) {
        List<String> newList = new ArrayList<>();
        for (String name : nameList) {
            newList.add(name.trim().replaceAll(MULTIPLE_WHITESPACES, ONE_WHITESPACE));
        }
        return newList;
    }
}
