package application.dataset.structure;

import application.solution.SolutionUtil;
import application.testData.util.NameVariationsUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static application.constants.ConstantsUtil.*;

@Getter
@Setter
public class State extends AbstractLocation {
    final Country countryRoot;

    public State(Country countryRoot, int geoNameId, String name, String asciiName, List<String> alternateNames, String code, String admin1) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
        this.countryRoot = countryRoot;
    }

    /**
     * helpful method to add all the cities from the current state from country file
     */
    public void addCity(String filePath, State stateRoot) {
        String stateCode = this.getCode();
        String stateAdmin1 = this.getAdmin1();
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String dataFromFile = reader.nextLine();
                String[] splitData = dataFromFile.split(TAB);
                int geoNameId = Integer.parseInt(splitData[0]);
                String name = splitData[1];
                String asciiName = splitData[2];
                List<String> alternateNames = List.of(splitData[3].split(SEPARATOR_DB_FILES));
                String featureClass = splitData[6];
                String code = splitData[8];
                String admin1 = splitData[10];
                if (code.equals(stateCode) && admin1.equals(stateAdmin1) && (featureClass.equals(P_CODE) || featureClass.equals(A_CODE))) {
                    name = SolutionUtil.getCanonicalForm(List.of(name)).get(0);
                    asciiName = SolutionUtil.getCanonicalForm(List.of(asciiName)).get(0);
                    alternateNames = SolutionUtil.getCanonicalForm(alternateNames);
                    NameVariationsUtil.addAllVariationsOfAnAddress(name, asciiName, alternateNames, stateRoot);
                    SolutionUtil.addAlternateNameInMap(name, asciiName, alternateNames);
                    City city = new City(stateRoot, geoNameId, name, asciiName, alternateNames, code, admin1);
                    stateRoot.addSubRegion(city);
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public AbstractLocation getRoot() {
        return countryRoot;
    }
}
