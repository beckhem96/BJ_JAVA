package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class b1707 {
    static ArrayList<Integer>[] graph;
    static int[] colors; // 0: 방문안함, 1: 빵강, -1: 파랑
    static boolean isBipartite;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());

        while (K-- > 0) {
            String[] line = br.readLine().split(" ");
            int V = Integer.parseInt(line[0]);
            int E = Integer.parseInt(line[1]);
            // 그래포 초기화 (1번부터 V번까지 사용)
            graph = new ArrayList[V +1];
            for (int i = 1; i<= V; i++) {
                graph[i] = new ArrayList<>();
            }
            // 간선 정보 입력
            for (int i = 0; i < E; i++) {
                line = br.readLine().split(" ");
                int u = Integer.parseInt(line[0]);
                int v = Integer.parseInt(line[1]);
                graph[u].add(v);
                graph[v].add(u); // 무방향 그래프
            }

            colors = new int[V + 1]; // 0으로 초기화 됨
            isBipartite = true;
            // 모든 정점에 대해 확인 (비연결 그래프일 수 있으므로)
            for (int i = 1; i <= V; i++) {
                if (colors[i] == 0) { // 아직 방문하지 않은 정점이라면 탐색 시작
                    if (!bfs(i)) {
                        isBipartite = false;
                        break;
                    }
                }
            }
            System.out.println(isBipartite ? "YES" : "NO");
        }
    }

    static boolean bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        colors[start] = 1; // 시작점 빨강(1)으로 칠함

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : graph[current]) {
                // 아직 방문하지 않은 인접 정점
                if (colors[next] == 0) {
                    colors[next] = colors[current] * -1; // 현재와 반대 색으로 칠함 (1 -> -1, -1 -> 1)
                    queue.offer(next);
                } else if (colors[next] == colors[current]) { // 이미 방문했는데, 현재 정점과 색이 같다면 이분 그래프 아님
                    return false;
                }
            }
        }
        return true;
    }
}
