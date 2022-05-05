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
public class Country extends AbstractLocation {
    public Country(int geoNameId, String name, String asciiName, List<String> alternateNames, String code, String admin1) {
        super(geoNameId, name, asciiName, alternateNames, code, admin1);
    }

    /**
     * helpful method to add states from the given filePath
     * in admin1CodesAscii we have only states
     * eg: for Romania the states are identified by the id RO.X, where X is a number
     */
    public void addStates(String filePath, Country country) {
        try {
            File file = new File(ADMIN_1_CODES_FILE_ASCII);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String dataFromFile = reader.nextLine();
                String[] splitData = dataFromFile.split(TAB);
                String[] codeAndAdmin1 = splitData[0].split("\\.");
                String code = codeAndAdmin1[0];
                String admin1 = codeAndAdmin1[1];
                if (code.equals(this.code)) {
                    searchStateInCountryFile(filePath, country, admin1);
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * search the state with the given id from admin1CodesAscii file in the country file then it added to country
     * and add cities in the found state
     */
    private void searchStateInCountryFile(String filePath, Country countryRoot, String givenAdmin1) {
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
                String featureCode = splitData[7];
                String code = splitData[8];
                String admin1 = splitData[10];
                if (code.equals(countryRoot.getCode()) && admin1.equals(givenAdmin1) && featureClass.equals(A_CODE) && featureCode.equals(ADM1_CODE)) {
                    name = SolutionUtil.getCanonicalForm(List.of(name)).get(0);
                    asciiName = SolutionUtil.getCanonicalForm(List.of(asciiName)).get(0);
                    alternateNames = SolutionUtil.getCanonicalForm(alternateNames);
                    NameVariationsUtil.addAllVariationsOfAnAddress(name, asciiName, alternateNames, countryRoot);
                    SolutionUtil.addAlternateNameInMap(name, asciiName, alternateNames);
                    State state = new State(countryRoot, geoNameId, name, asciiName, alternateNames, code, admin1);
                    countryRoot.addSubRegion(state);
                    state.addCity(filePath, state);
                    return;
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @return null because this is the first level
     */
    @Override
    public AbstractLocation getRoot() {
        return null;
    }
}
