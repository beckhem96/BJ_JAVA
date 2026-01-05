package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class b15683 {
    static int N, M;
    static int[][] map;
    static ArrayList<CCTV> cctvs = new ArrayList<>();
    static int minBlindSpot = Integer.MAX_VALUE;

    // 상, 우, 하, 좌
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    // CCTV 종류별 감시 방향 모음 (회전 포함)
    static int[][][] mode = {
            {},
            {{0}, {1}, {2}, {3}}, // 1번 CCTV
            {{0, 2}, {1, 3}}, // 2번 CCTV
            {{0, 1}, {1, 2}, {2, 3}, {3, 0}}, // 3번 CCTV
            {{0, 1, 3}, {0, 1, 2}, {1, 2, 3}, {2, 3, 0}}, // 4번 CCTV
            {{0, 1, 2, 3}} // 5번 CCTV
    };

    static class CCTV {
        int r, c, type;
        CCTV(int r, int c, int type) {
            this.r = r;
            this.c = c;
            this.type = type;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");

        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);
        map = new int[N][M];
        int emptySpace = 0; // 반 칸의 개수

        for (int i = 0; i < N; i++) {
            line = br.readLine().split(" ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(line[j]);
                if (map[i][j] >= 1 && map[i][j] <= 5) {
                    cctvs.add(new CCTV(i, j, map[i][j]));
                } else if (map[i][j] == 0) {
                    emptySpace++;
                }
            }
        }

        // 초기 사각지대 개수 설정
        minBlindSpot = emptySpace;

        // DFS 시작 (0번째 CCTV부터 방향 설정)
        dfs(0, map);

        System.out.println(minBlindSpot);
    }

    static void dfs(int depth, int[][] prevMap) {
        // 모든 CCTV의 방향을 결정했을 때
        if (depth == cctvs.size()) {
            int count = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (prevMap[i][j] == 0) count++;
                }
            }
            minBlindSpot = Math.min(minBlindSpot, count);
            return;
        }
        CCTV cctv = cctvs.get(depth);
        int type = cctv.type;

        // 해당 CCTV 종류의 가능한 모든 회전 방향에 대해
        for (int i = 0; i < mode[type].length; i++) {
            // map 복사 (이번 단계에서 칠한 것이 다음 분기에 영향 주지 않도록)
            int[][] tempMap = new int[N][N];
            for (int k = 0; k < N; k++) {
                tempMap[k] = prevMap[k].clone();
            }

            // 현재 방향 설정에 따라 감시 영역 표시
            for (int dir : mode[type][i]) {
                watch(tempMap, cctv.r, cctv.c, dir);
            }

            // 다음 CCTV로 넘어감
            dfs(depth + 1, tempMap);
        }
    }

    static void watch(int[][] map, int r, int c, int dir) {
        while (true) {
            r += dr[dir];
            c += dc[dir];

            if (r < 0 || r >= N || c < 0 || c >= M || map[r][c] == 6) return;

            // 빈 칸(0)이면 감시 구역(-1)으로 표시
            // CCTV가 있는 곳이나 이미 감시된 곳은 통과
            if (map[r][c] == 0) {
                map[r][c] = -1;
            }
        }
    }
}
