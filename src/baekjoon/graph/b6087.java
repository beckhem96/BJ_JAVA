package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b6087 {
    static int W;
    static int H;
    static Point[] target = new Point[2];
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int INF = Integer.MAX_VALUE;
    static char[][] arr;

    static class Point implements Comparable<Point> {
        int x, y;
        int dir;
        int mirrors;

        Point(int x, int y, int dir, int mirrors) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.mirrors = mirrors;
        }

        public int compareTo(Point other) {
            return this.mirrors - other.mirrors;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        List<int[]> cList = new ArrayList<>();
        arr = new char[H][W];
        for (int i=0;i<H;i++) {
            String line = br.readLine();
            for (int j=0;j<W;j++) {
                arr[i][j] = line.charAt(j);
                if (arr[i][j] == 'C') {
                    cList.add(new int[]{j, i});
                }
            }
        }
        int result = bfs(cList.get(0), cList.get(1));
        System.out.println(result);
    }

    static int bfs(int[] start, int[] end) {
        int[][][]dist = new int[H][W][4];
        for (int i=0;i<H;i++) {
            for(int j=0;j<W;j++) {
                Arrays.fill(dist[i][j], INF);
            }
        }

        PriorityQueue<Point> pq = new PriorityQueue<>();

        for (int d=0;d<4;d++) {
            dist[start[1]][start[0]][d] = 0;
            pq.offer(new Point(start[0], start[1], d, 0));
        }

        while (!pq.isEmpty()) {
            Point p = pq.poll();

            if (p.x == end[0] && p.y == end[1]) {
                return p.mirrors;
            }

            for (int d=0;d<4;d++) {
                int nx = p.x + dx[d];
                int ny = p.y + dy[d];
                int nm = p.mirrors;

                if(nx<0||ny<0||nx>=W||ny>=H) continue;
                if(arr[ny][nx] == '*') continue;

                if(p.dir != d) nm++;

                if(dist[ny][nx][d] > nm) {
                    dist[ny][nx][d] = nm;
                    pq.offer(new Point(nx, ny, d, nm));
                }
            }
        }
        return -1;
    }
}
