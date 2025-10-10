package programmers.전력망을둘로나누기;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    List<Integer>[] graph;

    public int solution(int n, int[][] wires) {
        int minDiff = n;
        graph = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < wires.length; i++) {
            for (int k = 1; k <= n; k++) graph[k].clear();

            for (int j = 0; j < wires.length; j++) {
                if (i == j) continue;
                int v1 = wires[j][0];
                int v2 = wires[j][1];
                graph[v1].add(v2);
                graph[v2].add(v1);
            }

            int count = bfs(wires[i][0], n);
            int diff = Math.abs(count - (n - count));
            minDiff = Math.min(minDiff, diff);
        }
        return minDiff;
    }

    private int bfs(int start, int n) {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next: graph[current]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                    count++;
                }
            }
        }
        return count;
    }
}
