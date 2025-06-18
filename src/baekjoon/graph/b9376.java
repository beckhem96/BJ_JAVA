package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b9376 {
    static int H, W;
    static char[][] map;
    static int[][] dist0, dist1, dist2;
    static int[] dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H + 2][W + 2];
            for (char[] row : map) Arrays.fill(row, '.');

            List<int[]> prisoners = new ArrayList<>();
            for (int i = 1; i <= H; i++) {
                String line = br.readLine();
                for (int j = 1; j <= W; j++) {
                    map[i][j] = line.charAt(j - 1);
                    if (map[i][j] == '$') prisoners.add(new int[]{i, j});
                }
            }

            dist0 = bfs(0, 0);
            dist1 = bfs(prisoners.get(0)[0], prisoners.get(0)[1]);
            dist2 = bfs(prisoners.get(1)[0], prisoners.get(1)[1]);

            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < H + 2;i++) {
                for (int j = 0; j < W +2; j++) {
                    // 세명이 다 -1이 아니다 -> 세명다 여길 지났다 -> 세명이 만났다. 그것도 최소 문을 연 상태로(함수 bfs의 결과이니)
                    if (dist0[i][j] < 0 || dist1[i][j] < 0 || dist2[i][j] < 0) continue; // 한명이라도 -1이면 안지났다는 애기 -> 탈출 못함
                    int total = dist0[i][j] + dist1[i][j] + dist2[i][j]; // 지나온 문 다 더함
                    if (map[i][j] == '#') total -= 2; // 근데 현재 위치가 문이다? -> 3명이 중복으로 열었을 경우이니 -2를 해준다.
                    ans = Math.min(ans, total);
                }
            }
            System.out.println(ans);
        }
    }

    static int[][] bfs(int sy, int sx) {
        int[][] dist = new int[H + 2][W + 2];
        for (int[] row: dist) Arrays.fill(row, -1);

        Deque<int[]> dq = new ArrayDeque<>();
        // 시작점
        dq.addFirst(new int[]{sy, sx});
        dist[sy][sx] = 0; // 지났으니 0

        while (!dq.isEmpty()) { // deque가 비어있을 때까지
            int[] cur = dq.pollFirst();
            int y = cur[0], x = cur[1];

            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d], nx = x + dx[d];
                if (ny < 0 || ny >= H + 2 || nx < 0 || nx >= W + 2) continue; // 벗어나면 pass
                if (map[ny][nx] == '*') continue; // 벽이어도 pass

                int cost = dist[y][x] + (map[ny][nx] == '#' ? 1 : 0); // 문이면  + 1, 아니면 0 -> 0-1 bfs

                if (dist[ny][nx] < 0 || cost < dist[ny][nx]) { // 지난 적 없거나, 원래 있던 비용에 비해 현재 비용(cost)가 더 저렴하면
                    dist[ny][nx] = cost; // 원래 있던 비용에 현재 비용을 넣는다.
                    if (map[ny][nx] == '#') dq.addLast(new int[]{ny, nx}); // 현재 위치가 문이면 덱 뒤쪽에 추가하는데, 이유는 문을 최소로 지나야하기 때문에 우선순위가 낮아서 그럼
                    // 덱 뒤로 가면 나중에 실행 시켜 우선순위가 낮아진다 -> 비용이 저렴한 경로를 먼저 탐색하고 비용이 더 드는 경로를 나중에 탐색해서 가장 적게 문을 열고 도달하는 경로를 자동으로 계산
                    // 시간 복잡도 문제다. 0-1 bfs의 탐색 우선순위는 비용 적은 경로 먼저하기 때문에 시간 복잡도가 O(V + E)이라 백준에서 빠르게 통과한다.
                    // 근데 addFirst만 하면 시간초과 남, 마지막에 넣으면 쓸데 없는 경로(비용이 이미 있는 것보다 큰 경우)를 방문할 일이 없음 왜? -> if (dist[ny][nx] < 0 || cost < dist[ny][nx]) 때문에
                    else dq.addFirst(new int[]{ny, nx});
                }
            }
        }

        return dist;
    }
}
