package baekjoon.graph.b16954;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class b16954 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            board[i] = br.readLine().toCharArray();
        }

        // 9가지 이동 방향 (제자리 포함)
        int[] dr = {0, 0, 1, -1, 1, 1, -1, -1, 0};
        int[] dc = {1, -1, 0, 0, 1, -1, 1, -1, 0};

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{7, 0});

        // visited[time][r][c]: time초에 (r, c)에 방문했는지 여부
        // 시간의 흐름에 따라 같은 장소를 다시 방문할 수 있으므로 3차원 배열로 관리
        boolean[][][] visited = new boolean[9][8][8];
        visited[0][7][0] = true;

        int time = 0;
        while (!queue.isEmpty()) {
            // --- 레벨 단위 BFS: 현재 시간(time)에 가능한 모든 이동 처리 ---
            // 큐의 현재 사이즈만큼만 반복하면 현재 레벨(시간)의 노드만 처리하게 됨
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                int[] current = queue.poll();
                int r = current[0];
                int c = current[1];

                // 만약 현재 서 있는 위치(r, c)로 벽이 내려왔다면, 이 경로는 더이상 유효하지 않음
                if (r - time >= 0 && board[r - time][c] == '#') {
                    continue;
                }

                // 9가지 방향으로 시도
                for (int d = 0; d < 9; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    int nextTime = time + 1;

                    // 맵 범위 체크
                    if (nr < 0 || nr >=8 || nc < 0 || nc >= 8) {
                        continue;
                    }

                    // *** 핵심 로직 ***
                    // 내가 도착할 시간(nextTime)에 목적지(nr, nc)가 안전한지 확인
                    // 1. 목적지에 벽이 내려오는지 확인
                    if (nr -time >= 0 && board[nr - time][nc] == '#') {
                        continue;
                    }

                    // 8초가 넘으면 모든 벽이 사라져서 성공
                    if (nextTime > 8) {
                        System.out.println(1);
                        return;
                    }

                    // 해당 시간에 해당 위치를 방문한 적이 없다면 큐에 추가
                    if (!visited[nextTime][nr][nc]) {
                        visited[nextTime][nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            // --- 현재 시간(time)의 모든 이동이 끝난 후 시간 증가 ---
            time++;
        }
        // 이동 끝났는데 도달 못함
        System.out.println(0);
    }
}
