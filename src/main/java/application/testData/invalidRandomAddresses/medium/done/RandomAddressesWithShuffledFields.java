package application.testData.invalidRandomAddresses.medium.done;

import application.testData.model.TestObject;

import java.util.*;

/**
 * helpful class to create a wrong completed address
 */
public class RandomAddressesWithShuffledFields {
    /**
     * helpful method to build the address for this case
     * shuffle the values from the fields of address
     */
    public static TestObject getAddress(TestObject testObject) {
        List<String> list = Arrays.asList(testObject.getStreet(), testObject.getCity(), testObject.getState(),
                testObject.getPhoneNumber(), testObject.getZipCode(), testObject.getCountryCallingCode(), testObject.getCountry());
        Collections.shuffle(list);
        return new TestObject(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
    }
}
