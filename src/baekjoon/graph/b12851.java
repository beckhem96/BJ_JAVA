package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class b12851 {
    static int N, K;
    static int[] time = new int[100001];
    static int minTime = Integer.MAX_VALUE;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (N >= K) {
            // N이 K보다 크거나 같으면 -1 이동 밖에 못하므로 경우의 수는 1가지
            System.out.println((N - K) + "\n1");
            return;
        }

        bfs();

        System.out.println(minTime);
        System.out.println(count);
    }

    static void bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.offer(N);
        time[N] = 1; // 시작점 방문 처리 (시간 계산 편의를 위해 1초부터 시작하고 나중에 -1): 0은 미방문으로 두기 위함

        while (!q.isEmpty()) {
            int now = q.poll();

            // 현재 시간이 이미 구한 최단 시간보다 길다면 탐색 중단 (가지치기)
            if (minTime < time[now] - 1) return;

            for (int i = 0; i < 3; i++) {
                int next;
                if (i == 0) next = now + 1;
                else if (i == 1) next = now -1;
                else next = now * 2;

                // 범위 체크
                if (next < 0 || next > 100000) continue;

                // 목표 지점 도달 시
                if (next == K) {
                    if (minTime == Integer.MAX_VALUE) { // 처음
                        minTime = time[now]; // (time[now] - 1) + 1 이므로 그냥 time[now]
                        count = 1;
                    } else if (minTime == time[now]) {
                        count++;
                    }
                }

                // 방문 로직 (핵식!)
                // 1. 처음 방문하거나 (time[next] == 0)
                // 2. 이미 방문했지만, 지금 경로가 최단 시간과 동일하다면 (time[next] == time[now] + 1)
                // 큐에 넣어서 경우의 수를 계산하게 함
                if (time[next] == 0 || time[next] == time[now] + 1) {
                    q.offer(next);
                    time[next] = time[now] + 1;
                }
            }
        }
    }
}