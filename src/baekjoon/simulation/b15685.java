package baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class b15685 {
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};
    static boolean[][] map = new boolean[101][101];

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()); // 시작 방향
            int g = Integer.parseInt(st.nextToken()); // 세대

            drawDragonCurve(x, y, d, g);
        }

        // 1x1 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인지 확인
        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (map[i][j] && map[i + 1][j] && map[i][j + 1] && map[i + 1][j + 1]) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    static void drawDragonCurve(int x, int y, int d, int g) {
        List<Integer> dirs = new ArrayList<>();
        dirs.add(d); // 0세대 방향 추가

        // 1. 드래곤 커브의 세대만큼 방향 수열 만들기
        for (int i = 0; i < g; i++) {
            int size = dirs.size();
            // 뒤에서부터 거꾸로 읽으면서 +1 (90도 시계 방향 회전)
            for (int j = size - 1; j >= 0; j--) {
                dirs.add((dirs.get(j) + 1) % 4);
            }
        }

        // 2. 완성된 방향 수열 바탕으로 지도에 점 찍기
        map[x][y] = true; // 시작점 마킹
        int cx = x;
        int cy = y;

        for (int dir: dirs) {
            cx += dx[dir];
            cy += dy[dir];
            map[cx][cy] = true; // 이동한 꼭짓점 마킹
        }
    }
}
