package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.State;
import application.solution.SolutionUtil;

import java.util.Map;
import java.util.Set;

public class DatasetLoader {
    public static void main(String[] args) {
        DataStorage.createDataStorage();
        DataStorage.saveDataStorage();

        Set<Map.Entry<String, AbstractLocation>> multimap = SolutionUtil.childNameParentMultimap.get("Prahova");
        for (Map.Entry<String, AbstractLocation> abstractLocation : multimap)
            System.out.println(abstractLocation.getValue() instanceof State);
    }
}
