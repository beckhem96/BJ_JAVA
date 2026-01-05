package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b11052 {
    // 카드팩,
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] cards = new int[N+1];
        String[] str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            cards[i] = Integer.parseInt(str[i-1]);
        }
        int[] dp = new int[N+1];
        dp[0] = 0;
        for (int i = 1;i<=N;i++) {
            for (int k = 1;k<=i;k++) {
                dp[i] = Math.max(dp[i], dp[i - k] + cards[k]);
            }
        }
        System.out.println(dp[N]);
    }
}
