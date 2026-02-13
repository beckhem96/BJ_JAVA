package baekjoon.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b10986 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] cnt = new long[M]; // 나머지가 i인 누적 합의 개수를 저장
        long sum = 0; // 누적 합 변수

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());

            sum = (sum + num) % M; // 누적 합을 M으로 나눈 나머지 계산
            cnt[(int)sum]++;
        }
        // 1. 나머지가 0인 경우 (0번 인덱스부터의 구간 합이 나누어 떨어지는 경우)
        long result = cnt[0];

        // 2. 나머지가 같은 인덱스 2개를 뽑는 경우
        for (int i = 0; i < M; i++) {
            if (cnt[i] > 1) {
                result += (cnt[i] * (cnt[i] - 1)) / 2;
            }
        }
        System.out.println(result);
    }
}
