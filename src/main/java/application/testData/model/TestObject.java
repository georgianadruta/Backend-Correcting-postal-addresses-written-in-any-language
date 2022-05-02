package application.testData.model;

import application.solution.SolutionUtil;
import lombok.Data;

import java.io.Serializable;

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
        this.street = street != null ? SolutionUtil.getCanonicalForm(new String[]{street.trim()})[0] : null;
        this.city = city != null ? SolutionUtil.getCanonicalForm(new String[]{city.trim()})[0] : null;
        this.state = state != null ? SolutionUtil.getCanonicalForm(new String[]{state.trim()})[0] : null;
        this.zipCode = zipCode != null ? zipCode.trim() : null;
        this.country = country != null ? SolutionUtil.getCanonicalForm(new String[]{country.trim()})[0] : null;
    }

    @Override
    public String toString() {
        return "{ " +
                "\nStreet: " + street +
                ",\nCity: " + city +
                ",\nState: " + state +
                ",\nZip code: " + zipCode +
                ",\nCountry: " + country + "\n}\n";
    }

    /**
     * helpful method to create an exact copy of an object
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
