package application.testData.invalidRandomAddresses.easy.done;

import application.testData.model.TestObject;

import java.util.Random;

public class RandomAddressesWithAnEmptyField {
    // se extrage un field intamplator si se elimina

    private static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(7); // 0, ... , 6
        while (n == 3) {
            n = rand.nextInt(7);
        }
        return n;
    }

    public static TestObject getAddress(TestObject testObject) {
        int n = getRandomNumber();
        switch (n) {
            case 0 -> testObject.setStreet(null);
            case 1 -> testObject.setCity(null);
            case 2 -> testObject.setState(null);
            case 4 -> testObject.setZipCode(null);
            case 5 -> testObject.setCountryCallingCode(null);
            case 6 -> testObject.setCountry(null);
            default -> {
            }
        }
        return testObject;
    }
}
