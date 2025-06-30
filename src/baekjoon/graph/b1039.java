package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
//        int[] nums = new int[N.length()];
        int K = Integer.parseInt(st.nextToken());
        visited = new boolean[K+1][1000001];

        bfs(N);
        System.out.println(max);
    }

    static void bfs(String N) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(N, 0));

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            if (cur.cnt == K) {

            }
        }
        // N이 한자리 수면 끝
        if (N.length() == 1) {
            System.out.println(-1);
            System.exit(0);
        }
        if (N.length() == 2 && N.contains("0")) {
            System.out.println(-1);
            System.exit(0);
        }
        for (int i = 0; i<N.length();i++) {
            nums[i] = N.charAt(i);
        }
        int curIdx = 0;
        while (K-- > 0) {
            int chgInx = 0;
            for (int i = curIdx + 1; i < N.length(); i++) {
                if (nums[i] >= nums[curIdx]) {
                    chgInx = i;
                }
            }
            int tmp = nums[curIdx];
            nums[curIdx] = nums[chgInx];
            nums[chgInx] = tmp;
        }
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
