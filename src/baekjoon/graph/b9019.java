package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b9019 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            sb.append(bfs(A, B)).append("\n");
        }
        System.out.println(sb);
    }

    static String bfs(int start, int target) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[10000];
        int[] parent = new int[10000]; // 경로 역추적을 위한 부모 노드 저장
        char[] command = new char[10000]; // 해당 노드로 오기 위해 사용한 명령어 저장

        q.offer(start);
        visited[start] = true;

        while(!q.isEmpty()) {
            int cur = q.poll();

            if (cur == target) {
                // 목표 도달 시 역추적하여 경로 생성
                return getPath(start, target, parent, command);
            }

            // 4가지 명령어 실행(D, S, L, R)
            // 1. D
            int next = (cur * 2) % 10000;
            if (!visited[next]) {
                visited[next] = true;
                parent[next] = cur;
                command[next] = 'D';
                q.offer(next);
            }
            // 2. S
            next = (cur == 0) ? 9999: cur - 1;
            if (!visited[next]) {
                visited[next] = true;
                parent[next] = cur;
                command[next] = 'S';
                q.offer(next);
            }
            // 3. L
            next = (cur % 1000) * 10 + (cur / 1000);
            if (!visited[next]) {
                visited[next] = true;
                parent[next] = cur;
                command[next] = 'L';
                q.offer(next);
            }
            // 4. R
            next = (cur % 10) * 1000 + (cur / 10);
            if (!visited[next]) {
                visited[next] = true;
                parent[next] = cur;
                command[next] = 'R';
                q.offer(next);
            }
        }
        return "";
    }
    // 역추적
    static String getPath(int start, int target, int[] parent, char[] command) {
        StringBuilder sb = new StringBuilder();
        int cur = target;
        while (cur != start) {
            sb.append(command[cur]);
            cur = parent[cur];
        }
        return sb.reverse().toString();
    }
}
