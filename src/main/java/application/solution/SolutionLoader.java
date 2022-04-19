package application.solution;

import application.dataset.storage.DataStorage;
import application.testData.model.TestObject;

import java.util.ArrayList;
import java.util.List;

public class SolutionLoader {
    public static void main(String[] args) {
//        DataStorage.createDataStorage();
//        DataStorage.saveDataStorage();
//        SolutionUtil.saveMultimap();

        DataStorage.loadDataStorage();
        SolutionUtil.loadMultimap();

//                City: slatina,
//                State: slatina => slatina, olt. e mai prioritar o resedinta de judet

        List<TestObject> list = new ArrayList<>(); //TestUtil.readFromSerializedFile("./files/test/correctRandomAddresses/RO.ser");
//        list.add(new TestObject(null, "slatina", "slatina", null, null, null, "romania"));
//        list.add(new TestObject(null, "oravita", "caras severin", null, null, null, "romania"));
        list.add(new TestObject(null, "com bran podoleni", "brasov iasi", null, null, null, "romania iasi"));
//        List<String> names = new ArrayList<String>();
//        SolutionUtil.multimap.get("bran").forEach(a -> names.add(a.getName()));
//        System.out.println(names);
        System.out.println(Solution.getNumberOfCorrectAddressesAfterCorrection(list));
    }
}
