//package baekjoon.graph;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.PriorityQueue;
//
////class Node implements Comparable<Node> {
////    int index;
////    int cost;
////    public Node(int index, int cost) {
////        this.index = index;
////        this.cost = cost;
////    }
////    @Override
////    public int compareTo(Node other) {
////        return this.cost - other.cost;
////    }
////}
//
//public class b1238 {
//    static final int INF = Integer.MAX_VALUE;
//    static int N, M, X;
//    // 원본 그래프와 역방향 그래프
//    static List<List<Node>> graphForward;
//    static List<List<Node>> graphBackward;
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String[] nums = br.readLine().split(" ");
//        N = Integer.parseInt(nums[0]);
//        M = Integer.parseInt(nums[1]);
//        X = Integer.parseInt(nums[2]);
//        // 그래프 초기화
//        graphForward = new ArrayList<>();
//        graphBackward = new ArrayList<>();
//        for (int i = 0; i <= N; i++) {
//            graphForward.add(new ArrayList<>());
//            graphBackward.add(new ArrayList<>());
//        }
//        for (int i = 0; i < M; i++) {
//            String[] nodes = br.readLine().split(" ");
//            int u = Integer.parseInt(nodes[0]);
//            int v = Integer.parseInt(nodes[1]);
//            int time = Integer.parseInt(nodes[2]);
//            graphForward.get(u).add(new Node(v, time));
//            graphBackward.get(v).add(new Node(u, time));
//        }
//
//        // 다익스트라 실행 (X에서 각 직으로 가는 시간)
//        int[] distFromX = dijkstra(X, graphForward);
//        int[] distToX = dijkstra(X, graphBackward);
//
//        int maxTime = 0;
//        for (int i = 1; i <= N; i++) {
//            maxTime = Math.max(maxTime, distFromX[i] + distToX[i]);
//        }
//        System.out.println(maxTime);
//    }
//
//    static int[] dijkstra(int start, List<List<Node>> graph) {
//        int[] dist = new int[N + 1];
//        Arrays.fill(dist, INF);
//        PriorityQueue<Node> pq = new PriorityQueue<>();
//
//        dist[start] = 0;
//        pq.offer(new Node(start, 0));
//
//        while (!pq.isEmpty()) {
//            Node current = pq.poll();
//
//            if (current.cost > dist[current.index]) {
//                continue;
//            }
//
//            for (Node neighbor : graph.get(current.index)) {
//                if (dist[neighbor.index] > current.cost + neighbor.cost) {
//                    dist[neighbor.index] = current.cost + neighbor.cost;
//                    pq.offer(new Node(neighbor.index, dist[neighbor.index]));
//                }
//            }
//        }
//        return dist;
//    }
//}
