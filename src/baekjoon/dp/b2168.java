package baekjoon.dp;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b2168 {
    static int N, M, K;
    static char[][] board;
    static String word;
    static int[][][] dp;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int wordLen;
    public static void main(String[] args)  throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }
        word = br.readLine();
        wordLen = word.length();

        // DP 테이블을 -1 (아직 계산 안 됨)로 초기화
        dp = new int[N][M][wordLen];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        int totalPaths = 0;
        // 단어의 첫 글자와 일치하는 모든 위치에서 탐색 시작
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == word.charAt(0)) {
                    totalPaths += dfs(i, j, 0);
                }
            }
        }
        System.out.println(totalPaths);
    }

    static int dfs(int r, int c, int index) {
        // 기저 조건: 단어의 마지막 글자까지 완성했다면 경로 1개를 찾은 것
        if (index == wordLen - 1) {
            return 1;
        }
        // 메모이제이션 확인: 이미 계산된 상태라면 저장된 값을 반환
        if (dp[r][c][index] != -1) {
            return dp[r][c][index];
        }
        // 현재 상태에서 만들 수 있는 경로의 수를 0으로 초기화
        dp[r][c][index] = 0;
        // 상하좌우 네 방향으로 탐색
        for (int d = 0; d < 4; d++) {
            // 1칸 부터 K칸 까지 이동
            for (int k = 1; k <= K; k++) {
                int nr = r + dr[d] * k;
                int nc = c + dc[d] * k;
                // 맵 범위를 벗어나지 않는지 확인
                if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                    // 다음 글자가 일치하는 경우
                    if (board[nr][nc] == word.charAt(index + 1)) {
                        dp[r][c][index] += dfs(nr, nc, index + 1);
                    }
                }
            }
        }
        return dp[r][c][index];
    }
}
