package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b4179 {
    static int R, C;
    static char[][] maze;
    static int[][] fireTime;
    static int[][] jihoonTime;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        maze = new char[R][C];
        fireTime =  new int[R][C];
        jihoonTime = new int[R][C];

        Queue<int[]> fireQ = new LinkedList<>();
        Queue<int[]> jihoonQ = new LinkedList<>();

        // 배열 초기화
        for (int i = 0; i < R; i++) {
            Arrays.fill(fireTime[i], Integer.MAX_VALUE); // 불이 안 닿는 곳은 무한대로
            Arrays.fill(jihoonTime[i], -1); // 지훈이 방문 여부는 -1로
        }

        for (int i = 0; i < R;i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                maze[i][j] = line.charAt(j);
                if (maze[i][j] == 'F') {
                    fireQ.offer(new int[]{i, j});
                    fireTime[i][j] = 0;
                } else if (maze[i][j] == 'J') {
                    jihoonQ.offer(new int[]{i, j});
                    jihoonTime[i][j] = 0;
                }
            }
        }

        // 1. 불에 대한 BFS
        while (!fireQ.isEmpty()) {
            int[] cur = fireQ.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (nr < 0 || nc < 0 || nr >= R || nc >= C) continue;
                if (maze[nr][nc] =='#' || fireTime[nr][nc] != Integer.MAX_VALUE) continue;

                fireTime[nr][nc] = fireTime[cr][cc] + 1;
                fireQ.offer(new int[]{nr, nc});
            }
        }

        // 2. 지훈이에 대한 BFS
        while (!jihoonQ.isEmpty()) {
            int[] cur =  jihoonQ.poll();
            int cr = cur[0];
            int cc = cur[1];

            // 가장자리에 도달했다면 다음 번 이동으로 탈출 가능
            if (cr == 0 || cc == 0 || cr == R - 1 || cc == C - 1) {
                System.out.println(jihoonTime[cr][cc] + 1);
                return;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (nr < 0 || nc < 0 || nr >= R || nc >= C) continue;
                if (maze[nr][nc] == '#' || jihoonTime[nr][nc] >= 0) continue;

                // 핵심: 지훈이가 도착할 시간이 불이 도착할 시간보다 작아야만 이동 가능!
                if (jihoonTime[cr][cc] + 1 < fireTime[nr][nc]) {
                    jihoonTime[nr][nc] = jihoonTime[cr][cc] + 1;
                    jihoonQ.offer(new int[]{nr, nc});
                }
            }
        }
        // 큐가 다 빌 때까지 탈출을 못 했다면
        System.out.println("IMPOSSIBLE");
    }
}
