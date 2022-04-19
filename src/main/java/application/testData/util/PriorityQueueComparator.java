package application.testData.util;

import java.util.Comparator;

public class PriorityQueueComparator implements Comparator<String> {

    @Override
    public int compare(String x, String y) {
        return -1 * Integer.compare(x.length(), y.length());
    }
}
