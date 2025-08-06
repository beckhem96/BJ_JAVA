package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b7579 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] memories = new int[N];
        int[] costs = new int[N];
        int totalCost = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            memories[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            costs[i] = Integer.parseInt(st.nextToken());
            totalCost += costs[i];
        }

        // dp[c] = 비용 c를 들여 확보할 수 있는 최대 메모리
        int[] dp = new int[totalCost + 1];

        // 모든 앱을 순회
        for (int i = 0; i < N; i++) {
            int memory = memories[i];
            int cost = costs[i];

            // 역순으로 순회(핵심)
            for (int j = totalCost; j >= cost; j--) {
                // j 비용으로 i번째 앱을 비활성화하는 경우와 안하는 경우 중 최대 메모리 선택
                // dp[j] : i번째 앱을 비활성화 안 함
                // dp[j - cost] + memory : i번째 앱을 비활성화 함
                dp[j] = Math.max(dp[j], dp[j - cost] + memory);
            }
        }

        int minCost = 0;
        for (int i = 1; i <= totalCost; i++) {
            if (dp[i] >= M) {
                minCost = i;
                break;
            }
        }
        System.out.println(minCost);
    }
}
