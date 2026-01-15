package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b14499 {
    static int N, M, x, y, K;
    static int[][] map;
    // 동(1), 서(2), 북(3), 남(4) 순서에 맞춘 델타 배열 (0번 인덱스는 더미)
    static int[] dx = {0, 0, 0, -1, 1};
    static int[] dy = {0, 1, -1, 0, 0};
    // dice[0]: 윗면, dice[1]: 북, dice[2]: 동, dice[3]: 서, dice[4]: 남, dice[5]: 바닥
    static int[] dice = new int[6];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");

        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);
        x = Integer.parseInt(line[2]);
        y = Integer.parseInt(line[3]);
        K = Integer.parseInt(line[4]);
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            line = br.readLine().split(" ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        line = br.readLine().split(" ");
        for (int i = 0; i < K; i++) {
            int command = Integer.parseInt(line[i]);
            move(command);
        }
    }

    static void move(int dir) {
        int nx = x + dx[dir];
        int ny = y + dy[dir];
        // 범위를 벗어나면 무시
        if (nx < 0 || nx >= N || ny < 0 || ny >= M) return;

        // 주사위 굴리기 (면의 이동)
        roll(dir);

        // 좌표 업데이트
        x = nx;
        y = ny;

        // 지도와 주사위 바닥면 상호작용
        if (map[x][y] == 0) {
            map[x][y] = dice[5]; // 바닥면 -> 지도
        } else {
            dice[5] = map[x][y]; // 지도 -> 바닥면
            map[x][y] = 0;
        }

        System.out.println(dice[0]);
    }

    static void roll(int dir) {
        int temp = dice[0]; // 윗면 임시 저장

        switch (dir) {
            case 1: // 동쪽
                dice[0] = dice[3];
                dice[3] = dice[5];
                dice[5] = dice[2];
                dice[2] = temp;
                break;
            case 2: // 서쪽
                dice[0] = dice[2];
                dice[2] = dice[5];
                dice[5] = dice[3];
                dice[3] = temp;
                break;
            case 3: // 북쪽
                dice[0] = dice[4];
                dice[4] = dice[5];
                dice[5] = dice[1];
                dice[1] = temp;
                break;
            case 4: // 남쪽
                dice[0] = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[4];
                dice[4] = temp;
                break;
        }
    }
}
