package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b9663 {
    static int N;
    static int count = 0;
    static int[] queenPositions;
    public static void main(String[] atgs) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        // 같은 행에 퀸이 있으면 안 됨
        // 같은 열에 퀸이 있으면 안 됨
        // 같은 대각선에 퀸이 있으면 안 됨
        queenPositions = new int[N];
        placeQueen(0);
    }

    static void placeQueen(int row) {
        if (row == N) {
            count++;
            return;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                queenPositions[row] = col;
                placeQueen(row + 1);
            }
        }
    }

    static boolean isSafe(int row, int col) {
        for (int prevRow = 0; prevRow < row; prevRow++) {
            int prevCol = queenPositions[prevRow];

            if (prevCol == col) {
                return false;
            }

            if (Math.abs(row - prevRow) == Math.abs(col - prevCol)) {
                return false;
            }
        }
        return true;
    }
}
