package application.testData.util;

import application.testData.model.TestObject;
import org.apache.commons.math3.util.Pair;

import java.util.Comparator;

public class TestObjectPairComparator implements Comparator<Pair<TestObject, Integer>> {

    @Override
    public int compare(Pair<TestObject, Integer> pair, Pair<TestObject, Integer> secondPair) {
        return -1 * Integer.compare(pair.getValue(), secondPair.getValue());
    }
}