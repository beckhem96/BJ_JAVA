package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1890 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt((st.nextToken()));
            }
        }

        long[][] dp = new long[N][N];
        dp[0][0] = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 현재 칸에 도달 할 수 있는 경로가 없으면(0) pass
                if (dp[i][j] == 0) continue;

                // 2. 도착 지점이면 더 이상 점프하지 않음
                if (i == N-1 && j == N-1) {
                    continue;
                }

                int jump = map[i][j]; // 점프거리

                // 점프거리가 0이면 이동 불가
                if (jump == 0) continue;

                // 오른쪽
                if (j+jump < N) dp[i][j+jump] += dp[i][j];
                // 아래쪽
                if (i+jump < N) dp[i+jump][j] += dp[i][j];
            }
        }
        System.out.println(dp[N-1][N-1]);
    }
}
