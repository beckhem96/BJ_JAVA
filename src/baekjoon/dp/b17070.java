package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b17070 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];

        for (int i = 0; i < N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp[행][열][방향] (0:가로, 1:세로, 2:대각선)
        int[][][] dp = new int[N][N][3];

        // 초기 상태: (0, 1) 위치에 가로(0) 파이프가 놓여있음
        dp[0][1][0] = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // (0,0), (0,1)은 건너뜀 (이미 초기화됨)
                if (i == 0 && j < 2) continue;

                // 현재 위치가 벽(1)이면 갈 수 없음
                if (map[i][j] == 1) continue;

                // 1. 가로로 오는 경우 (왼쪽에서 가로 or 대각선)
                if (j - 1 >= 0) { // 가로 파이프 위치가 r, c라면 r, c-1도 사용해야하니 j -1 >= 0 조건이 들어감. 즉, j = 0엔 가로 파이프가 올 수 없음.
                    // + 인덱스 범위 보호
                    dp[i][j][0] = dp[i][j - 1][0] + dp[i][j -1][2];
                }

                // 2. 세로로 오는 경우 (위쪽에서 새로 or 대각선)
                if (i - 1 >= 0) {
                    dp[i][j][1] = dp[i - 1][j][1] + dp[i - 1][j][2];
                }

                // 3. 대각서능로 오는 경우(왼쪽 위에서 가로 or 세로 or 대각선)
                // 대각선은 추가로 위쪽과 왼쪽이 벽이 아니어야 함
                if (i - 1 >= 0 && j - 1 >= 0) {
                    if (map[i - 1][j] == 0 && map[i][j - 1] == 0) {
                        dp[i][j][2] = dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2];
                    }
                }
            }
        }

        System.out.println(dp[N-1][N-1][0] + dp[N-1][N-1][1] + dp[N-1][N-1][2]);
    }
}
