package application.testData.util.comparator;

import java.util.Comparator;

import static application.constants.ConstantsUtil.STAR;

public class StarListComparator implements Comparator<String> {
    @Override
    public int compare(String first, String second) {
        int c1 = 0;
        int c2 = 0;
        if (first.endsWith(STAR)) {
            c1 = 1;
        }
        if (second.endsWith(STAR)) {
            c2 = 1;
        }
        return c1 - c2;
    }
}
