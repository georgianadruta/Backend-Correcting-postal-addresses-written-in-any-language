package application.solution;

import application.dataset.storage.DataStorage;

import java.util.Date;

public class SolutionLoader {
    public static void main(String[] args) {
        Date startDate = new Date();// contorizare timp

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimaps();

        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection("./files/test/correctRandomAddresses/RO.txt", "getAddressWithTwoDataInGivenField") + "/100");

        Date endDate = new Date();
        int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
        System.out.println(numSeconds); //afisare timp creare db
    }
}
