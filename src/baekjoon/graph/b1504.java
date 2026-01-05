package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node implements Comparable<Node> {
    int index;
    int distance;

    public Node(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    // 우선 순위 큐에서 거리가 짧은 순으로 정렬하기 위함
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

public class b1504 {

    static int N, E;
    static List<List<Node>> graph;
    static final int INF = 200000000; // 최대러기보닫 큰 값으로 설정
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        // 그래프 초기화
        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선정보 입력
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 양방향 그래프
            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }

        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        // 1.다익스트라 실행 (필요한 시작점들)
        int[] distFrom1 = dijkstra(1);
        int[] distFromV1 = dijkstra(v1);
        int[] distFromV2 = dijkstra(v2);

        // 2. 두가지 경로의 총 거리 계산
        // 경로 1 : 1 -> v1 -> v2 -> N
        long path1 = 0;
        path1 += distFrom1[v1];
        path1 += distFromV1[v2];
        path1 += distFromV2[N];

        // 경로 2 : 1 -> v2 -> v1 -> N
        long path2 = 0;
        path2 += distFrom1[v2];
        path2 += distFromV2[v1];
        path2 += distFromV1[N];

        // 3. 최단 거리 선택
        long ans = Math.min(path1, path2);

        // 4. 결과 출력 (경로가 없는 경우 처리)
        if (ans >= INF) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }

    public static int[] dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);

        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int now = current.index;
            int distance = current.distance;

            if (distance > dist[now]) continue;

            for (Node next : graph.get(now)) {
                if (dist[next.index] > dist[now] + next.distance) {
                    dist[next.index] = dist[now] + next.distance;
                    pq.offer(new Node(next.index, dist[next.index]));
                }
            }
        }
        return dist;
    }
}
