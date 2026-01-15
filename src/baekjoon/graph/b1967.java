package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class b1967 {
    static class Node {
        int to;
        int weight;
        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static ArrayList<Node>[] list;
    static boolean[] visited;
    static int maxDistance = 0;
    static int farthestNode = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 노드가 1개일 경우 지름은 0
        if (n == 1) {
            System.out.println(0);
            return;
        }

        list = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            // 양방향(무방향) 그래프로 연결
            list[parent].add(new Node(child, weight));
            list[child].add(new Node(parent, weight));
        }
        // 1. 임의의 노드(1번)에서 가장 먼 노드를 찾는다. -> farthestNode에 저장됨
        visited = new boolean[n + 1];
        dfs(1, 0);

        // 2. 위에서 찾은 farthestNode에서 다시 가장 먼 노드를 찾는다. -> maxDistanc가 지름
        visited = new boolean[n + 1];
        maxDistance = 0; // 최대 거리 초기화
        dfs(farthestNode, 0);

        System.out.println(maxDistance);
    }

    static void dfs(int node, int dist) {
        // 현재 거리가 최대 거리보다 크면 갱신
        if (dist > maxDistance) {
            maxDistance = dist;
            farthestNode = node;
        }

        visited[node] = true;

        for (Node next: list[node]) {
            if (!visited[next.to]) {
                dfs(next.to, dist + next.weight);
            }
        }
    }
}
