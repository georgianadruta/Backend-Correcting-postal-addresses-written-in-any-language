package application.solution;

import application.dataset.storage.DataStorage;
import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.List;

public class SolutionLoader {
    public static void main(String[] args) {
        // System.out.println(Arrays.toString(SolutionUtil.getCanonicalForm(new String[]{"    hakuna MATATA %^$# nº ? &"})));

        // DataStorage.createDataStorage();
        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimap();


        List<TestObject> list = TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");
        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection(list));
    }
}
