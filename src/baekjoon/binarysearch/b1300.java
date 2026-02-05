package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b1300 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        // x의 범위: 1 ~ K (K번쨰 수는 K보다 클 수 없음)
        long low = 1;
        long high = K;
        long ans = 0;

        while (low <= high) {
            long mid = (low + high) / 2;
            long count = 0;

            // 각 행별로 mid 보다 작거나 같은 수의 개수 합산
            for (int i =1; i <= N; i++) {
                count += Math.min(mid/i, N);
            }
            // 개수가 K개 이상이면, mid는 정답 후보임 (더 작은 수도 가능한지 확인 위해 줄임)
            if (count >= K) {
                ans = mid;
                high = mid - 1;
            }
            // 개수가 K개 미만이면, mid는 너무 작음 (더 큰 수 탐색)
            else {
                low = mid + 1;
            }
        }
        System.out.println(ans);
    }
}
