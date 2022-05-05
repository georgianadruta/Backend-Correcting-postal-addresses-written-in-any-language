package application.solution;

import application.dataset.storage.DataStorage;

import java.util.Date;

import static application.constants.ConstantsUtil.RO_PATH;

public class SolutionLoader {
    public static void main(String[] args) {
        Date startDate = new Date();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();

        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection(RO_PATH, "getAddressWithTwoDataInGivenField") + "/100");

        Date endDate = new Date();
        int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        System.out.println(numSeconds);
    }
}
