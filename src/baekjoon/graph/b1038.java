package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class b1038 {
    static List<Long> numbers;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        numbers = new ArrayList<>();
        // 1023개 뿐이므로, N이 1022를 초가화면 -1 출력
        // 0번째부터 1022번째까지 총 1023개
        if (N > 1022) {
            System.out.println(-1);
            return;
        }

        // 0부터 9까지의 1자리 수로 DFS 시작
        for (int i = 0; i < 10; i++) {
            dfs(i);
        }

        Collections.sort(numbers);

        System.out.println(numbers.get(N));
    }

    static void dfs(long num) {
        numbers.add(num);

        long lastDigit = num % 10;

        // 마지막 자릿수보다 작은 숫자(0~lastDigit - 1)를 뒤에 붙임
        for (int i = 0; i < lastDigit; i++) {
            dfs(num * 10 + i);
        }
    }
}
