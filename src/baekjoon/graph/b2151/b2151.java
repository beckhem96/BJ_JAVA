package baekjoon.graph.b2151;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class State {
    int r, c, dir, mirrors;
    State(int r, int c, int dir, int mirrors) {
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.mirrors = mirrors;
    }
}

public class b2151 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        char[][] map = new char[N][N];
        int[][] doors = new int[2][2];
        int doorIdx = 0;

        for (int i = 0;i < N; i++) {
            String line = br.readLine();
            for (int j = 0;j < N; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == '#') {
                    doors[doorIdx][0] = i;
                    doors[doorIdx][1] = j;
                    doorIdx++;
                }
            }
        }

        // 방향: 0:상, 1:하, 2:좌, 3:우
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // mirrors[r][c][dir]: (r, c)에 dir 방향으로 도달하기 위한 최소 거울 수
        int[][][] mirrors = new int[N][N][4];
        for (int[][] row: mirrors) {
            for (int[] col: row) {
                Arrays.fill(col, Integer.MAX_VALUE);
            }
        }

        Deque<State> deque = new ArrayDeque<>();
        int startR = doors[0][0];
        int startC = doors[0][1];

        // 시작점에서 네 방향으로 출발하는 초기 상태 설정
        for (int i = 0; i < 4; i++) {
            mirrors[startR][startC][i] = 0;
            deque.addFirst(new State(startR, startC, i, 0));
        }

        while (!deque.isEmpty()) {
            State current = deque.removeFirst();

            // 직진 (비용 0)
            int nr = current.r + dr[current.dir];
            int nc = current.c + dc[current.dir];

            if (nr >= 0 && nr < N && nc >= 0 && nc < N && map[nr][nc] != '*') {
                if (mirrors[nr][nc][current.dir] > current.mirrors) {
                    mirrors[nr][nc][current.dir] = current.mirrors;
                    deque.addFirst(new State(nr, nc, current.dir, current.mirrors));
                }
            }

            // 회전 (비용 1) - 거울 설치 가능 장소에서만
            if (map[current.r][current.c] == '!') {
                // 상(0), 하(1) -> 좌(2), 우(3) / 좌(2), 우(3) -> 상(0), 하(1)
                for (int nextDir = 2; nextDir <= 3; nextDir++) { // 좌, 우 회전 시도
                    if (current.dir < 2) { // 현재 방향이 상, 하일 때
                        nr = current.r + dr[nextDir];
                        nc = current.c + dc[nextDir];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < N && map[nr][nc] != '*') {
                            if (mirrors[nr][nc][nextDir] > current.mirrors + 1) {
                                mirrors[nr][nc][nextDir] = current.mirrors + 1;
                                deque.addLast(new State(nr, nc, nextDir, current.mirrors + 1));
                            }
                        }
                    }
                }
                for (int nextDir = 0; nextDir <= 1; nextDir++) { // 상, 하 회전 시도
                    if (current.dir >= 2) {
                        nr = current.r + dr[nextDir];
                        nc = current.c + dc[nextDir];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < N && map[nr][nc] != '*') {
                            if (mirrors[nr][nc][nextDir] > current.mirrors + 1) {
                                mirrors[nr][nc][nextDir] = current.mirrors + 1;
                                deque.addLast(new State(nr, nc, nextDir, current.mirrors + 1));
                            }
                        }
                    }
                }
            }
        }

        int endR = doors[1][0];
        int endC = doors[1][1];
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            result = Math.min(result, mirrors[endR][endC][i]);
        }
        System.out.println(result);
    }
}
