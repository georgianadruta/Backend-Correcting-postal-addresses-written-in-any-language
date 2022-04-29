package application.solution;

import application.dataset.storage.DataStorage;
import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.List;

public class SolutionLoader {
    public static void main(String[] args) {
//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimap();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimap();

        List<TestObject> list = TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");

//        list.add(new TestObject(null, "cluj napoca", "cluj napoca", null, null, null, "romania"));
//        list.add(new TestObject(null, "ilfov", "pantelimon", null, null, null, "romania"));

//        list.add(new TestObject("romania", "com bran, podoleni", "iasi brasov",
//                null, null, null, "iasi romania romania"));

        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection(list) + "/" + list.size());
    }
}
