package application.testData.model;

import application.solution.SolutionUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static application.constants.ConstantsUtil.*;

/**
 * helpful method for modeling random addresses
 */
@Data
public class TestObject implements Serializable, Cloneable {
    private String street;
    private String zipCode;
    private String state;
    private String city;
    private String country;

    public TestObject(String street, String zipCode, String state, String city, String country) {
        this.street = street != null ? SolutionUtil.getCanonicalForm(List.of(street.trim())).get(0) : null;
        this.state = state != null ? SolutionUtil.getCanonicalForm(List.of(state.trim())).get(0) : null;
        this.zipCode = zipCode != null ? zipCode.trim() : null;
        this.city = city != null ? SolutionUtil.getCanonicalForm(List.of(city.trim())).get(0) : null;
        this.country = country != null ? SolutionUtil.getCanonicalForm(List.of(country.trim())).get(0) : null;
    }

    public TestObject() {
        this.street = null;
        this.state = null;
        this.zipCode = null;
        this.city = null;
        this.country = null;
    }

    public void setTestObjectField(String fieldName, String input) {
        switch (fieldName) {
            case STREET -> this.setStreet(input);
            case ZIP_CODE -> this.setZipCode(input);
            case STATE -> this.setState(input);
            case CITY -> this.setCity(input);
            case COUNTRY -> this.setCountry(input);
        }
    }

    public void moveAFieldToAnother(String fromField, String toField) {
        try {
            String input = getAndRemoveField(fromField);
            switch (toField) {
                case STREET -> this.setStreet(this.getStreet() + ONE_WHITESPACE + input);
                case ZIP_CODE -> this.setZipCode(this.getZipCode() + ONE_WHITESPACE + input);
                case STATE -> this.setState(this.getState() + ONE_WHITESPACE + input);
                case CITY -> this.setCity(this.getCity() + ONE_WHITESPACE + input);
                case COUNTRY -> this.setCountry(this.getCountry() + ONE_WHITESPACE + input);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAndRemoveField(String fromField) throws CloneNotSupportedException {
        TestObject copy = (TestObject) this.clone();
        switch (fromField) {
            case STREET -> {
                this.setStreet(EMPTY_STRING);
                return copy.getStreet();
            }
            case ZIP_CODE -> {
                this.setZipCode(EMPTY_STRING);
                return copy.getZipCode();
            }
            case STATE -> {
                this.setState(EMPTY_STRING);
                return copy.getState();
            }
            case CITY -> {
                this.setCity(EMPTY_STRING);
                return copy.getCity();
            }
            case COUNTRY -> {
                this.setCountry(EMPTY_STRING);
                return copy.getCountry();
            }
        }
        return EMPTY_STRING;
    }

    public String getValueForNoField(Integer integer) {
        switch (integer) {
            case 0 -> {
                return this.street;
            }
            case 1 -> {
                return this.zipCode;
            }
            case 2 -> {
                return state;
            }
            case 3 -> {
                return city;
            }
            case 4 -> {
                return country;
            }
        }
        return EMPTY_STRING;
    }

    public void setValueForNoField(String value, Integer integer) {
        switch (integer) {
            case 0 -> street = value;
            case 1 -> zipCode = value;
            case 2 -> state = value;
            case 3 -> city = value;
            case 4 -> country = value;
        }
    }

    @Override
    public String toString() {
        return "{ " + NEW_LINE + "Street: " + street + SEPARATOR_DB_FILES + NEW_LINE + "Zip code: " + zipCode + SEPARATOR_DB_FILES + NEW_LINE + "State: "
                + state + SEPARATOR_DB_FILES + NEW_LINE + "City: " + city + SEPARATOR_DB_FILES + NEW_LINE + "Country: " + country + NEW_LINE + "}" + NEW_LINE;
    }

    /**
     * helpful method to create an exact copy of an object
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
