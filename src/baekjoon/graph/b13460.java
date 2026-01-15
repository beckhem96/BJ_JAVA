package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b13460 {
    static int N, M;
    static char[][] map;
    static boolean[][][][] visited;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    static class Marble{
        int rx, ry, bx, by, count;

        public Marble(int rx, int ry, int bx, int by, int count) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.count = count;
        }
    }

    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       StringTokenizer st  = new StringTokenizer(br.readLine());
       int N = Integer.parseInt(st.nextToken());
       int M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M][N][M];

        int rx = 0, ry = 0, bx = 0, by = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'R') {
                    rx = i; ry = j;
                } else if (map[i][j] == 'B') {
                    bx = i; by = j;
                }
            }
        }

        bfs(rx, ry, bx, by);
    }

    static void bfs(int rx, int ry, int bx, int by) {
        Queue<Marble> q = new LinkedList<>();
        q.offer(new Marble(rx, ry, bx, by, 1));
        visited[rx][ry][bx][by] = true;

        while (!q.isEmpty()) {
            Marble cur = q.poll();

            // 10번을 넘어가면 실패 (-1)
            if (cur.count > 10) {
                System.out.println(-1);
                return;
            }

            for (int i = 0; i < 4; i++) {
                // 파란 구슬 이동
                int nbx = cur.bx;
                int nby = cur.by;
                boolean blueHole = false;

                // 벽이나 구멍 만날 때까지 이동
                while (map[nbx + dx[i]][nby + dy[i]] != '#') {
                    nbx += dx[i];
                    nby += dy[i];
                    if (map[nbx][nby] == 'O') {
                        blueHole = true;
                        break;
                    }
                }

                // 빨간 구슬 이동
                int nrx = cur.rx;
                int nry = cur.ry;
                boolean redHole = false;

                while (map[nrx + dx[i]][nry + dy[i]] != '#') {
                    nrx += dx[i];
                    nry += dy[i];
                    if (map[nrx][nry] == 'O') {
                        redHole = true;
                        break;
                    }
                }
                // 파란구슬이 구멍에 빠지면 실패 -> 이 결로는 무시 (continue)
                if (blueHole) continue;

                // 빨간 구슬만 구멍에 빠지면 성동
                if (redHole) {
                    System.out.println(cur.count);
                    return;
                }

                // 둘 다 구멍에 안빠졌는데 위치가 겹친 경우 (위치 보정)
                if (nrx == nbx && nry == nby) {
                    // 이동 거리가 긴 쪽이 더 뒤에 있었던 것( 해당 phase 한 해서, 전체 이동거리 아님) -> 한칸 뒤로
                    int redDist = Math.abs(nrx - cur.rx) + Math.abs(nry - cur.ry);
                    int blueDist = Math.abs(nbx - cur.bx) + Math.abs(nby - cur.by);

                    if (redDist > blueDist) {
                        nrx -= dx[i];
                        nry -= dy[i];
                    } else {
                        nbx -= dx[i];
                        nby -= dy[i];
                    }
                }

                // 방문하지 않은 상태라면 큐에 추가
                if (!visited[nrx][nry][nbx][nby]) {
                    q.offer(new Marble(nrx, nry, nbx, nby, cur.count + 1));
                }
            }
        }
        System.out.println(-1);
    }
}
