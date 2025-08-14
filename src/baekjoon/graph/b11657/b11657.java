package baekjoon.graph.b11657;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// 간선 정보를 담을 클래스
class Edge {
    int from;
    int to;
    int cost;

    public Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

public class b11657 {
    static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edges.add(new Edge(from, to, cost));
        }

        // 1. 거리 배열 초기화
        long[] dist = new long[N + 1];
        Arrays.fill(dist, INF);
        dist[1] = 0; // 시작 도시(1)의 거리는 0

        // 2. N-1번 동안 모든 간선에 대한 완화 작업 반복
        for (int i = 0; i < N - 1; i++) {
            for (Edge edge : edges) {
                if (dist[edge.from] != INF && dist[edge.to] > dist[edge.from] + edge.cost) {
                    dist[edge.to] = dist[edge.from] + edge.cost;
                }
            }
        }

        // 3. 음수 사이클 확인(N 번쨰 완화 작업)
        boolean hasNegativeCycle = false;
        for (Edge edge : edges) {
            // N - 1번 반복 후에도 거리가 갱신된다면 음수 사이클 존재
            if (dist[edge.from] != INF && dist[edge.to] > dist[edge.from] + edge.cost) {
                hasNegativeCycle = true;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (hasNegativeCycle) {
            sb.append(-1).append("\n");
        } else {
            for (int i = 2; i <= N; i++) {
                if (dist[i] == INF) {
                    sb.append(-1).append("\n"); // 도달 불가
                } else {
                    sb.append(dist[i]).append("\n");
                }
            }
        }
        System.out.print(sb);
    }
}
