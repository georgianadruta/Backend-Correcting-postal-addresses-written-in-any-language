package application.testData.util.comparator;

import java.util.Comparator;

public class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String first, String second) {
        return (-1) * (first.length() - second.length());
    }
}
