package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b2467 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int start = 0;
        int end = N-1;
        // 정답을 저장할 변수 (초기값은 최대로 설정)
        long minAbs = Long.MAX_VALUE;
        int ansLeft = 0;
        int ansRight = 0;
        while (start < end) {
            int sum = arr[start] + arr[end];
            long currentAbs = Math.abs(sum);

            if (currentAbs <= minAbs) {
                minAbs = currentAbs;
                ansLeft = arr[start];
                ansRight = arr[end];
            }
            if (sum == 0) break;
            if (sum > 0) {
                end--;
            } else {
                start++;
            }
        }

        System.out.println(ansLeft + " " + ansRight);
    }
}
