package application.dataset.structure;

import application.solution.SolutionUtil;
import application.testData.util.NameVariationsUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
@Setter
public class State extends AbstractLocation {
    final Country countryRoot;

    public State(Country countryRoot, int geoNameId, String name, String asciiName, String[] alternateNames, String code, String admin1) {
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
                String[] splitData = dataFromFile.split("\t");
                int geoNameId = Integer.parseInt(splitData[0]);
                String name = splitData[1];
                String asciiName = splitData[2];
                String[] alternateNames = splitData[3].split(",");
                String featureClass = splitData[6];
                String code = splitData[8];
                String admin1 = splitData[10];
                if (code.equals(stateCode) && admin1.equals(stateAdmin1) && (featureClass.equals("P") || featureClass.equals("A"))) {
                    name = SolutionUtil.getCanonicalForm(new String[]{name})[0];
                    asciiName = SolutionUtil.getCanonicalForm(new String[]{asciiName})[0];
                    alternateNames = SolutionUtil.getCanonicalForm(alternateNames);
                    NameVariationsUtil.addAllVariationsOfAnAddress(name, asciiName, alternateNames, stateRoot);
                    SolutionUtil.addAlternateNameInMap(name, asciiName, alternateNames);
                    City city = new City(stateRoot, geoNameId, name, asciiName, alternateNames, code, admin1);
                    stateRoot.addSubRegion(city);
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ERROR! File at given path was not found!");
        }
    }

    @Override
    public AbstractLocation getRoot() {
        return countryRoot;
    }
}
