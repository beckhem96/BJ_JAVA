package baekjoon.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class b2217 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] lopeList = new int[N];
        for (int i = 0;i < N; i++) {
            lopeList[i] = Integer.parseInt(br.readLine());
        }
        int answer = 0;
        Arrays.sort(lopeList);
        for (int i = 0;i < N; i++) {
            answer = Math.max(answer, (N- i) * lopeList[i]);
        }
        System.out.println(answer);
    }
}
