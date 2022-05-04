package application.dataset.storage;

import application.dataset.structure.AbstractLocation;
import application.dataset.structure.State;
import application.solution.SolutionUtil;

import java.util.Map;
import java.util.Set;

public class DatasetLoader {
    public static void main(String[] args) {
//        Date startDate = new Date();// contorizare timp

//        DataStorage.addAllCountriesInToDoFile(); //metoda ajutatoare pt a adauga toate tarile
        DataStorage.createDataStorage();
        DataStorage.saveDataStorage();
//        for (AbstractLocation abstractLocation : DataStorage.abstractLocationSet) {
//            System.out.println(DataStorage.calculateNumberOfLocationsInWorld(abstractLocation));
//        }

        Set<Map.Entry<String, AbstractLocation>> multimap = SolutionUtil.childNameParentMultimap.get("Prahova");
        for (Map.Entry<String, AbstractLocation> abstractLocation : multimap)
            System.out.println(abstractLocation.getValue() instanceof State);

//        for (AbstractLocation abstractLocation : DataStorage.world.getSubRegions()) {
//            for (AbstractLocation location : abstractLocation.getSubRegions()) {
//                if (location.getName().equals("Galaţi")) {
//                    for (AbstractLocation subLocation : location.getSubRegions()) {
//                        if (subLocation.getName().equals("Podoleni")) {
//                            System.out.println(subLocation);
//                        }
//                    }
//                }
//            }
//        } //metoda pt a verifica daca s a creat corect dbul. nu s au omis date

//        Date endDate = new Date();
//        int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
//        System.out.println(numSeconds); //afisare timp creare db
    }
}
