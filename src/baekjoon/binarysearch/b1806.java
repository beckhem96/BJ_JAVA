package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1806 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int start = 0;
        int end = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;

        while (true) {
            // S 이상이 되면 길이를 줄여보자 (start 이동)
            if (currentSum >= S) {
                minLength = Math.min(minLength, end - start);
                currentSum -= arr[start];
                start++;
            } else if (end == N) { // end가 끝까지 갔는데도 S 미만이면 더 이상 방법 없음(종료)
                break;
            } else {
                currentSum += arr[end];
                end++;
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            System.out.println(0);
        } else {
            System.out.println(minLength);
        }
    }
}
