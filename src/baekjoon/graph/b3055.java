package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b3055 {
    static int N, M;
    static char[][] map;
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static Queue<Point> waterQueue = new LinkedList<>();
    static Queue<Point> hedgehogQueue = new LinkedList<>();

    static  class Point {
        int r, c, time;

        public Point(int r, int c, int time) {
            this.r = r;
            this.c = c;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'S') {
                    hedgehogQueue.offer(new Point(i, j, 0));
                } else if (map[i][j] == '*') {
                    waterQueue.add(new Point(i, j, 0));
                }
            }
        }
        bfs();
    }

    public static void bfs() {
        while (!hedgehogQueue.isEmpty()) {
            // 1. 물 먼저 확장 (현재 큐에 있는 물의 양만큼만 반복 - 1분치)
            int waterSize = waterQueue.size();
            for (int i = 0; i < waterSize; i++) {
                Point w = waterQueue.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = w.r + dr[d];
                    int nc = w.c + dc[d];
                    if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                        // 물은 빈 곳이나 고슴도치가 있는 곳으로 확장 가능
                        // 돌이나 비버의 굴은 침범 불가
                        if (map[nr][nc] == '.') {
                            map[nr][nc] = '*';
                            waterQueue.add(new Point(nr, nc, 0));
                        }
                    }
                }
            }

            // 2. 고슴도치 이동 (현재 큐에 있는 고스도치 수만큼 반복 - 1 분치)
            int hedgehogSize = hedgehogQueue.size();
            for (int i = 0; i < hedgehogSize; i++) {
                Point h = hedgehogQueue.poll();

                for (int d = 0; d < 4; d++) {
                    int nr = h.r + dr[d];
                    int nc = h.c + dc[d];
                    if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                        // 비버의 굴 도착
                        if (map[nr][nc] == 'D') {
                            System.out.println(h.time + 1);
                            return;
                        }

                        // 빈 곳으로 이동
                        if (map[nr][nc] == '.') {
                            map[nr][nc] = 'S'; // 방문 처리 (고스도치 위치 표시)
                            hedgehogQueue.add(new Point(nr, nc, h.time + 1));
                        }
                    }
                }
            }
        }
        System.out.println("KAKTUS");
    }
}
