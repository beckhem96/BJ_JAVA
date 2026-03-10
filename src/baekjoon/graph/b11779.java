package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b11779 {
    static class Node implements Comparable<Node> {
        int to, cost;

        public Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost); // 비용 기준 오름차순 정렬
        }
    }

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine()); // 도시의 개수
        int m = Integer.parseInt(br.readLine()); // 버스의 개수

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new Node(v, w));
        }

        // 출발점, 도착점 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        // 다익스트라 초기 세팅
        int[] dist = new int[n + 1];
        int[] route = new int[n + 1]; // 경로 역추적 배열
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        dist[start] = 0;

        // 다익스트라 알고리즘 시작
        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 도착 지점에 도달하면 탐색 종료 (최소 비용 보장)
            if (current.to == end) break;

            // 이미 처리된 적 있는 노드라면 무시 (가지치기)
            if (current.cost > dist[current.to]) continue;

            for (Node next: graph.get(current.to)) {
                int nextCost = current.cost + next.cost;

                // 더 적은 비용으로 갈 수 있는 길을 찾았다면 갱신
                if (nextCost < dist[next.to]) {
                    dist[next.to] = nextCost;
                    route[next.to] = current.to; // * 핵심: 어디서 왔는지 기록
                    pq.offer(new Node(next.to, nextCost));
                }
            }
        }

        // 최소비용
        System.out.println(dist[end]);

        // 경로 역추척 (도착지 -> 출발지)
        Stack<Integer> stack = new Stack<>();
        int curr = end;
        while (curr != 0) { // 출발지의 부모는 0이므로 0이 될 때까지 반복
            stack.push(curr);
            curr = route[curr];
        }

        // 경로 포함된 도시 개수 출력
        System.out.println(stack.size());

        // 경로 출력 (스택에서 뺴면서 역순으로)
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop()).append(" ");
        }
        System.out.println(sb);
    }
}
