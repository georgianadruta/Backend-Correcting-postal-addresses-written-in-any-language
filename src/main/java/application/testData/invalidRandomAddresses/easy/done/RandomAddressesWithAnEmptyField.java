package application.testData.invalidRandomAddresses.easy.done;

import application.testData.model.TestObject;
import application.testData.util.TestUtil;

/**
 * helpful method to generate addresses with a random empty field
 */
public class RandomAddressesWithAnEmptyField {
    /**
     * clear the extracted field
     */
    public static TestObject getAddress(TestObject testObject) {
        int n = TestUtil.getRandomNumber();
        switch (n) {
            case 0 -> testObject.setStreet(null);
            case 1 -> testObject.setCity(null);
            case 2 -> testObject.setState(null);
            case 3 -> testObject.setPhoneNumber(null);
            case 4 -> testObject.setZipCode(null);
            case 5 -> testObject.setCountryCallingCode(null);
            case 6 -> testObject.setCountry(null);
            default -> {
            }
        }
        return testObject;
    }
}
