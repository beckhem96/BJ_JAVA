package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b12100 {
    static int max = 0;
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int[][] initialBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                initialBoard[i][j] = Integer.parseInt(line[j]);
            }
        }
        dfs(0, initialBoard);
        System.out.println(max);
    }

    static void dfs(int cnt, int[][] board) {
        if (cnt == 5) {
            int currentMax = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    currentMax = Math.max(currentMax, board[i][j]);
                }
            }
            max = Math.max(max, currentMax);
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            int[][] nextBoard = move(dir, board);
            dfs(cnt+1, nextBoard);
        }
    }

    static int[][] move(int dir, int[][] board) {
        int[][] newBoard = new int[N][N];
        if (dir == 0 || dir == 1) {
            for (int j = 0; j < N; j++) {
                List<Integer> line = new ArrayList<>();
                List<Integer> mergedLine = new ArrayList<>();

                // 1. 0이 아닌 블록 수집
                if (dir == 0) {
                    for (int i = 0; i < N; i++) if (board[i][j] != 0) line.add(board[i][j]);
                } else {
                    for (int i = N - 1; i >= 0; i--) if (board[i][j] != 0) line.add(board[i][j]);
                }

                // 2. 블록 합치기
                int k = 0;
                while (k < line.size()) {
                    if (k + 1 < line.size() && line.get(k).equals(line.get(k+1))) { // 다음 블록과 같다면
                        mergedLine.add(line.get(k) * 2);
                        k += 2;
                    } else {
                        mergedLine.add(line.get(k));
                        k += 1;
                    }
                }
                // 3. 새 보드에 배치
                if (dir == 0) { // 위: 위쪽부터 채움
                    for (int i = 0; i < mergedLine.size(); i++) newBoard[i][j] = mergedLine.get(i);
                } else {
                    for (int i = 0; i < mergedLine.size(); i++) newBoard[N - 1 - i][j] = mergedLine.get(i);
                }
            }
        } else {
            for (int i = 0; i < N; i++) {
                List<Integer> line = new ArrayList<>();
                List<Integer> mergedLine = new ArrayList<>();

                if (dir == 2) {
                    for (int j = 0; j < N; j++) if (board[i][j] != 0) line.add(board[i][j]);
                } else {
                    for (int j = N - 1; j >= 0; j--) if (board[i][j] !=0) line.add(board[i][j]);
                }

                int k = 0;
                while (k < line.size()) {
                    if (k + 1 < line.size() && line.get(k).equals(line.get(k+1))) {
                        mergedLine.add(line.get(k) * 2);
                        k += 2;
                    } else {
                        mergedLine.add(line.get(k));
                        k += 1;
                    }
                }

                if (dir == 2) {
                    for (int j = 0; j < mergedLine.size(); j++) newBoard[i][j] = mergedLine.get(j);
                } else {
                    for (int j = 0; j < mergedLine.size(); j++) newBoard[i][N - 1 -j] = mergedLine.get(j);
                }
            }
        }
        return newBoard;
    }
}
