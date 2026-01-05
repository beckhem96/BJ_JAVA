package baekjoon.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class b1184 {
    static int N;
    static int[][] grid;
    static int[][] sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        grid = new int[N + 1][N + 1];
        sum = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 1; j <= N; j++) {
                grid[i][j] = Integer.parseInt(line[j - 1]);
                // S[i][j] = grid[i][j] + S[i-1][j] + S[i][j-1] - S[i-1][j-1]
                sum[i][j] = grid[i][j] + sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1];
            }
        }

        long totalWayCount = 0; // 정답은 int 범위를 넘을 수 있으므로 long

        // 2. 모든 분할점 (r, c) 순회 (O(N^2))
        // (r, c)는 좌상단 사분면의 우하단 꼭짓점
        for (int r = 1; r < N; r++) {
            for (int c = 1; c < N; c++) {
                // 각 사분면의 사각형 합들을 저장할 맵
                Map<Integer, Integer> mapTL = new HashMap<>();
                Map<Integer, Integer> mapBR = new HashMap<>();
                Map<Integer, Integer> mapTR = new HashMap<>();
                Map<Integer, Integer> mapBL = new HashMap<>();
                // 3. 각 사분면의 모든 사각형 합 계산 (O(N^2))

                // 최상단(TL) 사각형들 계산: (r1, c1) ~ (r, c)
                for (int r1 = 1; r1 <= r; r1++) {
                    for (int c1 = 1; c1 <= c; c1++) {
                        int rectSum = getSum(r1, c1, r, c);
                        mapTL.put(rectSum, mapTL.getOrDefault(rectSum, 0) + 1);
                    }
                }

                // 우하단(BR) 사각형들 계산: (r+1, c+1) ~ (r2, c2)
                for (int r2 = r + 1; r2 <= N; r2++) {
                    for (int c2 = c + 1; c2 <= N; c2++) {
                        int rectSum = getSum(r+1, c+1, r2, c2);
                        mapBR.put(rectSum, mapBR.getOrDefault(rectSum, 0) + 1);
                    }
                }

                // 우상단(TR) 사각형들 계산: (r1, c+1) ~ (r, c2)
                for (int r1 = 1; r1 <= r; r1++) {
                    for (int c2 = c+1; c2 <= N; c2++) {
                        int rectSum = getSum(r1, c + 1, r, c2);
                        mapTR.put(rectSum, mapTR.getOrDefault(rectSum, 0) + 1);
                    }
                }

                // 좌하단(BL) 사각형들 계산: (r+1, c1) ~ (r2, c)
                for (int r2 = r + 1; r2 <= N; r2++) {
                    for (int c1 = 1; c1 <= c; c1++) {
                        int rectSum = getSum(r + 1, c1, r2, c);
                        mapBL.put(rectSum, mapBL.getOrDefault(rectSum, 0) + 1);
                    }
                }

                // 4.경우의 수 합산
                // Case 1: (TL ~ BR)
                for (int s : mapTL.keySet()) {
                    if (mapBR.containsKey(s)) {
                        totalWayCount += (long) mapTL.get(s) * mapBR.get(s);
                    }
                }

                // Case 2: (TR ~ BL)
                for (int s : mapTR.keySet()) {
                    if (mapBL.containsKey(s)) {
                        totalWayCount += (long) mapTR.get(s) * mapBL.get(s);
                    }
                }
            }
        }
        System.out.println(totalWayCount);
    }

    static int getSum(int r1, int c1, int r2, int c2) {
        return sum[r2][c2] - sum[r1 - 1][c2] - sum[r2][c1 - 1] + sum[r1 - 1][c1 - 1];
    }
}
