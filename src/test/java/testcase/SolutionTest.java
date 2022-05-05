package testcase;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import application.testData.util.testsGenerator.TestsGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.constants.ConstantsUtil.*;
import static application.solution.Solution.getNumberOfCorrectAddressesAfterCorrection;

public class SolutionTest {
    List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() {

//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimaps();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();

        TestsGenerator.createRandomNumberListForAddressesWithAllFieldsFilledIncorrectly();
        TestsGenerator.createRandomNumberListForAddressesWithMultipleDataInOneField();
    }

    @Test
    public void addressWithTwoDataInGivenField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithTwoDataInGivenField", STREET);
        list.add(n);
    }

    @Test
    public void addressWithAGivenFieldToAnother_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAGivenFieldToAnother", STREET, CITY);
        list.add(n);
    }

    @Test
    public void addressWithAllDataInOneField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutAGivenField", STREET);
        list.add(n);
    }

    @Test
    public void addressWithAWrongCompletedField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAWrongCompletedField", STREET);
        list.add(n);
    }

    @Test
    public void addressWithAllFieldsFilledIncorrectly_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAllFieldsFilledIncorrectly");
        list.add(n);
    }

    @Test
    public void addressWithAlternateName_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAlternateName");
        list.add(n);
    }

    @Test
    public void addressWithMultipleDataInOneField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithMultipleDataInOneField");
        list.add(n);
    }

    @Test
    public void addressWithoutPrepositions_RO() {
        Date startDate = new Date();
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutPrepositions");
        list.add(n);
        Date endDate = new Date();
        int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        System.out.println(numSeconds);
    }

    @Test
    public void addressWithoutDuplicateCharacters_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutDuplicateCharacters");
        list.add(n);
    }

    @Test
    public void addressWithoutVowels_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutVowels");
        list.add(n);
    }

    @After
    public void displayNumberOfCorrectedAddressesForEachCase() {
        System.out.println(list);
    }
}
