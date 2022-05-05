package application.testData.util;

import java.util.Arrays;

/**
 * helpful class to calculate the similarity string comparison
 */
public class LevenshteinDistance {
    public static double compute(String str1, String str2) {
        int n = Math.max(str1.length(), str2.length());
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = getMinimumOfEdits(dp[i - 1][j - 1] + getNumOfReplacement(str1.charAt(i - 1), str2.charAt(j - 1)), // replace
                            dp[i - 1][j] + 1, // delete
                            dp[i][j - 1] + 1); // insert
                }
            }
        }
        return (double) (n - dp[str1.length()][str2.length()]) / n;
    }

    private static int getNumOfReplacement(char c1, char c2) {
        return c1 == c2 ? 0 : 1;
    }

    private static int getMinimumOfEdits(int... nums) {
        return Arrays.stream(nums).min().orElse(Integer.MAX_VALUE);
    }
}
