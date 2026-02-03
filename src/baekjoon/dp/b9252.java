package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b9252 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 문자열 입력 (인덱스 편의를 위해 1부터 시작한다고 가정하고 로직 짬)
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();

        int n = str1.length;
        int m = str2.length;

        // dp 테이블
        int[][] dp = new int[n + 1][m + 1];

        // 1. DP 테이블 채우기 (LCS 길이 구하기)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 문자가 같으면 대각선 값 + 1
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                // 다르면 위쪽이나 왼쪽 중 큰 값 가져오기
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 길이 출력
        System.out.println(dp[n][m]);

        // 길이가 0이면 문자열을 찾을 필요 없음
        if (dp[n][m] == 0) return;

        // 2. 역추적 (LCS 문자열 찾기)
        StringBuilder sb = new StringBuilder();
        int i = n;
        int j = m;

        while (i > 0 &&  j > 0) {
            // 1. 위쪽 값과 같다면 위로 이동
            if (dp[i][j] == dp[i - 1][j]) {
                i--;
            } else if (dp[i][j] == dp[i][j-1]) { // 2. 왼쪽 값과 같다면 왼쪽으로 이동
                j--;
            } else { // 3. 둘 다 다르다면 해당 문자가 LCS에 포함됨 (대각선 이동)
                sb.append(str1[i-1]);
                i--;j--;
            }
        }
        System.out.println(sb.reverse().toString());
    }
}
