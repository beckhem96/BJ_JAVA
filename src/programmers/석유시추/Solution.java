package programmers.석유시추;

import java.util.*;

public class Solution {
    static int[][] poolIdGrid;
    static Map<Integer, Integer> poolSizeMap;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    public int solution(int[][] land) {
        int rowLength = land.length;
        int colLength = land[0].length;
        poolIdGrid = new int[rowLength][colLength];
        poolSizeMap = new HashMap<>();

        int currentPoolId = 1; // 석유 덩어리 ID는 1부터 시작
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                if (land[i][j] == 1 && poolIdGrid[i][j] == 0) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{i, j});
                    poolIdGrid[i][j] = currentPoolId;
                    int count = 1;
                    while (!queue.isEmpty()) {
                        int[] point = queue.poll();
                        int r = point[0];
                        int c = point[1];
                        for (int k = 0; k < 4; k++) {
                            int nr = r + dr[k];
                            int nc = c + dc[k];
                            if (nr < 0 || nr >= rowLength || nc < 0 || nc >= colLength) continue;
                            if (land[nr][nc] == 1 && poolIdGrid[nr][nc] == 0) {
                                poolIdGrid[nr][nc] = currentPoolId;
                                queue.offer(new int[]{nr, nc});
                                count++;
                            }
                        }
                    }
                    poolSizeMap.put(currentPoolId, count);
                    currentPoolId++;
                }
            }
        }
        // --- 2단계: 열 별 석유량 합산 ---
        int[] oilPerColumn = new int[colLength];
        for (int j = 0; j < colLength; j++) {
            Set<Integer> poolsInColumn = new HashSet<>();
            for (int i = 0; i < rowLength; i++) {
                if (poolIdGrid[i][j] != 0) {
                    poolsInColumn.add(poolIdGrid[i][j]);
                }
            }

            // Set에 있는 ID들의 크기를 합산
            for (int id: poolsInColumn) {
                oilPerColumn[j] += poolSizeMap.get(id);
            }
        }

        // --- 3단계: 최댓값 찾기 ---
        int maxOil = 0;
        for (int oil: oilPerColumn) {
            maxOil = Math.max(maxOil, oil);
        }
        return maxOil;
    }
}
