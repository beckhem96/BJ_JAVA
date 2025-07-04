package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b4991 {
    static int[] dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};
    static char[][] map;
    static int[][] dist;
    static int H, W;
    static boolean[] visited;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] arg) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            if (W == 0 && H == 0) break;
            List<Node> q = new ArrayList<>();
            map = new char[H][W];
            for (int i = 0; i<H;i++) {
                String line = br.readLine();
                for (int j = 0;j<W;j++) {
                    map[i][j] = line.charAt(j);
                    if (map[i][j] == 'o') q.add(0, new Node(i, j)); // 시작점과
                    else if  (map[i][j] == '*') q.add(new Node(i, j)); // 더러운 칸
                }
            }

            int size = q.size();
            dist = new int[size][size]; // 시작점 + 도달해야하는 칸 수(더러운칸)

            // 도달 가능 한지 확인
            boolean impossible = false;

            // 거리 계산, 시작 점에서 더럽칸1, 더럽칸1에서 더럽칸2...등
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == j) continue;
                    dist[i][j] = bfs(q.get(i), q.get(j)); // 거리 계산 후 dist 배열에 저장, i
                    if (dist[i][j] == -1) impossible = true; // 도달 못하는게 있다? -> -1로 끝
                }
            }
            if (impossible) {
                System.out.println(-1);
                continue;
            }

            // 순열 탑색
            visited = new boolean[size];
            visited[0] = true;
            min = Integer.MAX_VALUE;

            // 최소 거리 구해야함
            dfs(0, 0, 0, size);
            System.out.println(min);
        }
    }

    static void dfs(int cur, int cnt, int sum, int size) {
        if (cnt == size - 1) { // 더럽칸 청소 끝
            min = Math.min(min, sum);
            return;
        }
        // 이부분 다시 봐야 할듯
        for (int i = 1; i < size; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, cnt + 1, sum + dist[cur][i], size);
                visited[i] = false;
            }
        }
    }

    static int bfs(Node start, Node end) {
        boolean[][] visited = new boolean[H][W];
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(start.x, start.y, 0));
        visited[start.x][start.y] = true;

        while (!q.isEmpty()) { // 이 함수 q에 있는건 방문하지 않은 node 정보
            Node cur = q.poll();
            if (cur.x == end.x && cur.y == end.y) return cur.cnt; // 도달 했을 때(더럽칸 갔을때)의 이동거리 return
            for (int k = 0;k<4;k++) {
                int nx = cur.x + dx[k], ny = cur.y + dy[k];
                if (nx < 0 || nx >= W || ny < 0 || ny >= H || map[ny][nx] == 'x') {
                    continue;
                }
                if (!visited[nx][ny] && map[nx][ny] != 'x') { // 방문 안했고 벽이 아니면
                    visited[nx][ny] = true;
                    q.add(new Node(nx, ny, cur.cnt + 1));
                }
            }
        }
        return -1;
    }


    static class Node {
        int x, y, cnt; // 세로 위치

        Node(int x, int y) { this.x = x; this.y = y;}
        Node(int x, int y, int cnt) { this.x = x; this.y = y; this.cnt = cnt;}
    }
}
