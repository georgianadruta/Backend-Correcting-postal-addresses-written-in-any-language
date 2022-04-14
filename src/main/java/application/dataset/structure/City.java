package application.dataset.structure;

public class City extends AbstractLocation {
    final State stateRoot;

    public City(State stateRoot, int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
//    float latitude, float longitude,
//                String featureClass, String featureCode, String code, String cc2, String admin1, String admin2, String admin3,
//                String admin4, float population, String deviation, String dem, String timezone, String modificationDate) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
        addAlternateNamesInMap(getNamesWithoutPrepositions(name, asciiName, alternateNames), stateRoot);
        addAlternateNamesInMap(new String[]{name}, stateRoot);
        addAlternateNamesInMap(new String[]{asciiName}, stateRoot);
        addAlternateNamesInMap(alternateNames, stateRoot);
        this.stateRoot = stateRoot;
//        latitude, longitude,
//                featureClass, featureCode, code, cc2, admin1, admin2, admin3,
//                admin4, population, deviation, dem, timezone, modificationDate);
    }

    @Override
    public AbstractLocation getRoot() {
        return null;
    }
}
