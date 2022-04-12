package application.testData.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestObject implements Serializable {
    private String street;
    private String city;
    private String state;
    private String phoneNumber;
    private String zipCode;
    private String countryCallingCode;
    private String country;

    public TestObject(String street, String city, String state, String phoneNumber, String zipCode, String countryCallingCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.countryCallingCode = countryCallingCode;
        this.zipCode = zipCode;
        this.country = country;
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
}
