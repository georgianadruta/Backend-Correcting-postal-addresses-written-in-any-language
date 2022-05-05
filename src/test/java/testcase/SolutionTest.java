package testcase;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static application.constants.ConstantsUtil.RO_PATH;
import static application.solution.Solution.getNumberOfCorrectAddressesAfterCorrection;
import static org.junit.Assert.assertEquals;

public class SolutionTest {
    List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() {

//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimaps();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();
    }

    @Test
    public void addressWithTwoDataInGivenField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithTwoDataInGivenField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAGivenFieldToAnother_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAGivenFieldToAnother");
        list.add(n);
        assertEquals(97, n);
    }

    @Test
    public void addressWithAllDataInOneField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutAGivenField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAWrongCompletedField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAWrongCompletedField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAllFieldsFilledIncorrectly_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAllFieldsFilledIncorrectly");
        list.add(n);
        assertEquals(91, n);
    }

    @Test
    public void addressWithAlternateName_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithAlternateName");
        list.add(n);
        assertEquals(92, n);
    }

    @Test
    public void addressWithMultipleDataInOneField_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithMultipleDataInOneField");
        list.add(n);
    }

    @Test
    public void addressWithoutPrepositions_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithoutPrepositions");
        list.add(n);
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
