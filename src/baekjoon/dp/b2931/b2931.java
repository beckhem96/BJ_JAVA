package baekjoon.dp.b2931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b2931 {
    static char[][] map;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        map = new char[R][C];

        int startR = -1, startC = -1;
        for (int i = 0; i< R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'M') {
                    startR = i;
                    startC = j;
                }
            }
        }
        // 1. 첫 흐름 찾기
        int r = -1, c = -1, dir = -1;
        for (int i = 0; i < 4; i++) {
            int nr = startR + dr[i];
            int nc = startC + dc[i];
            if (nr >= 0 && nr < R && nc >= 0 && nc < C && map[nr][nc] != '.' && map[nr][nc] != 'Z') {
                r = nr;
                c = nc;
                dir = i;
                break;
            }
        }

        // 2. 시뮬레이션
        while (map[r][c] != '.') {
            char block = map[r][c];

            // 현재 진행 방향(dir)과 블록 종류에 따라 다음 진행 방향(nextDir)을 결정
            if (block == '1') {
                dir = (dir == 0) ? 3 : 1;
            } else if (block == '2') {
                dir = (dir == 1) ? 3 : 0;
            } else if (block == '3') {
                dir = (dir == 1) ? 2 : 0;
            } else if (block == '4') {
                dir = (dir == 0) ? 2 : 1;
            }
            // block이 '|', '-', '+' 일때는 방향이 바뀌지 않으므로 dir을 그대로 사용

            // 계산된 다음 방향으로 이동
            r += dr[dir];
            c += dc[dir];
        }

        boolean[] connected = new boolean[4];
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr < 0 || nr >= R || nc < 0 || nc >= C || map[nr][nc] == '.')
                continue;

            char pipe = map[nr][nc];
            if (i == 0 && (pipe == '|' || pipe == '+' || pipe == '1' || pipe == '4')) connected[0] = true;
            else if (i == 1 && (pipe == '|' || pipe == '+' || pipe == '2' || pipe == '3')) connected[1] = true;
            else if (i == 2 && (pipe == '-' || pipe == '+' || pipe == '1' || pipe == '2')) connected[2] = true;
            else if (i == 3 && (pipe == '-' || pipe == '+' || pipe == '3' || pipe == '4')) connected[3] = true;
        }

        System.out.print((r + 1) + " " + (c + 1) + " ");
        if (connected[0] && connected[1] && connected[2] && connected[3]) System.out.println("+");
        else if (connected[0] && connected[1]) System.out.println("|");
        else if (connected[2] && connected[3]) System.out.println("-");
        else if (connected[1] && connected[3]) System.out.println("1");
        else if (connected[0] && connected[3]) System.out.println("2");
        else if (connected[0] && connected[2]) System.out.println("3");
        else if (connected[1] && connected[2]) System.out.println("4");
    }
}
