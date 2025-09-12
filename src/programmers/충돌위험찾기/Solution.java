package programmers.충돌위험찾기;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        // 1. 모든 로봇의 시간대별 경로 생성
        List<List<int[]>> robotPaths = new ArrayList<>();
        int maxTime = 0;

        for (int[] route: routes) {
            List<int[]> path = new ArrayList<>();
            // 첫 번째 포인트의 좌표를 0초 위치로 추가
            int[] startPoint = points[route[0] - 1];
            path.add(new int[]{startPoint[0], startPoint[1]});

            // 경로의 각 구간(point -> nextPoint)을 순회
            for (int i = 0; i < route.length - 1; i++) {
                int[] currentPos = points[route[i] - 1];
                int[] nextPost = points[route[i+1] - 1];

                int r1 = currentPos[0], c1 = currentPos[1];
                int r2 = nextPost[0], c2 = nextPost[1];

                // r 좌표 우선 이동
                while (r1 != r2) {
                    r1 += (r2 > r1) ? 1 : -1;
                    path.add(new int[]{r1, c1});
                }

                // c좌표 이동
                while (c1 != c2) {
                    c1 += (c2 > c1) ? 1: -1;
                    path.add(new int[]{r1, c1});
                }
            }
            robotPaths.add(path);
            maxTime = Math.max(maxTime, path.size() - 1);
        }

        // 2 시간대별 충돌 확인
        int totalCollisions = 0;
        for (int t = 0; t <= maxTime; t++) {
            // Map<"r,c", count> 형태로 해당 시간에 위치별 로봇 수를 기록
            Map<String, Integer> positionsThisSecond = new HashMap<>();

            for (List<int[]> path : robotPaths) {
                if (t < path.size()) {
                    int[] pos = path.get(t);
                    String key = pos[0] + "," + pos[1];
                    positionsThisSecond.put(key, positionsThisSecond.getOrDefault(key, 0) + 1);
                }
            }

            // 2대 이상 모인 경우, 충돌횟수 계산
            for (int count: positionsThisSecond.values()) {
                if (count > 1) {
                    totalCollisions++;
                }
            }
        }
        return totalCollisions;
    }
}
