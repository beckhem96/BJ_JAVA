package baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b14890 {
    static int N, L;
    static int[][] map;

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int count = 0;

        // 가로길, 세로길을 각각 1차원 배열로 만들어서 검사
        for (int i = 0; i < N; i++) {
            if (checkPath(map[i])) count++; //가로 검사

            int[] col = new int[N];
            for (int j = 0; j < N; j++) {
                col[j] = map[j][i];
            }
            if (checkPath(col)) count++; // 세로 검사
        }
        System.out.println(count);
    }
    // 하나의 길(1차원 배열)이 지나갈 수 있는 길인지 판별
    static boolean checkPath(int[] path) {
        boolean[] ramp = new boolean[N]; // 경사로가 놓인 위치 기록

        for (int i = 0; i < N - 1; i++) {
            // 1. 높이가 같으면 패스
            if (path[i] == path[i + 1]) continue;

            // 2. 높이 차기ㅏ 1보타 크면 불가
            if (Math.abs(path[i] - path[i + 1]) > 1) return false;

            // 3. 내리막길 (현재 높이 > 다음 높이)
            if (path[i] == path[i + 1] + 1) {
                for (int j = i + 1; j <= i + L;j++) {
                    // 범위를 벗어나거나, 높이가 다르거나, 이미 경사로가 있으면 불가
                    if (j >= N || path[j] != path[i+1] || ramp[j]) return false;
                    ramp[j] = true; // 경사로 설치
                }
            }
            // 4. 오르막길 (현재 높이 < 다음 높이)
             else if (path[i] == path[i + 1] - 1) {
                 for (int j = i;j>i -L;j--) {
                     // 범위를 벗어나거나, 높이가 다르거나, 이미 경사로가 있으면 불가
                     if (j < 0 || path[j] != path[i] || ramp[j]) return false;
                     ramp[j] = true; // 경사로 설치
                 }
            }
        }
        return true;
    }
}
