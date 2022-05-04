package application.dataset.structure;

import application.solution.SolutionUtil;
import application.testData.util.NameVariationsUtil;

public class City extends AbstractLocation {
    final State stateRoot;

    public City(State stateRoot, int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
        this.stateRoot = stateRoot;
        NameVariationsUtil.addAllVariationsOfAnAddress(name, asciiName, alternateNames, stateRoot);
        SolutionUtil.addAlternateNameInMap(name, asciiName, alternateNames);
    }

    @Override
    public AbstractLocation getRoot() {
        return stateRoot;
    }
}
