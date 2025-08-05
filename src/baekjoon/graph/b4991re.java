package baekjoon.graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b4991re {
    static int w, h;
    static char[][] map;
    static int[][]dist;
    static ArrayList<Point> dirtySpots;
    static int startIdx;
    static int minMoves;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static class Point {
        int x, y, moves;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(int x, int y, int moves) {
            this.x = x;
            this.y = y;
            this.moves = moves;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            if (w == 0 && h == 0) break;

            map = new char[h][w];
            dirtySpots = new ArrayList<>();
            Point startPoint = null;

            for (int i=0; i<h; i++) {
                String line = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = line.charAt(j);
                    if (map[i][j] == 'o') {
                        startPoint = new Point(i, j);
                    } else if (map[i][j] == '*') {
                        dirtySpots.add(new Point(i, j));
                    }
                }
            }

            dirtySpots.add(0, startPoint);
            startIdx = 0;

            int dirtyCount = dirtySpots.size();
            dist = new int[dirtyCount][dirtyCount];
            boolean isPossible = true;

            for (int i=0; i<dirtyCount; i++) {
                if (!calculateDistanceFrom(i)) {
                    isPossible = false;
                    break;
                }
            }

            if (!isPossible) {
                sb.append(-1).append("\n");
                continue;
            }

            minMoves = Integer.MAX_VALUE;
            boolean[] visited = new boolean[dirtyCount];
            visited[startIdx] = true;
            findShortestPath(startIdx, 1, 0, visited);

            sb.append(minMoves).append("\n");
        }
        System.out.print(sb);
    }

    private static boolean calculateDistanceFrom(int fromIdx) {
        Queue<Point> q = new LinkedList<>();
        int[][] visited = new int[h][w];

        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                visited[i][j] = -1;
            }
        }

        Point start = dirtySpots.get(fromIdx);
        q.add(new Point(start.x, start.y, 0));
        visited[start.x][start.y] = 0;

        while (!q.isEmpty()) {
            Point curr = q.poll();

            for (int i=0; i<4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];

                if (nx >= 0 && nx< h && ny >= 0 && ny < w && map[nx][ny] != 'x' && visited[nx][ny] == -1) {
                    visited[nx][ny] = curr.moves + 1;
                    q.add(new Point(nx, ny, curr.moves + 1));
                }
            }
        }

        for (int toIdx = 0; toIdx < dirtySpots.size(); toIdx++) {
            Point target = dirtySpots.get(toIdx);
            if (visited[target.x][target.y] == -1) {
                return false;
            }
            dist[fromIdx][toIdx] = visited[target.x][target.y];
        }
        return true;
    }

    private static void findShortestPath(int currentIdx, int count, int totalMoves, boolean[] visited) {
        if (count == dirtySpots.size()) {
            minMoves = Math.min(minMoves, totalMoves);
            return;
        }

        for (int nextIdx = 0; nextIdx < dirtySpots.size(); nextIdx++) {
            if (!visited[nextIdx]) {
                visited[nextIdx] = true;
                findShortestPath(nextIdx, count + 1, totalMoves + dist[currentIdx][nextIdx], visited);
                visited[nextIdx] = false;
            }
        }
    }
}
