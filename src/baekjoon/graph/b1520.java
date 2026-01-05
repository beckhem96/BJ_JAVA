package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class b1520 {
    static int M, N;
    static int[][] grid;
    static int[][] dp;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        M = Integer.parseInt(line[0]);
        N = Integer.parseInt(line[1]);
        grid = new int[M][N];
        dp = new int[M][N];
        for (int i = 0; i < M; i++) {
            line = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(line[j]);
                dp[i][j] = -1;
            }
        }
        System.out.println(dfs(0, 0));
    }

    static int dfs(int r, int c) {
        if (r == M - 1 && c == N - 1) {
            return 1;
        }

        if (dp[r][c] != -1) {
            return dp[r][c];
        }

        dp[r][c] = 0;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >= 0 && nr < M && nc >= 0 && nc < N) {
                if (grid[r][c] > grid[nr][nc]) {
                    dp[r][c] += dfs(nr, nc);
                }
            }
        }

        return dp[r][c];
    }
}
