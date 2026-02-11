package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class b1766 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 문제의 수
        int M = Integer.parseInt(st.nextToken()); // 정보의 수

        // 인접 리스트 (그래프)
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0;i <= N;i++) {
            graph.add(new ArrayList<>());
        }

        // 진입 차수 배열 (자신을 가리키는 화살표 개수)
        int[] inDegree = new int[N + 1];

        for (int i = 0; i < M;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b); // a번 문제를 풀고 b를 풀어야 함
            inDegree[b]++; // b번 문제의 진입 차수 증가
        }

        // 우선순위 큐 (번호가 작은 문제가 먼저 나옴)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 1. 진입 차수가 0인 (선행 문제가 없는)  문제들을 큐에 넣음
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                pq.offer(i);
            }
        }

        StringBuilder sb = new StringBuilder();

        // 2. 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            int current = pq.poll();
            sb.append(current).append(" ");
            // 현재 문제를 풀었으므로, 이 문제와 연결된 다음 문제들의 진입 차수를 감소
            for (int next: graph.get(current)) {
                inDegree[next]--;

                // 새롭게 진입 차수가 0이 된 문제는 큐에 추가
                if (inDegree[next] == 0) {
                    pq.offer(next);
                }
            }
        }
        System.out.println(sb.toString());
    }
}
