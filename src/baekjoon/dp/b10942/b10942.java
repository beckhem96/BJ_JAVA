package baekjoon.dp.b10942;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b10942 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] numList = new int[N+1];

        for (int i = 1;i <= N;i++) {
            numList[i] = Integer.parseInt(st.nextToken());
        }

        int M = Integer.parseInt(br.readLine());
        boolean[][] dp = new boolean[N+1][N+1];

        for (int i = 1; i <= N;i++) {
            dp[i][i] = true;
        }

        for (int i = 1; i <= N-1;i++) {
            if (numList[i] == numList[i+1]) {
                dp[i][i+1] = true;
            }
        }

        for (int len = 2; len < N; len++) { // 길이가 작은 것부터 회문인지 검사
            for (int s = 1; s <= N-len; s++) {
                int e = s + len;
                if (numList[s] == numList[e] && dp[s+1][e-1]) {
                    dp[s][e] = true;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int m = 0; m < M;m++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            if (dp[S][E]) {
                sb.append(1).append("\n");
            } else {
                sb.append(0).append("\n");
            }
        }
        System.out.println(sb);
    }
}
