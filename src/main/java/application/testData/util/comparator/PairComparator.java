package application.testData.util.comparator;

import org.apache.commons.math3.util.Pair;

import java.util.Comparator;

public class PairComparator implements Comparator<Pair<String, Integer>> {

    @Override
    public int compare(Pair<String, Integer> pair, Pair<String, Integer> secondPair) {
        return -1 * Integer.compare(pair.getValue(), secondPair.getValue());
    }
}