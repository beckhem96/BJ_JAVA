package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b6064 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while(T-- > 0) {
            String[] line = br.readLine().split(" ");
            int M = Integer.parseInt(line[0]);
            int N = Integer.parseInt(line[1]);
            int x = Integer.parseInt(line[2]);
            int y = Integer.parseInt(line[3]);

            int answer = -1;
            // 마지막 해(멸망의 날)는 M과 N의 최소공배수
            int lcm = M * N / gcd(M, N);

            // x는 고정하고 M만큼씩 건너 뛰며 탐색
            // i는 우리가 찾는 'k번째 해'의 후보입니다.
            for (int i = x; i <= lcm; i += M) {
                // 현재 값 i가 y 조던도 만족하는지 확인
                // (i - 1) % N + 1 은  1-based index에서 나머지를 구하는 공식입니다.
                if ((i - 1) % N + 1 == y) {
                    answer = i;
                    break;
                }
            }
            System.out.println(answer);
        }
    }

    // 최대 공약수(GCD) 구하는 함수 (유클리드 호재법)
    public static int gcd(int a, int b) {
        while(b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}


//요약: 문제를 보고 파악하는 힌트"두 주기가 서로 다르게 반복된다" (M년 주기, N년 주기)👉 **최소공배수(LCM)**가 전체 주기의 끝이겠구나!"나머지 연산과 관련된 조건이다" ($k \% M = x$, $k \% N = y$)👉 부정방정식 혹은 나머지 연산(Modulo)의 성질을 이용해야겠구나!"탐색 범위가 매우 크다" (M, N이 4만이면 LCM은 16억)👉 1씩 더하는 시뮬레이션은 안 되고, 배수만큼 건너뛰는(Jump) 방법을 써야겠구나!