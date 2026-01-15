package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b1074 {
    static int count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");

        int N = Integer.parseInt(line[0]);
        int r = Integer.parseInt(line[1]);
        int c = Integer.parseInt(line[2]);

        // 2^N 크기 계산 (비트 연산 사용)
        int size = (int) Math.pow(2, N);

        find(size, r, c);
        System.out.println(count);
    }

    static void find(int size, int r, int c) {
        if (size == 1) return;

        int half = size / 2;

        // 1사분면 (왼쪽 위)
        if (r < half && c < half) {
            find(half, r, c);
        }
        // 2사분면 (오른쪽 위)
        else if (r < half && c >= half) {
            count += half * half; // 1사분면 크기만큼 더함
            find(half, r, c - half); // 좌표 갱신 (오른쪽으로 왔으므로 c에서 half를 뺌)
        }
        // 3사분면 (왼쪽 아래)
        else if (r >= half && c < half) {
            count += 2 * half * half; // 1, 2사분면 크기만큼 더함
            find(half, r - half, c); // 좌표 갱신 (아래로 왔으므로 r에서 half를 뺌)
        }
        // 4사분면 (오른쪽 아래)
        else {
            count += 3 * half * half; // 1, 2, 3사분면 크기만큼 더함
            find(half, r - half, c - half); // r, c 모두 갱신
        }
    }
}
