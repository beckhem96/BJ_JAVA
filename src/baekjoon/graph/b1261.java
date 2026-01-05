package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Point {
    int r, c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class b1261 {
    static int N, M;
    static int[][] map, dist;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
                dist[i][j] = -1;
            }
        }
        bfs01();
    }

    static void bfs01() {
        // Deque (덱) 사용
        Deque<Point> deque = new ArrayDeque<>();
        deque.offer(new Point(0, 0));
        dist[0][0] = 0;

        while(!deque.isEmpty()) {
            Point current = deque.poll(); // 앞에서 꺼냄
            int r = current.r;
            int c = current.c;

            // 목적지
            if (r == N -1 && c == M - 1) {
                System.out.println(dist[r][c]);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nc >= 0 && nr < N && nc < M) {
                    // 아직 방문하지 않은 곳이라면
                    if (dist[nr][nc] == -1) {
                        if (map[nr][nc] == 0) {
                            // 빈 방 (0) -> 비용증가 없음, 우선순위 놓음 -> 덱의 '앞'에 추가
                            dist[nr][nc] = dist[r][c];
                            deque.addFirst(new Point(nr, nc));
                        } else {
                            // 벽 (1) -> 비용 1증가, 우선순위 낮음 -> 덱의 '뒤'에 추가
                            dist[nr][nc] = dist[r][c] + 1;
                            deque.addLast(new Point(nr, nc));
                        }
                    }
                }
            }
        }
    }
}
