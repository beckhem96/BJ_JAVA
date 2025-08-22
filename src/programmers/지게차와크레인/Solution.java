package programmers.지게차와크레인;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int solution(String[] storage, String[] requests) {
        int n = storage.length, m = storage[0].length();
        char[][] board = new char[n][m];

        for (int i = 0; i < n; i++) board[i] = storage[i].toCharArray();

        for (String req: requests) {
            char ch = req.charAt(0);
            if (req.length() == 2) {
                // 크레인: 해당 문자 전부 제거
                for (int i = 0; i < n;i++)
                    for (int j = 0; j < m;j++)
                        if (board[i][j] == ch) board[i][j] = '.';
            } else {
                // 지게차: 현재 시점 접근 가능한 것만 제거
                boolean[][] outside = buildOutside(board);
                List<int[]> toDel = new ArrayList<>();
                for (int i = 0; i < n;i++) {
                    for (int j = 0; j < m; j++) {
                        if (board[i][j] != ch) continue;
                        int pi = i + 1, pj = j + 1; // 패딩 좌표
                        if (outside[pi+1][pj] || outside[pi-1][pj] || outside[pi][pj+1] || outside[pi][pj-1]) {
                            toDel.add(new int[]{i, j});
                        }
                    }
                }
                for (int[] p: toDel) board[p[0]][p[1]] = '.';
            }
        }
        int remain = 0;
        for (int i =0; i<n; i++) {
            for (int j = 0;j<m;j++) {
                if (board[i][j] != '.') remain++;
            }
        }
        return remain;
    }

    // 패딩 격자에서 외부 공기 BFS ('.'만 통과)
    private boolean[][] buildOutside(char[][] board) {
        int n = board.length, m = board[0].length;
        int H = n + 2, W = m + 2;
        boolean[][] vis = new boolean[H][W];
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{0, 0});
        vis[0][0] = true;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!dq.isEmpty()) {
            int[] cur = dq.poll();
            int r = cur[0], c = cur[1];
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (nr < 0 || nr >= H || nc <0 || nc >= W) continue;
                if (vis[nr][nc]) continue;

                // 패딩 내부가 실제 창고라면   빈칸만 이동
                if (1 <= nr && nr <= n && 1 <= nc && nc <=m) {
                    if (board[nr - 1][nc - 1] != '.') continue;
                }
                vis[nr][nc] = true;
                dq.add(new int[]{nr, nc});
            }
        }
        return vis;
    }
}
