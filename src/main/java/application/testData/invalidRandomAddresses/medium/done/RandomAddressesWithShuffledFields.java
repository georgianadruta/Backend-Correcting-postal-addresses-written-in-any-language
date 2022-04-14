package application.testData.invalidRandomAddresses.medium.done;

import application.testData.model.TestObject;

import java.util.*;

public class RandomAddressesWithShuffledFields {

    // nerespectarea sensului fieldului. fac shuffle datelor din adresa

    public static TestObject getAddress(TestObject testObject) {
        List<String> list = Arrays.asList(testObject.getStreet(), testObject.getCity(), testObject.getState(),
                testObject.getPhoneNumber(), testObject.getZipCode(), testObject.getCountryCallingCode(), testObject.getCountry());
        Collections.shuffle(list);
        return new TestObject(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6));
    }
}
