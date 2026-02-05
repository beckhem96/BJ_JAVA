package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b2636 {
    static int N, M;
    static int[][] cheeses;
    static boolean[][] visited;
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int cheeseCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cheeseCount = 0;
        cheeses = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                cheeses[i][j] = Integer.parseInt(st.nextToken());
                if (cheeses[i][j] == 1) {
                    cheeseCount++;
                }
            }
        }

        int time = 0;
        int lastCheesCount = 0;

        while (cheeseCount > 0) {
            time++;
            lastCheesCount = cheeseCount;
            bfs();
        }
        System.out.println(time);
        System.out.println(lastCheesCount);
    }

    static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        visited = new boolean[N][M];
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int i = 0; i < 4; i++) {
                int nr = cr + dr[i];
                int nc = cc + dc[i];
                // 범위 체크 및 방문 여부 확인
                if (nr >= 0 && nc >= 0 && nr < N && nc < M && !visited[nr][nc]) {
                    visited[nr][nc] = true;

                    if (cheeses[nr][nc] == 0) {
                        // 공기라면 계속 탐색을 위해 큐에 넣음
                        q.offer(new int[]{nr, nc});
                    } else {
                        // 치즈라면 녹임 (0으로 변경)
                        // 단, 이번 타임에 공기가 여기를 통과하진 못하므로 큐에 넣지 않음
                        cheeseCount--;
                        cheeses[nr][nc] = 0;
                    }
                }
            }
        }
    }
}
