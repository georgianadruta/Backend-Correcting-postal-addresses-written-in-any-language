package application.dataset.storage;

import application.dataset.structure.AbstractLocation;

import java.util.Date;

public class DatasetLoader {
    public static void main(String[] args) {
//        Date startDate = new Date();


        DataStorage.addAllCountriesInToDoFile();
        DataStorage.createDataStorage();
        System.out.println(DataStorage.world.toString());
        System.out.println("The world object contains: " + DataStorage.calculateNumberOfLocationsInWorld(DataStorage.world) + " locations.");

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
//        }

//        Date endDate = new Date();
//
//        int numSeconds = (int)((endDate.getTime() - startDate.getTime()) / 1000);
//        System.out.println(numSeconds);
    }
}
