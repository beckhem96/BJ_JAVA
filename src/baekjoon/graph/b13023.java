package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class b13023 {
    static List<List<Integer>> graph;
    static int N, E;
    static boolean[] visited;
    static boolean found = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        E = Integer.parseInt(line[1]);

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            String[] nodes = br.readLine().split(" ");
            int u = Integer.parseInt(nodes[0]);
            int v = Integer.parseInt(nodes[1]);
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        // 누가 시작점일지 모르니 0번 부터 N-1번까지 다 돌려 봐야함
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            if (found) break; // 이미 찾았으면 중단

            visited[i] = true;
            dfs(i, 1);
            visited[i] = false;
        }

        if (found) System.out.println(1);
        else System.out.println(0);
    }

    static void dfs(int current, int depth) {
        if (depth == 5) {
            found = true;
            return;
        }

        for (int next: graph.get(current)) {
            if (!visited[next]) {
                visited[next] = true; // 방문 처리
                dfs(next, depth + 1);
                visited[next] = false;

                if (found) return;
            }
        }
    }
}