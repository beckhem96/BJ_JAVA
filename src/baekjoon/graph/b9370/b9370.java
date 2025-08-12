package baekjoon.graph.b9370;

import java.io.*;
import java.util.*;

// 다익스트라 알고리즘의 우선순위 큐에 저장될 노드 클래스
// 특정 정점(index)까지의 비용(cost)을 저장하며, 비용이 낮은 순으로 정렬됨
class Node implements Comparable<Node> {
    int index;
    int cost;

    public Node(int index, int cost) {
        this.index = index;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        // 비용(cost)을 기준으로 오름차순 정렬
        return Integer.compare(this.cost, other.cost);
    }
}

public class b9370 {
    // 그래프는 인접 리스트 형태로 표현
    static List<List<Node>> graph;
    // 도달할 수 없는 경우를 나타낼 무한대 값. 문제의 최대 경로 비용보다 큰 값으로 설정
    static final int INF = 50_000_001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 전체 테스트 케이스 수
        StringBuilder sb = new StringBuilder(); // 출력을 한번에 처리하기 위함

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // 교차로(정점) 수
            int m = Integer.parseInt(st.nextToken()); // 도로(간선) 수
            int t = Integer.parseInt(st.nextToken()); // 목적지 후보 수

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()); // 시작점
            int g = Integer.parseInt(st.nextToken()); // 경유지 1
            int h = Integer.parseInt(st.nextToken()); // 경유지 2

            // 그래프 초기화
            graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }

            int ghCost = 0; // g-h 사이의 도로 길이를 저장할 변수
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                // 양방향 그래프이므로 양쪽에 간선 정보 추가
                graph.get(a).add(new Node(b, d));
                graph.get(b).add(new Node(a, d));
                // g-h 도로의 길이를 미리 저장
                if ((a == g && b == h) || (a == h && b == g)) {
                    ghCost = d;
                }
            }

            // 목적지 후보들을 리스트에 저장
            List<Integer> candidates = new ArrayList<>();
            for (int i = 0; i < t; i++) {
                candidates.add(Integer.parseInt(br.readLine()));
            }

            // --- 1단계: 다익스트라 알고리즘 3회 실행 ---
            // s, g, h 각 지점에서 다른 모든 지점까지의 최단 거리를 미리 계산
            int[] distFromS = dijkstra(s, n);
            int[] distFromG = dijkstra(g, n);
            int[] distFromH = dijkstra(h, n);

            // --- 2단계: 후보 목적지 판별 ---
            List<Integer> result = new ArrayList<>();
            for (int candidate : candidates) {
                // s에서 candidate까지의 전체 최단 거리가 INF가 아니어야 함 (즉, 도달 가능해야 함)
                if(distFromS[candidate] == INF) continue;

                // s -> g -> h -> candidate 경로의 총 길이 계산
                long path1 = (long)distFromS[g] + ghCost + distFromH[candidate];
                // s -> h -> g -> candidate 경로의 총 길이 계산
                long path2 = (long)distFromS[h] + ghCost + distFromG[candidate];

                // s에서 candidate까지의 실제 최단 거리가, g-h를 경유하는 경로 중 하나의 길이와 같다면 정답 후보
                if (distFromS[candidate] == path1 || distFromS[candidate] == path2) {
                    result.add(candidate);
                }
            }

            // 결과 오름차순 정렬 후 출력 문자열에 추가
            Collections.sort(result);
            for (int node : result) {
                sb.append(node).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    /**
     * 특정 지점에서 다른 모든 지점까지의 최단 거리를 계산하는 다익스트라 알고리즘
     * @param start 시작 정점
     * @param n 전체 정점 수
     * @return 시작 정점으로부터 각 정점까지의 최단 거리를 담은 배열
     */
    static int[] dijkstra(int start, int n) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF); // 거리 배열을 무한대로 초기화
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 최소 힙으로 사용할 우선순위 큐

        dist[start] = 0; // 시작점의 거리는 0
        pq.offer(new Node(start, 0)); // 큐에 시작점 추가

        while (!pq.isEmpty()) {
            Node current = pq.poll(); // 현재까지의 거리가 가장 짧은 노드를 꺼냄

            // 큐에 저장된 거리보다 이미 더 짧은 경로를 발견했다면 무시
            if (current.cost > dist[current.index]) {
                continue;
            }

            // 현재 노드와 연결된 이웃 노드들을 순회
            for (Node neighbor : graph.get(current.index)) {
                // 현재 노드를 거쳐 이웃 노드로 가는 것이 더 빠르다면 거리 갱신
                if (dist[neighbor.index] > current.cost + neighbor.cost) {
                    dist[neighbor.index] = current.cost + neighbor.cost;
                    pq.offer(new Node(neighbor.index, dist[neighbor.index]));
                }
            }
        }
        return dist;
    }
}