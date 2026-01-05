package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b11054 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        // dp_inc[i]: i번째 원소를 마지막으로 하는 가장 긴 증가 부분 수열 길이
        int[] dp_inc = new int[N];
        // dp_dec[i]: i번째 원소를 시작으로 하는 가장 긴 감소 부분 수열 길이 (역방향 LIS)
        int[] dp_dec = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 1.왼쪽 -> 오른쪽 (LIS)
        for (int i = 0; i< N;i++) {
            dp_inc[i] = 1; // 기본 길이는 자기 자신 1
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp_inc[i] < dp_inc[j] + 1) { // 가장 긴 수열을 구하고 있음
                    dp_inc[i] = dp_inc[j] + 1;
                }
            }
        }

        // 2.오른쪽 -> 왼쪽 (역방향 LIS == LDS)
        for (int i = N - 1; i >= 0; i--) {
            dp_dec[i] = 1; // 기본 길이는 자기 자신 1
            for (int j = N - 1; j > i; j--) {
                if (arr[j] < arr[i] && dp_dec[i] < dp_dec[j] + 1) {
                    dp_dec[i] = dp_dec[j] +1;
                }
            }
        }

        // 3. 합치기
        int maxLen = 0;
        for (int i = 0; i < N; i++) {
            // 두 길이를 더하고, 겹치는 peak(자기 자신) 1개를 뺌
            int len = dp_inc[i] + dp_dec[i] - 1;
            if (len > maxLen) {
                maxLen = len;
            }
        }

        System.out.println(maxLen);
    }
}
