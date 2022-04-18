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
    private String city;
    private String state;
    private String phoneNumber;
    private String zipCode;
    private String countryCallingCode;
    private String country;

    public TestObject(String street, String city, String state, String phoneNumber, String zipCode, String countryCallingCode, String country) {
        this.street = street != null ? SolutionUtil.getCanonicalForm(new String[]{street.trim()})[0] : null;
        this.city = city != null ? SolutionUtil.getCanonicalForm(new String[]{city.trim()})[0] : null;
        this.state = state != null ? SolutionUtil.getCanonicalForm(new String[]{state.trim()})[0] : null;
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
        this.countryCallingCode = countryCallingCode != null ? countryCallingCode.trim() : null;
        this.zipCode = zipCode != null ? zipCode.trim() : null;
        this.country = country != null ? SolutionUtil.getCanonicalForm(new String[]{country.trim()})[0] : null;
    }

    @Override
    public String toString() {
        return "{ " +
                "\nStreet: " + street +
                ",\nCity: " + city +
                ",\nState: " + state +
                ",\nPhone number: " + phoneNumber +
                ",\nZip code: " + zipCode +
                ",\nCountry calling code: " + countryCallingCode +
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
