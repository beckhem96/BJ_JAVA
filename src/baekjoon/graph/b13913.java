package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class b13913 {
    static int N, K;
    static int[] time = new int[100001]; // 방문 여부 및 시간 저장
    static int[] parent = new int[100001]; // 경로 역추적을 위한 부모 노드 저장
    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 예외 처리: 시작점과 도착점이 같은 경우
        if (N == K) {
            System.out.println(0);
            System.out.println(N);
            return;
        }

        bfs();
    }

    static void bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.offer(N);
        time[N] = 1; // 0초를 미방문으로 구분하기 위해 1초부터 시작 (나중에 -1해서 출력)

        while (!q.isEmpty()) {
            int now = q.poll();

            // 목쵸 도달
            if (now == K) {
                // 1.최단 시간 출력
                System.out.println(time[now] -1);

                // 2. 경로 역추적 및 출력
                Stack<Integer> stack = new Stack<>();
                int idx = K;
                while (idx != N) {
                    stack.push(idx);
                    idx = parent[idx];
                }
                stack.push(N);

                StringBuilder sb = new StringBuilder();
                while (!stack.isEmpty()) {
                    sb.append(stack.pop()).append(" ");
                }
                System.out.println(sb);
                return;
            }

            // 3가지 이동 경우의 수
            int[] nextMoves = {now - 1, now + 1, now * 2};

            for (int next: nextMoves) {
                // 범위 체크 및 방문 여부 확인
                if (next >= 0 && next <= 100000 && time[next] == 0) {
                    time[next] = time[now] + 1;
                    parent[next] = now; // 과거 경로 저장
                    q.offer(next);
                }
            }
        }
    }
}
