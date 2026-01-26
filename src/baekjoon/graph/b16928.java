package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b16928 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // board[i] = 1번 칸에 도착했을 때 최종적으로 이동하는 칸
        int[] board = new int[101];
        for (int i = 1; i <= 100; i++) {
            board[i] = i;
        }

        for (int i = 0; i < N + M;i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            board[u] = v;
        }

        Queue<Integer> q =new LinkedList<>();
        int[] dist = new int[101]; // 각 칸까지 도달하는 최소 주사위 굴림 횟수
        Arrays.fill(dist, -1);

        // 시작점
        q.offer(1);
        dist[1] = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();

            // 100번에 도달했으면 즉시 종료
            if (cur == 100) {
                System.out.println(dist[cur]);
                return;
            }

            // 주사위 1부터 6까지 굴림
            for (int i = 1; i <=6; i++) {
                int next = cur + i;

                // 100칸 넘어가면 무시
                if (next > 100) continue;

                // 뱀이나 사다리가 있다면 타고 이동한 최종 위치 구하기
                next = board[next];

                // 아직 방문하지 않은 칸이라면 큐에 넣기
                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    q.offer(next);
                }
            }
        }
    }
}
