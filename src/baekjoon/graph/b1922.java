package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class b1922 {
    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        // cost 기분 오름차순 정렬
        @Override
        public int compareTo(Edge other) {
            return this.cost - other.cost;
        }
    }

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pq.offer(new Edge(a, b, c));
        }

        // Union - Find를 위한 부모 배열 초기화
        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int edgeCount = 0;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            // 두 노드의 부모가 다르면 (연결되어 있지 않으면) 연결 수행
            if (find(edge.from) != find(edge.to)) {
                union(edge.from, edge.to);
                totalCost += edge.cost;
                edgeCount++;
            }
            // 모든 컴퓨터가 연결되었으면 조기 종료 (간선 N-1개)
            if (edgeCount == N - 1) break;
        }
        System.out.println(totalCost);
    }

    // Find: 해당 노드의 최상위 부모(루트) 찾기 + 경로 압축
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union: 두 집합을 하나로 합치기
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA;
        }
    }
}
