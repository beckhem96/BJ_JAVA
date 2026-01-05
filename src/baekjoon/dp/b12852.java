package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b12852 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // dp[i]: i를 1로 만드는데 필요한 최소 연산 횟수
        int[] dp = new int[N + 1];
        // trace[i]: i가 되기 직전의 숫자 (경로 복원용)
        int[] trace = new int[N +1];

        dp[1] = 0; // 1은 이미 1이므로 연산 횟수 0

        for (int i = 2; i <= N; i++) {
            // 1. 1을 빼는 경우 (기본값으로 설정)
            dp[i] = dp[i - 1] + 1;
            trace[i] = i -1;

            // 2. 2로 나누어 떨어지는 경우
            if (i % 2 == 0 && dp[i] > dp[i / 2] + 1) {
                dp[i] = dp[i /2] + 1;
                trace[i] = i / 2;
            }

            // 3. 3으로 나누어 떨어지는 경우
            if (i % 3 == 0 && dp[i] > dp[i / 3] + 1) {
                dp[i] = dp[i/3] + 1;
                trace[i] = i / 3;
            }
        }

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        sb.append(dp[N]).append("\n");

        // 경로 역추적
        int current = N;
        while (current > 0) {
            sb.append(current).append(" ");
            if (current == 1) break; // 1에 도달하면 종료
            current = trace[current];
        }

        System.out.println(sb);
    }
}
