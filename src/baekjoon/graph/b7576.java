package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class b7576 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] MN = br.readLine().split(" ");
        int M = Integer.parseInt(MN[0]);
        int N = Integer.parseInt(MN[1]);
        int[][] box = new int[N][M];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < M; j++) {
                box[i][j] = Integer.parseInt(line[j]);
                if (box[i][j] == 1) {
                    queue.offer(new int[] {i, j, 0});
                }
            }
        }

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        int answer = 0;
        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            answer = current[2];
            for (int d = 0; d < 4;d++) {
                int nr = current[0] + dr[d];
                int nc = current[1] + dc[d];
                if (nr >= 0 && nr < N && nc >= 0 && nc < M && box[nr][nc] == 0) {
                    box[nr][nc] = 1;
                    queue.offer(new int[] {nr, nc, current[2] + 1});
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (box[i][j] == 0) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(answer);

    }
}
