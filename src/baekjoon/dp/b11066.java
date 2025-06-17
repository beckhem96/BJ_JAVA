package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b11066 {
    static int[][]dp;
    static int[] sum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int K = Integer.parseInt(br.readLine());
            int[] files = new int[K+1];
            sum = new int[K+1]; // 누적합
            dp = new int[K+1][K+1]; // 최소비용 저장, [i][j]면 i~j 까지 최소비용, [1][3]이면 파일 1~3까지의 최소 비용을 저장할 거다

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1;i<=K;i++) {
                files[i] = Integer.parseInt(st.nextToken());
                sum[i] = sum[i-1] + files[i]; //
            }

            for (int len = 2; len <=K;len++) { // 합칠 길이, 2개씩 먼저, C1 + C2 =X1 -> len=2, X1 + C3 = len3..
                // 2가 초기, 3이 2개 더한거 다음으로 구하는 것
                for (int i =1;i<=K - len + 1;i++) {// 시작점 구하기, 그래서 len(합친 길이)과 K(총 길이)의 영향을 받음
                    int j = i + len - 1; // 합칠 구간의 끝점 구하기(시작 길이에 + len-1)
                    dp[i][j] = Integer.MAX_VALUE;
                    for(int k = i;k<j;k++) { // k는 구간, 그래서 시작점부터 끝점까지 나눠서 최소 비용을 구하는것,
                        dp[i][j] = Math.min(dp[i][j],
                                dp[i][k] +dp[k+1][j] + sum[j] - sum[i-1]); // j(끝점) 구간까지의 합에서 i-1(시작점 전), 그래야 i~j까지의 합이 구해짐
                    }
                }
            }
            System.out.println(dp[1][K]);
        }
    }
}
