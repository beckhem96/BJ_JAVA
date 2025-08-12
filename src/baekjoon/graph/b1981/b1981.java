package baekjoon.graph.b1981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b1981 {
    static int n;
    static int[][] grid;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];

        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                numSet.add(grid[i][j]);
            }
        }

        List<Integer> uniqueNums = new ArrayList<>(numSet);
        Collections.sort(uniqueNums);

        int left = 0, right = 0;
        int result = Integer.MAX_VALUE;

        while (right < uniqueNums.size()) {
            int minVal = uniqueNums.get(left);
            int maxVal = uniqueNums.get(right);

            if (bfs(minVal, maxVal)) {
                // 경로를 찾았으면 정답 갱신하고 더 좁은 범위를 시도(left 증가)
                result = Math.min(result, maxVal - minVal);
                left++;
            } else {
                // 경로 못 찾았으면, 더 넣은 범위가 필요 (right 증가)
                right++;
            }
        }
        System.out.println(result);
    }

    static boolean bfs(int minVal, int maxVal) {
        if (grid[0][0] < minVal || grid[0][0] > maxVal) {
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            if (r == n - 1 && c == n - 1) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dx[i];
                int nc = c + dy[i];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    int nextVal = grid[nr][nc];
                    if (nextVal >= minVal && nextVal <= maxVal) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        return false;
    }
}
