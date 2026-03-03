package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class b4485 {
    // 좌표와 누적 비용을 담을 클래스
    static class Node implements Comparable<Node> {
        int r, c, cost;

        public Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost); // 비용 기준 오름차순 정렬
        }
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static final int INF = Integer.MAX_VALUE;

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int tc = 1; // 테스트 케이스 번호

        while (true) {
            int N = Integer.parseInt(br.readLine());
            if (N == 0) break; // 0이 입력되면 종료

            int[][] map = new int[N][N];
            int[][] dist = new int[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    dist[i][j] = INF; // 최단 거리 배열을 무한대로 초기화
                }
            }

            // 다익스트라 시작
            PriorityQueue<Node> pq = new PriorityQueue<>();

            // 시작점 (0, 0) 설정 (시작점의 루피도 포함해야 함!)
            dist[0][0] = map[0][0];
            pq.offer(new Node(0, 0, map[0][0]));

            while (!pq.isEmpty()) {
                Node cur = pq.poll();

                // 도착지점에 도달하면 탐색 종료 (우선순위 큐이므로 처음 도달한 것이 무조건 최소 비용)
                if (cur.r == N - 1 && cur.c == N - 1) {
                    sb.append("Problem ").append(tc).append(": ").append(cur.cost).append("\n");
                    break;
                }

                // 이미 더 짧은 경로를 찾았다면 스킵 (시간 초과 방지)
                if (cur.cost > dist[cur.r][cur.c]) continue;

                // 4방향 탐색
                for (int i = 0; i < 4 ;i ++) {
                    int nr = cur.r + dr[i];
                    int nc = cur.c + dc[i];

                    // 맵 범위 안인지 확인
                    if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                        int nextCost = cur.cost + map[nr][nc];

                        // 기존에 기록된 비용보다 새롭게 구한 비용이 더 작다면 갱신
                        if (nextCost < dist[nr][nc]) {
                            dist[nr][nc] = nextCost;
                            pq.offer(new Node(nr, nc, nextCost));
                        }
                    }
                }
            }
            tc++;
        }
        System.out.println(sb);
    }
}
