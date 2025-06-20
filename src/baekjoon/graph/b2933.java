package baekjoon.graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class b2933 {
    static int R, C, N;
    static char[][] map;
    static boolean[][] visited;
    static int[] height;
    static int[] dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; n++) {
            int h = R - Integer.parseInt(st.nextToken());
            throwStick(h, n % 2 == 0);
            visited = new boolean[R][C];
            checkCluster();
            fallDisconnectedClusters();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) sb.append(new String(map[i])).append('\n');
        System.out.println(sb);
    }

    public static void throwStick(int h, boolean left) {
        if (left) {
            for (int j = 0; j < C;j++) {
                if (map[h][j] == 'x') {
                    map[h][j] = '.';
                    break;
                }
            }
        } else {
            for (int j = C -1; j >= 0; j--) {
                if (map[h][j] == 'x') {
                    map[h][j] = '.';
                    break;
                }
            }
        }
    }

    public static void checkCluster() {
        Queue<int[]> q = new LinkedList<>();
        for (int j = 0; j < C; j++) {
            if (map[R-1][j] == 'x' && !visited[R-1][j]) {
                visited[R-1][j] = true;
                q.add(new int[]{R-1, j});
                while(!q.isEmpty()) {
                    int[] cur = q.poll();
                    int y = cur[0], x = cur[1];
                    for (int d = 0; d < 4; d++) {
                        int nx = x + dx[d], ny = y + dy[d];
                        if (nx < 0 || nx >= C || ny < 0 || ny >= R) continue;
                        if (!visited[ny][nx] && map[ny][nx] == 'x') {
                            visited[ny][nx] = true;
                            q.offer(new int[]{ny, nx});
                        }
                    }
                }
            }
        }
    }

    public static void fallDisconnectedClusters() {
        List<int[]> cluster = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (!visited[i][j] && map[i][j] == 'x') {
                    cluster.add(new int[]{i, j});
                    map[i][j] = '.';
                }
            }
        }

        if (cluster.isEmpty()) return;
        // 떨어지는 클러스터는 하나다. - 문제에 나와있음
        // 그럼 최소거리로(땅에서 가장 가까운 거리만큼) 떨어져야 한다.
        int minDrop = R;
        for (int[] p: cluster) {
            int y = p[0], x = p[1];
            int drop = 0;
            while (y + drop + 1 < R && map[y +drop + 1][x] == '.') drop++;
            minDrop = Math.min(minDrop, drop); // 가장 가까운 거리 구함, 왜냐하면 클러스터는 서로 연결되어 있기 때문에
            // 바닥에 가장 가까운게 닿으면 멀리 있다고 판단된 클러스터도 바닥에 닿은 것이기 때문
        }

        for (int[] p: cluster) {
            map[p[0]+minDrop][p[1]] = 'x';
        }
    }
}
