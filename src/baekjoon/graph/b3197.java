package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b3197 {
    static int R, C;
    static char[][] map;
    static int[] dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};
    static Queue<int[]> swanQ = new LinkedList<>();
    static Queue<int[]> nextSwanQ = new LinkedList<>();
    static Queue<int[]> waterQ = new LinkedList<>();
    static boolean[][] dist;
    public static void main(String[] args) throws IOException {
        int day = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        List<int[]> swans = new ArrayList<>(); // 백조 위치
        dist = new boolean[R][C];
        for (int i = 0; i < R;i++) {
            String line = br.readLine();
            for (int j = 0; j < C;j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'L') {
                    swans.add(new int[]{i, j}); // 백조 위치 저장
                    waterQ.offer(new int[]{i, j}); // 백조 위치도 물로 취급해서 넣어줘야 함, 얼음에 둘러쌓인 경우 물 취급을 해줘야 얼음이 녹음
                } else if (map[i][j] == '.') {
                    waterQ.offer(new int[]{i, j});
                }
            }
        }
        swanQ.offer(swans.get(0));
        dist[swans.get(0)[0]][swans.get(0)[1]] = true; // 한번 지나간 거리는 다시 가지 않도록
        while (!bfs(swans.get(1))) {
            melt();
            swanQ = nextSwanQ;
            nextSwanQ = new LinkedList<>();
            day += 1;
        }
        System.out.println(day);
    }
    // bfs로 돌고 만났어? 안만났어 그럼 녹아, 녹고 만났어? 반복
    static boolean bfs(int[] swan2) {

        while(!swanQ.isEmpty()) {
            int[] cur = swanQ.poll();
            int y = cur[0], x = cur[1];
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k], ny = y + dy[k];
                if (nx < 0 || nx >= C || ny < 0 || ny >= R || dist[ny][nx]) continue;
                dist[ny][nx] = true; // 한번 지나간 거리는 다시 가지 않도록
                if (nx == swan2[1] && ny == swan2[0]) return true;
                else if (map[ny][nx] == '.') { // 물 -> 지금 탐색
                    swanQ.offer(new int[]{ny, nx});
                } else if (map[ny][nx] == 'X') { // 얼음 -> 다음날 탐색
                    nextSwanQ.offer(new int[]{ny, nx});
                }
            }
        }
        return false;
    }

    static void melt(){
        int size = waterQ.size();
        for (int i = 0; i < size; i++) { // 딱 현재 사이즈만큼 돌고
            int[] cur = waterQ.poll();
            int y = cur[0], x = cur[1];
            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d], nx = x + dx[d];
                if (ny < 0 || ny >= R || nx < 0 || nx >= C) continue;
                if (map[ny][nx] == 'X') {
                    map[ny][nx] = '.'; // 이제 녹은 것들은
                    waterQ.offer(new int[]{ny, nx}); // 넣어서 나중에 녹일 수 있게
                }
            }
        }
    }
}
