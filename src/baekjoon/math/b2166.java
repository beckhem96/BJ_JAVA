package baekjoon.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b2166 {
    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[] x = new long[N + 1];
        long[] y = new long[N + 1];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Long.parseLong(st.nextToken());
            y[i] = Long.parseLong(st.nextToken());
        }

        // 마지막에 첫 번째 좌푤르 한 번 더 넣어준다. (신발끈 공식의 핵심)

        x[N] = x[0];
        y[N] = y[0];

        long sum1 = 0;
        long sum2 = 0;

        // 대각선으로 곱해서 더하기
        for (int i = 0; i < N; i++) {
            sum1 += x[i] * y[i+1];
            sum2 += x[i+1] * y[i];
        }

        // 두 합의 차이의 절댓값을 구하고 2로 나눔 (소수점 첫째 자리까지 출력)
        double area = Math.abs(sum1 - sum2) / 2.0;

        // 소수 첫째자리까지 출력
        System.out.printf("%.1f\n", area);
    }
}
