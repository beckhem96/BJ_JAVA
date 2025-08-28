package baekjoon.graph.b1956;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b1956 {
    // 도달 할 수 없는 경우를 나타낼 무한대 값.
    // 경로 비용의 합이 int 범위를 넘을 수 있으므로 적당히 큰 값으로 설정
    static final int INF = 400 * 10000 + 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        int[][] dist = new int[V + 1][V + 1];
        for (int i = 1; i <= V;i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // 주어진 간선 정보로 거리 배열 설정
        for (int i = 0; i < E;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            dist[a][b] = c;
        }

        for (int k = 1;k <= V;k++) {
            for (int i = 1;i <= V;i++) {
                for (int j = 1; j<= V;j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        int minCycle = INF;
        for (int i = 1;i<=V;i++) {
            // dist[i][i]가 0이 아니라는 것은 i를 포함하는 사이클이 있다는 의미
            // 하지만 이 문제에서는 야수 가중치만 있으므로 dist[i][i]는 0으로 유지
            // 따라서 i -> j -> i 형태의 사이클을 직접 찾아야함
            for (int j = 1;j <= V; j++) {
                if (i ==j) continue;
                if (dist[i][j] != INF && dist[j][i] != INF) {
                    minCycle = Math.min(minCycle, dist[i][j] + dist[j][i]);
                }
            }
        }

        if (minCycle == INF) {
            System.out.println(-1);
        } else {
            System.out.println(minCycle);
        }
    }
}
