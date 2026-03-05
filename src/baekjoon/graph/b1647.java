package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b1647 {
    static class Edge implements Comparable<Edge> {
        int a, b, cost;

        public Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost); // 비용 기준 오름차순 정렬
        }
    }

    static int[] parent;

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 집의 개수
        int M = Integer.parseInt(st.nextToken()); // 길의 개수

        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edgeList.add(new Edge(a, b, cost));
        }

        // 간선을 비용 순으로 오름차순 정렬
        Collections.sort(edgeList);

        // 부모 테이블 초기화 (자기 자신을 부모로)
        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int maxEdge = 0; // MST에 포함된 간선 중 가장 비용이 큰 간선

        // 크루스칼 알고리즘 수행
        for (Edge edge: edgeList) {
            // 사이클이 발생하지 않는 경우에만 집합에 포함, 이부분에서 이미 불필요한 경로는 없다고 생각하면 됨
            if (find(edge.a) != find(edge.b)) { // 같은 부모가 아니어야 사이클이 생성이 안됨
                union(edge.a, edge.b);
                totalCost += edge.cost;
                maxEdge = edge.cost; // 오름차순 정렬이므로 마지막으로 들어간 값이 최대값
            }
        }
        // 전체 MST 비용에서 가장 비싼 간선의 비용을 뺌 (마을 2개로 분할)
        System.out.println(totalCost - maxEdge);
    }

    // 부모 노드를 찾는 함수 (경로 압축)
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // 두 집합을 합치는 함수
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            parent[rootB] = rootA;
        }
    }
}
