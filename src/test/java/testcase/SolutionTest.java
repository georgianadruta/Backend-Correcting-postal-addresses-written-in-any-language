package testcase;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static application.solution.Solution.getNumberOfCorrectAddressesAfterCorrection;
import static org.junit.Assert.assertEquals;

public class SolutionTest {
    List<Integer> list = new ArrayList<>();
    String ROpath = "./files/test/correctRandomAddresses/RO.txt";

    @Before
    public void setUp() {

//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimaps();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();
    }

    @Test
    public void addressWithTwoDataInGivenFieldTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithTwoDataInGivenField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAGivenFieldToAnotherTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithAGivenFieldToAnother");
        list.add(n);
        assertEquals(97, n);
    }

    @Test
    public void addressWithAllDataInOneFieldTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithoutAGivenField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAWrongCompletedFieldTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithAWrongCompletedField");
        list.add(n);
        assertEquals(98, n);
    }

    @Test
    public void addressWithAllFieldsFilledIncorrectlyTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithAllDataInOneField");
        list.add(n);
        assertEquals(91, n);
    }

    @Test
    public void addressWithAlternateNameTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithAlternateName");
        list.add(n);
        assertEquals(92, n);
    }

    @Test
    public void addressWithMultipleDataInOneFieldTest_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithMultipleDataInOneField");
        list.add(n);
    }

    @Test
    public void addressWithoutPrepositions_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithoutPrepositions");
        list.add(n);
    }

    @Test
    public void addressWithoutDuplicateCharacters_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithoutDuplicateCharacters");
        list.add(n);
    }

    @Test
    public void addressWithoutVowels_RO() {
        int n = getNumberOfCorrectAddressesAfterCorrection(ROpath, "getAddressWithoutVowels");
        list.add(n);
    }

    @After
    public void displayNumberOfCorrectedAddressesForEachCase() {
        System.out.println(list);
    }
}
