package baekjoon.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class b2839 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        // greedy
//        int count_5 = N / 5;
//        while (count_5 >= 0) {
//            int remaining = N - (count_5 * 5);
//            if (remaining % 3 == 0) {
//                System.out.println(count_5 + (remaining / 3));
//                return;
//            } else {
//                count_5--;
//            }
//        }
//        System.out.println(-1);
        // dp
        int[] dp = new int[Math.max(N+1, 6)];
        Arrays.fill(dp, 5001);

        dp[3] = 1;
        dp[5] = 1;

        for (int i = 6; i <= N;i++) {
            int from3 = dp[i -3];
            int from5 = dp[i - 5];

            if (from3 != 5001 || from5 != 5001) {
                dp[i] = Math.min(from3, from5) + 1;
            }
        }

        if (dp[N] == 5001) {
            System.out.println(-1);
        } else {
            System.out.println(dp[N]);
        }
    }
}
