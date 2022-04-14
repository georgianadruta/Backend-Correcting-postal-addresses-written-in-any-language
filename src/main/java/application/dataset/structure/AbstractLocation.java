package application.dataset.structure;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public abstract class AbstractLocation implements Serializable {
    protected final int geoNameId;
    protected final String name;
    protected final String asciiName;
    protected final List<String> alternateNames;
    //    protected final float latitude;
//    protected final float longitude;
//    protected final String featureClass;
//    protected final String featureCode;
    protected final String code;
    //    protected final String cc2;
    protected final String admin1;
    //    protected final String admin2;
//    protected final String admin3;
//    protected final String admin4;
//    protected final float population;
//    protected final String deviation;
//    protected final String dem;
//    protected final String timezone;
//    protected final String modificationDate;
    protected Set<AbstractLocation> subRegions;

    public abstract AbstractLocation getRoot();

    public AbstractLocation(int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
//                            float latitude, float longitude, String featureClass, String featureCode, String code, String cc2, String admin1,
//                            String admin2, String admin3, String admin4, float population, String deviation, String dem, String timezone, String modificationDate) {
        this.geoNameId = geoNameId;
        this.name = name;
        this.asciiName = asciiName;
        this.alternateNames = List.of(alternateNames);
        this.code = code;
        this.admin1 = admin1;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.featureClass = featureClass;
//        this.featureCode = featureCode;
//        this.code = code;
//        this.cc2 = cc2;
//        this.admin1 = admin1;
//        this.admin2 = admin2;
//        this.admin3 = admin3;
//        this.admin4 = admin4;
//        this.population = population;
//        this.deviation = deviation;
//        this.dem = dem;
//        this.timezone = timezone;
//        this.modificationDate = modificationDate;
        this.subRegions = new HashSet<>();
    }

    /**
     * add subregion if it's not already added
     *
     * @param abstractLocation the given subregion to add
     */
    public void addSubRegion(AbstractLocation abstractLocation) {
        if (!DataStorage.foundGeoNameIds.contains(abstractLocation.geoNameId)) {
            subRegions.add(abstractLocation);
            DataStorage.foundGeoNameIds.add(geoNameId);
        }
    }

    public void addAllVariationsOfAnAddress(String name, String asciiName, String[] alternateNames, AbstractLocation abstractLocation) {
        addAlternateNamesInMap(new String[]{name, asciiName}, abstractLocation);
        addAlternateNamesInMap(alternateNames, abstractLocation);
        addAlternateNamesInMap(getMoreAlternateNames(name, asciiName, alternateNames), abstractLocation);
    }

    public void addAlternateNamesInMap(String[] alternateNames, AbstractLocation parent) { // tarile au ca parinte valoarea null
        for (String alternateName : alternateNames) {
            SolutionUtil.multimap.put(alternateName, parent);
        }
    }

    public String[] getMoreAlternateNames(String name, String asciiName, String[] alternateNames) {
        Set<String> set = new HashSet<>();
        set.addAll(getNamesWithoutPrepositions(name, asciiName, alternateNames));
        set.addAll(getNamesWithoutDuplicateCharacters(name, asciiName, alternateNames));
        set.addAll(getAllNamesVariationsWithoutVowels(name, asciiName, alternateNames));
        return set.toArray(new String[0]);
    }

    public List<String> getNamesWithoutDuplicateCharacters(String name, String asciiName, String[] alternateNames) {
        List<String> list = new ArrayList<>();
        list.add(removeDuplicateCharacters(name));
        list.add(removeDuplicateCharacters(asciiName));
        for (String alternateName : alternateNames) {
            list.add(removeDuplicateCharacters(alternateName));
        }
        return list;
    }

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

    public static List<String> getAllNamesVariationsWithoutVowels(String name, String asciiName, String[] alternateNames) {
        Set<String> allNamesVariationsWithoutVowels = new HashSet<>();
        List<String> vowelSubset = getAllSubsetsFromAString("aeiou");
        for (String vowels : vowelSubset) {
            String regex = "[" + vowels + "]";
            allNamesVariationsWithoutVowels.add(name.replaceAll(regex, ""));
            allNamesVariationsWithoutVowels.add(asciiName.replaceAll(regex, ""));
            for (String alternateName : alternateNames) {
                allNamesVariationsWithoutVowels.add(alternateName.replaceAll(regex, ""));
            }
        }
        return new ArrayList<>(allNamesVariationsWithoutVowels);
    }

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

    public List<String> getNamesWithoutPrepositions(String name, String asciiName, String[] alternateNames) {
        List<String> newNameList = new ArrayList<>();
        addNonNullElement(newNameList, removePreposition(name));
        addNonNullElement(newNameList, removePreposition(asciiName));
        for (String alternateName : alternateNames) {
            addNonNullElement(newNameList, removePreposition(alternateName));
        }
        return newNameList;
    }

    public static void addNonNullElement(List<String> list, List<String> inputList) {
        if (inputList != null) {
            for (String input : inputList) {
                if (input != null) {
                    list.add(input);
                }
            }
        }
    }

    public List<String> removePreposition(String input) {
        List<String> newWords = new ArrayList<>();
        String[] words = input.split("\\s+");
        boolean sw = false;
        for (String word : words) {
            if (word.length() <= 3) { // din, the, de, pe, le, la, l'
                sw = true;
                newWords.add(input.replace(word, ""));
                input = input.replace(word, "");
            }
        }
        newWords.add(input);
        return sw ? newWords : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLocation location = (AbstractLocation) o;
        return geoNameId == location.geoNameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(geoNameId);
    }

    @Override
    public String toString() {
        return "AbstractLocation{" +
                "geoNameId=" + geoNameId +
                ", name='" + name + '\'' +
                ", asciiName='" + asciiName + '\'' +
                ", alternateNames=" + alternateNames +
//                ", latitude=" + latitude +
//                ", longitude=" + longitude +
//                ", featureClass='" + featureClass + '\'' +
//                ", featureCode='" + featureCode + '\'' +
                ", code='" + code + '\'' +
//                ", cc2='" + cc2 + '\'' +
//                ", admin1='" + admin1 + '\'' +
//                ", admin2='" + admin2 + '\'' +
//                ", admin3='" + admin3 + '\'' +
//                ", admin4='" + admin4 + '\'' +
//                ", population=" + population +
//                ", deviation='" + deviation + '\'' +
//                ", dem='" + dem + '\'' +
//                ", timezone='" + timezone + '\'' +
//                ", modificationDate='" + modificationDate + '\'' +
                ", subRegions=" + subRegions +
                '}';
    }
}
