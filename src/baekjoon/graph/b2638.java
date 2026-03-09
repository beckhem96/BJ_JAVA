package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b2638 {
    static int N, M;
    static int[][] map;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static int cheeseCount = 0; // 남은 치즈 개수

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) cheeseCount++;
            }
        }

        int time = 0;

        // 치즈가 다 녹을 때까지 반복
        while (cheeseCount > 0) {
            bfs();  // 외부 공기 탐색 및 치즈 접촉 카운트
            time++;
        }

        System.out.println(time);
    }

    static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        int[][] contact = new int[N][M]; // 외부 공기 접촉 횟수

        // (0, 0)은 항상 외부 공기
        q.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                    // 빈 공간(외부 공기)이면 큐에 넣고 계속 탐색
                    if (map[nr][nc] == 0 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        q.offer(new int[]{nr, nc});
                    }
                    // 치즈를 만나면 접촉 횟수 증가 (큐에 넣지는 않음!)
                    else if (map[nr][nc] == 1) {
                        contact[nr][nc]++;
                    }
                }
            }
        }

        // 탐색이 끝난 후, 2면 이상 접촉한 치즈를 녹임
        for (int i = 0; i < N; i++) {
            for (int j = 0 ;j < M; j++) {
                if (contact[i][j] >= 2) {
                    map[i][j] = 0; // 치즈가 녹아서 빈 공간이 됨
                    cheeseCount--; // 남은 치즈 개수 감소
                }
            }
        }
    }
}
