package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b9205 {
    static class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());// 테스트 케이스 개수

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine()); // 편의점 개수

            // 집 좌표
            StringTokenizer st = new StringTokenizer(br.readLine());
            Point house = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            // 편의점 좌표들
            Point[] stores = new Point[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                stores[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            // 페스티벌 좌표
            st = new StringTokenizer(br.readLine());
            Point festival = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            // BFS 탐색
            if (bfs(house, stores, festival, n)) {
                sb.append("happy\n");
            } else {
                sb.append("sad\n");
            }
        }
        System.out.println(sb);
    }

    static boolean bfs(Point house, Point[] stores, Point festival, int n) {
        Queue<Point> q = new LinkedList<>();
        boolean[] visited = new boolean[n]; // 편의점 방문 여부

        q.offer(house);

        while (!q.isEmpty()) {
            Point cur = q.poll();
            // 1. 현재 위치에서 페스티벌까지 한 번에 갈 수 있는지 확인, 20병 * 50미터 = 1000미터
            if (Math.abs(cur.x - festival.x) + Math.abs(cur.y - festival.y) <= 1000) {
                return true;
            }

            // 2. 갈 수 없다면, 갈 수 있는(1000m 이내) 미방문 편의점을 찾음
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    int dist = Math.abs(cur.x - stores[i].x) + Math.abs(cur.y - stores[i].y);
                    if (dist <= 1000) {
                        visited[i] = true;
                        q.offer(stores[i]);
                    }
                }
            }
        }
        return false; // 큐가 빌 때까지 페스티벌에 도달하지 못함
    }
}
