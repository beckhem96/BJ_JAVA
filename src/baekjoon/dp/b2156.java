package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b2156 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N];
        int[] wine = new int[N];
        for (int i = 0; i < N; i++) {
            wine[i] = Integer.parseInt(br.readLine());
        }
        // 첫 번째 잔까지의 최댓값
        dp[0] = wine[0];
        // 두 번째 잔까지의 최댓 값
        if (N > 1) {
            dp[1] = wine[0] + wine[1];
        }

        // 세 번째 잔까지의 최댓값
        if (N > 2) {
            // Case 1: 3번째 잔을 안 마심 (1, 2번째 잔 마심) -> wine[0] + wine[1]
            // Case 2: 1, 3 번째 잔 마심 -> wine[0] + wine[2]
            // Case 3: 2, 3 번째 잔 마시 -> wine[1] + wine[2]
            int case1 = dp[1];
            int case2 = wine[0] + wine[2];
            int case3 = wine[1] + wine[2];
            dp[2] = Math.max(case1, Math.max(case2, case3));
        }

        for (int i = 3; i < N; i++) {
            // Case1: 현재 잔(i)를 안 마심
            int notDrink = dp[i-1];
            // Case2: 현재 잔(i)만 마시고 이전 잔(i-1)은 안마심
            int drinkOne = dp[i-2] + wine[i];
            // Case3: 현제 진(i)과 이전 잔(i-1)을 연속으로 마심
            int drinkTwo = dp[i-3] + wine[i-1] + wine[i];
            dp[i] = Math.max(notDrink, Math.max(drinkOne, drinkTwo));
        }

        System.out.print(dp[N-1]);
    }
}
