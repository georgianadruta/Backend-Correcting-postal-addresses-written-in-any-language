package application.solution;

import application.dataset.storage.DataStorage;
import application.testData.model.TestObject;
import application.testData.util.TestUtil;

import java.util.ArrayList;
import java.util.List;

public class SolutionLoader {
    public static void main(String[] args) {
//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimap();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimap();

        List<TestObject> list = TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");

//        list.add(new TestObject(null, "slatina", "slatina", null, null, null, "romania"));
//        list.add(new TestObject(null, "oravita", "caras severin", null, null, null, "romania"));

//        list.add(new TestObject("romania", "com bran, podoleni", " iasi brasov",
//                null, null, null, "iasi romania romania"));
        List<TestObject> testObjectList = new ArrayList<>();
        testObjectList.add(list.get(24));
        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection(testObjectList) + "/" + list.size());
    }
}
