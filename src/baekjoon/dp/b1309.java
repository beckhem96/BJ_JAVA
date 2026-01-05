package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b1309 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int MOD = 9901;
        // 세 가지의 경우의 수 N번째 칸 왼쪽에, 오른쪽에 사자가 있을 때와 아예 없을 때
        int[][] dp = new int[N+1][3]; //
        // 1. dp[i][0] : i번째 칸 왼쪽에 사자가 없을 때 -> dp[i-1][0] + dp[i-1][1] + dp[i-1][2]
        // 2. dp[i][1] : i번째 칸 왼쪽에 사자가 있을 때 -> dp[i-1][0] + dp[i-1][2]
        // 3. dp[i][2] : i번째 칸 왼쪽에 사자가 없을 때 -> dp[i-1][0] + dp[i-1][1]

        // 초기화 (첫 번째 줄)
        dp[1][0] = 1; // 배치 안했을 떄의 경우의 수
        dp[1][1] = 1; // 왼쪽에 배치했을 때 경우의 수
        dp[1][2] = 1; // 오른쪽에 배치했을 떄 경우의 수

        for (int i = 2; i <= N; i++) {
            dp[i][0] = (dp[i-1][0] + dp[i-1][1] + dp[i-1][2]) % MOD;
            dp[i][1] = (dp[i-1][0] + dp[i-1][2]) % MOD;
            dp[i][2] = (dp[i-1][0] + dp[i-1][1]) % MOD;
        }

        int result = (dp[N][0] + dp[N][1] + dp[N][2]) % MOD;
        System.out.println(result);
    }
}
