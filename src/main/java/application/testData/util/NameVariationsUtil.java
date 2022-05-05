package application.testData.util;

import application.dataset.structure.AbstractLocation;
import application.solution.SolutionUtil;

import java.util.*;

import static application.constants.ConstantsUtil.EMPTY_STRING;
import static application.constants.ConstantsUtil.VOWELS;

/**
 * helpful class to create more names for a better precision
 */
public class NameVariationsUtil {
    /**
     * helpful method to add in multimap the pairs [possible name, the corresponding node from tree]
     */
    public static void addAllVariationsOfAnAddress(String name, String asciiName, String[] alternateNames, AbstractLocation abstractLocation) {
        addAlternateNamesInMap(new String[]{name, asciiName}, name, abstractLocation);
        addAlternateNamesInMap(alternateNames, name, abstractLocation);
        String[] strings = getMoreAlternateNames(name, asciiName, alternateNames);
        addAlternateNamesInMap(strings, name, abstractLocation);
    }

    /**
     * add in multimap each alternate name as key with the corresponding node from tree
     * note: the countries do not have parents -> parent = null
     */
    public static void addAlternateNamesInMap(String[] alternateNames, String childName, AbstractLocation parent) {
        for (String alternateName : alternateNames) {
            SolutionUtil.childNameParentMultimap.put(alternateName, new AbstractMap.SimpleEntry<>(childName, parent));
        }
    }

    /**
     * helpful method to create more variations of names
     * like names without prepositions, names without duplicate characters, names without vowels
     */
    public static String[] getMoreAlternateNames(String name, String asciiName, String[] alternateNames) {
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>(List.of(alternateNames));
        list.addAll(List.of(name, asciiName));

        set.addAll(getNamesWithoutPrepositions(list));
        set.addAll(getNamesWithoutDuplicateCharacters(list));
        set.addAll(getAllNamesVariationsWithoutVowels(list));
        return set.toArray(new String[0]);
    }

    /**
     * helpful method to return the list of names after removing duplicate characters
     */
    public static List<String> getNamesWithoutDuplicateCharacters(List<String> givenList) {
        List<String> list = new ArrayList<>();
        for (String name : givenList) {
            String str = removeDuplicateCharacters(name);
            if (str != null)
                list.add(str);
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
                allNamesVariationsWithoutVowels.add(alternateName.replaceAll(regex, EMPTY_STRING));
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
            addNonNullElement(newNameList, removePreposition(name));
        }
        return newNameList;
    }

    /**
     * helpful method to prevent the adding of null elements
     */
    public static void addNonNullElement(List<String> list, List<String> inputList) {
        if (inputList != null) {
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
        boolean sw = false;
        for (String word : words) {
            if (word.length() <= 3) {
                sw = true;
                newWords.add(input.replace(word, EMPTY_STRING));
                input = input.replace(word, EMPTY_STRING);
            }
        }
        newWords.add(input);
        return sw ? newWords : null;
    }
}
