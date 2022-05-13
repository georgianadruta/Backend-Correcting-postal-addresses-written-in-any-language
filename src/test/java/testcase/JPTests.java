package testcase;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import application.testData.util.testsGenerator.TestsGenerator;
import org.junit.Before;
import org.junit.Test;

import static application.constants.ConstantsUtil.*;
import static application.solution.Solution.getNumberOfCorrectAddressesAfterCorrection;

public class JPTests {

    @Before
    public void setUp() {
//        Date startDate = new Date();
//
//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimaps();

//        Date endDate = new Date();
//        int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
//        System.out.println(numSeconds);

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();

        TestsGenerator.createRandomNumberListForAddressesWithAllFieldsFilledIncorrectly();
        TestsGenerator.createRandomNumberListForAddressesWithMultipleDataInOneField();
    }

    @Test
    public void correctAddress() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getCorrectAddress"));
    }

    @Test
    public void addressWithTwoDataInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithTwoDataInGivenField", STATE));
    }

    @Test
    public void addressWithTwoDataInCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithTwoDataInGivenField", CITY));
    }

    @Test
    public void addressWithTwoDataInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithTwoDataInGivenField", COUNTRY));
    }

    @Test
    public void addressWithStreetInZipCodeField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", STREET, ZIP_CODE));
    }

    @Test
    public void addressWithStreetInCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", STREET, CITY));
    }

    @Test
    public void addressWithStreetInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", STREET, STATE));
    }

    @Test
    public void addressWithStreetInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", STREET, COUNTRY));
    }

    @Test
    public void addressWithZipCodeInCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", ZIP_CODE, CITY));
    }

    @Test
    public void addressWithZipCodeInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", ZIP_CODE, STATE));
    }

    @Test
    public void addressWithZipCodeInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", ZIP_CODE, COUNTRY));
    }

    @Test
    public void addressWithCityInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", CITY, STATE));
    }

    @Test
    public void addressWithCityInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", CITY, COUNTRY));
    }

    @Test
    public void addressWithStateInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAGivenFieldToAnother", STATE, COUNTRY));
    }

    @Test
    public void addressWithAllDataInStreetField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllDataInOneField", STREET));
    }

    @Test
    public void addressWithAllDataInZipCodeField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllDataInOneField", ZIP_CODE));
    }

    @Test
    public void addressWithAllDataInCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllDataInOneField", CITY));
    }

    @Test
    public void addressWithAllDataInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllDataInOneField", STATE));
    }

    @Test
    public void addressWithAllDataInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllDataInOneField", COUNTRY));
    }

    @Test
    public void addressWithWrongCompletedStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAWrongCompletedField", STATE));
    }

    @Test
    public void addressWithWrongCompletedCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAWrongCompletedField", CITY));
    }

    @Test
    public void addressWithWrongCompletedCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAWrongCompletedField", COUNTRY));
    }

    @Test
    public void addressWithAllFieldsFilledIncorrectly() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAllFieldsFilledIncorrectly"));
    }

    @Test
    public void addressWithAlternateNameState() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAlternateName", STATE));
    }

    @Test
    public void addressWithAlternateNameCity() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAlternateName", CITY));
    }

    @Test
    public void addressWithAlternateNameCountry() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithAlternateName", COUNTRY));
    }

    @Test
    public void addressWithMultipleDataInStreetField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithMultipleDataInOneField", STREET));
    }

    @Test
    public void addressWithMultipleDataInZipCodeField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithMultipleDataInOneField", ZIP_CODE));
    }

    @Test
    public void addressWithMultipleDataInStateField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithMultipleDataInOneField", STATE));
    }

    @Test
    public void addressWithMultipleDataInCityField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithMultipleDataInOneField", CITY));
    }

    @Test
    public void addressWithMultipleDataInCountryField() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithMultipleDataInOneField", COUNTRY));
    }

    @Test
    public void addressCityWithoutPrepositions() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutPrepositions", CITY));
    }

    @Test
    public void addressStateWithoutPrepositions() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutPrepositions", STATE));
    }

    @Test
    public void addressCountryWithoutPrepositions() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutPrepositions", COUNTRY));
    }

    @Test
    public void addressCityWithoutDuplicateCharacters() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutDuplicateCharacters", CITY));
    }

    @Test
    public void addressStateWithoutDuplicateCharacters() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutDuplicateCharacters", STATE));
    }

    @Test
    public void addressCountryWithoutDuplicateCharacters() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutDuplicateCharacters", COUNTRY));
    }

    @Test
    public void addressCityWithoutVowels() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutVowels", CITY));
    }

    @Test
    public void addressStateWithoutVowels() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutVowels", STATE));
    }

    @Test
    public void addressCountryWithoutVowels() {
        System.out.println(getNumberOfCorrectAddressesAfterCorrection(JP_PATH, "getAddressWithoutVowels", COUNTRY));
    }
}
