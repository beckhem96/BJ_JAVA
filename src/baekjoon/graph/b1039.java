package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b1039 {
    static int K, max = -1;
    static boolean[][] visited;

    public static void main(String[] arg) throws IOException {
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String N = st.nextToken();
        K = Integer.parseInt(st.nextToken());
        visited = new boolean[K+1][1000001];

        bfs(N);
        System.out.println(max);
    }

    static void bfs(String N) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(N, 0));
        visited[0][Integer.parseInt(N)] = true; // 입력된건 한번 거친 것

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            if (cur.cnt == K) {
                max = Math.max(max, Integer.parseInt(cur.num));
                continue;
            }

            for (int i =0;i<cur.num.length() - 1;i++) {
                for (int j = i+1;j<cur.num.length();j++) {
                    String swapped = swap(cur.num, i, j);
                    if (swapped.charAt(0) == '0') continue;

                    int numInt = Integer.parseInt(swapped);
                    if (!visited[cur.cnt + 1][numInt]) { // swap 된 상태니 현재 depth+1에 방문한 적이 있는지, 없으면
                        visited[cur.cnt + 1][numInt] = true; // 방문처리
                        q.add(new Pair(swapped, cur.cnt + 1)); // 그리고 다음 depth의 시작으로 넘김, 이게 경로가됨
                    }
                }
            }
        }
    }

    static String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return new String(arr);
    }

    static class Pair {
        String num;
        int cnt;

        Pair(String num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }
}
