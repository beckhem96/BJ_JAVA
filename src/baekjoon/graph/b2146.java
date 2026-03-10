package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b2146 {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 1단계: 섬 번호 붙이기 (2부터 시작)
        int islandId = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1 & !visited[i][j]) {
                    markIsland(i, j, islandId);
                    islandId++;
                }
            }
        }

        // 2단계: 각 섬에서 다른 섬으로 가는 최단 거리 찾기
        int minBridge = Integer.MAX_VALUE;
        for (int i = 2; i < islandId; i++) {
            minBridge = Math.min(minBridge, buildBridge(i));
        }
        System.out.println(minBridge);
    }

    // 1 단계: BFS로 연결된 육지를 같은 번호로 마킹
    static void markIsland(int r, int c, int id) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        visited[r][c] = true;
        map[r][c] = id;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                    if (map[nr][nc] == 1 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        map[nr][nc] = id;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }

    // 2단계: 특정 섬(id)에서 바다를 건너 다른 섬을 찾는 BFS
    static int buildBridge(int id) {
        Queue<int[]> q = new LinkedList<>();
        int[][] dist = new int[N][N];

        // 거리 배열을 -1로 초기화하고, 현재 탐색할 섬의 땅을 큐에 모두 넣음
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], -1);
            for (int j = 0; j < N; j++) {
                if (map[i][j] == id) {
                    q.offer(new int[]{i, j});
                    dist[i][j] = 0; // 시작점(육지)은 다리 길이 0
                }
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                    // 1. 바다(0)을 만나면 다리를 놓으며 전진
                    if (map[nr][nc] == 0 && dist[nr][nc] == -1) {
                        dist[nr][nc] = dist[r][c] + 1;
                        q.offer(new int[]{nr, nc});
                    }
                    // 2. 다른 섬을 만났다면! (바다도 아니고, 내 섬도 아님)
                    else if (map[nr][nc] > 0 && map[nr][nc] != id) {
                        return dist[r][c]; // 현재까지 놓은 다리 길이를 즉시 리턴 (최단 거리)
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}
