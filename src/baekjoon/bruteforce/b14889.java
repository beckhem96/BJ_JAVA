package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b14889 {
    static int N;
    static int[][] map;
    static boolean[] visited;
    static int minDiff = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        visited = new boolean[N];
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        generateCombinations(0, 0);
        System.out.println(minDiff);
    }

    static void generateCombinations(int statIdx, int count) {
        if (count == N / 2) {
            calculate();
            return;
        }
        for (int i = statIdx; i < N;i++) {
            visited[i] = true;
            generateCombinations(i + 1, count + 1);
            visited[i] = false;
        }
    }

    static void calculate() {
        int teamStart = 0;
        int teamLink = 0;
        for (int i = 0; i<N-1;i++) {
            for (int j = i+1; j<N; j++) {
                if (visited[i] && visited[j]) {
                    teamStart += map[i][j] + map[j][i];
                } else if (!visited[i] && !visited[j]) {
                    teamLink += map[i][j] + map[j][i];
                }
            }
        }
        minDiff = Math.min(minDiff, Math.abs(teamStart - teamLink));
    }
}
