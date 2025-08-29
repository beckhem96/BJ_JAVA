package programmers.도넛과막대그래프;

import java.util.*;

public class Solution {
    public int[] solution(int[][] edges) {
        // 1. 모든 정점의 in-degree, out-degree 계산 및 그래프 생성
        Map<Integer, int[]> degreeMap = new HashMap<>(); // {정점 번호: [out-degree, int-degree]}
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] edge: edges) {
            int from = edge[0];
            int to = edge[1];

            // out-degrees, in-degree, graph 초기화
            degreeMap.putIfAbsent(from, new int[2]);
            degreeMap.putIfAbsent(to, new int[2]);
            graph.putIfAbsent(from, new ArrayList<>());

            // 차수 및 간선 정보 업데이트
            degreeMap.get(from)[0]++; // from의 out-degree 증가
            degreeMap.get(to)[1]++; // to의 in-degree 증가
            graph.get(from).add(to);
        }

        // 2. 생성된 정점 찾기
        int generatedNode = -1;
        for (Map.Entry<Integer, int[]> entry: degreeMap.entrySet()) {
            int node = entry.getKey();
            int outDegree = entry.getValue()[0];
            int inDegree = entry.getValue()[1];

            if (outDegree >= 2 && inDegree == 0) {
                generatedNode = node;
                break;
            }
        }

        // 3. 각 그래프 탐색 및 종류 판별
        int[] answer = new int[4];
        answer[0] = generatedNode;

        // 생성된 정점에서 출발하는 각 간선이 별개의 그래프를 형성
        for (int startNode: graph.get(generatedNode)) {
            int vertexCount = 0;
            int edgeCount = 0;

            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();

            queue.offer(startNode);
            visited.add(startNode);

            // BFS로 서브 그래프 탐색
            while(!queue.isEmpty()) {
                int u = queue.poll();
                vertexCount++;
                edgeCount += graph.getOrDefault(u, new ArrayList<>()).size();

                for (int v: graph.getOrDefault(u, new ArrayList<>())) {
                    if (!visited.contains(v)) {
                        visited.add(v);
                        queue.offer(v);
                    }
                }
            }
            // 각 그래프 모양의 '지문'으로 종류 판별
            if (edgeCount == vertexCount - 1) {
                answer[2]++; // 막대
            } else if (edgeCount == vertexCount) {
                answer[1]++; // 도넛
            } else if (edgeCount == vertexCount + 1){
                answer[3]++; // 8자
            }
        }
        return answer;
    }
}
