package testcase;

import application.dataset.storage.DataStorage;
import application.solution.SolutionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static application.solution.Solution.getNumberOfCorrectAddressesAfterCorrection;

public class SolutionTest {
    List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() {
        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();
    }

    @Test
    public void addressWithTwoDataInGivenFieldTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithTwoDataInGivenField");
        list.add(n);
    }

    @Test
    public void addressWithAGivenFieldToAnotherTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithAGivenFieldToAnother");
        list.add(n);
    }

    @Test
    public void addressWithAllDataInOneFieldTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithoutAGivenField");
        list.add(n);
    }

    @Test
    public void addressWithAWrongCompletedFieldTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithAWrongCompletedField");
        list.add(n);
    }

    @Test
    public void addressWithAllFieldsFilledIncorrectlyTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithAllDataInOneField");
        list.add(n);
    }

    @Test
    public void addressWithMultipleDataInOneFieldTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithMultipleDataInOneField");
        list.add(n);
    }

    @Test
    public void addressWithAlternateNameTest() {
        int n = getNumberOfCorrectAddressesAfterCorrection("getAddressWithAlternateName");
        list.add(n);
    }

//    @Test
//    public void addressWithTypoTest() {
//        System.out.println(getNumberOfCorrectAddressesAfterCorrection("getAddressWithTypo"));
//    }

    @After
    public void displayNumberOfCorrectedAddressesForEachCase() {
        System.out.println(list);
    }
}
