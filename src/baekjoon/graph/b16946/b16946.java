package baekjoon.graph.b16946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b16946 {
    static int N, M;
    static int[][] map;
    static int[][] group;
    static Map<Integer, Integer> groupSzie = new HashMap<>();
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        group = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        // 1. 0 영역 그룹필 및 크기 계산
        int groupId = 1;
        for (int i = 0; i < N;i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0 && group[i][j] == 0) {
                    bfs(i, j, groupId++);
                }
            }
        }

        // 2. 벽 기준 합산
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1) {
                    Set<Integer> adjacentGroups = new HashSet<>();
                    for (int d = 0; d < 4; d++) {
                        int nr = i + dr[d];
                        int nc = j + dc[d];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < M && group[nr][nc] != 0) {
                            adjacentGroups.add(group[nr][nc]);
                        }
                    }
                    int sum = 1;
                    for (int groupIdSum: adjacentGroups) {
                        sum += groupSzie.get(groupIdSum);
                    }
                    sb.append(sum % 10);
                } else {
                    sb.append(0);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void bfs(int r, int c, int groupId) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        group[r][c] = groupId;
        int count = 1;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];

            for (int i =0; i < 4; i++) {
                int nr = curR + dr[i];
                int nc = curC + dc[i];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == 0 && group[nr][nc] == 0) {
                    queue.offer(new int[]{nr, nc});
                    group[nr][nc] = groupId;
                    count++;
                }
            }
        }
        groupSzie.put(groupId, count);
    }
}
