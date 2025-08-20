package programmers.홀짝트리;

import java.util.*;

public class Solution {
    public int[] solution(int[] nodes, int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> degrees = new HashMap<>();

        // 1. 그래프 생성 및 차수 계산
        for (int node: nodes) {
            graph.put(node, new ArrayList<>());
            degrees.put(node, 0);
        }
        for (int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
            degrees.put(u, degrees.get(u) + 1);
            degrees.put(v, degrees.get(v) + 1);
        }

        int oddEvenTreeCount = 0;
        int reverseOddEvenTreeCount = 0;
        Set<Integer> visited = new HashSet<>();

        // 2. 모든 노드를 순회하며 아직 방문 안 한 노드에서 탐색 시작 (트리 분리)
        for (int startNode: nodes) {
            if (!visited.contains(startNode)) {
                List<Integer> currentTreeNodes = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();

                queue.offer(startNode);
                visited.add(startNode);

                // BFS로 현재 트리에 속한 모든 노드를 찾음
                while (!queue.isEmpty()) {
                    int u = queue.poll();
                    currentTreeNodes.add(u);
                    for (int v: graph.get(u)) {
                        if (!visited.contains(v)) {
                            visited.add(v);
                            queue.offer(v);
                        }
                    }
                }

                // 3. 분리된 트리를 판별
                int matchingCount = 0; // 노드 값과 차수의 홀짝성이 같은 노드 수
                for (int node: currentTreeNodes) {
                    if ((node % 2) == (degrees.get(node) % 2)) {
                        matchingCount++;
                    }
                }
                // ** 홀짝트리는 루트 노드 빼고 모두 불일치 노드여야한다.
                // 왜냐하면 루트 노드는 부모가 없지만, 루트 노드가 아닌 노드는 부모 간선을 제외(-1) 해야하기 때문이다.
                // 예를 들어 노드 값이 3이고 간선 개수가 3인 노드는 루트 노드가 돼면 3, 3으로 홀수 노드지만, 자식 노드가 되면 3, 2(부모 간선 제외)로 역홀수 노드가 된다.
                // 그래서 홀짝 트리 일려면 루트 노드를 제외한 노드가 모두 불매칭(노드 값의 홀짝과 간선 개수가 불일치)여야한다. 그래야 부모 노드와 이어지는 간선을 -1 하면 매칭이 되니까

                // ** 역홀짝 트리도 마찬가지 노드 값이 3, 4인 역홀짝 노드는 루트 노드일 떄는 역홀수 노드가 맞지만, 자식 노드가 되면 홀수 노드가 된다.
                // 그래서 홀짝 트리를 구할 땐 매칭 노드가 하나여야 가능하고, 역홀짝 트리를 구할 땐 붎매핑 노드가 하나여야 가능한 것이다.
                int nonMatchingCount = currentTreeNodes.size() - matchingCount;

                // "매칭" 노드가 1개면 그 노드를 루트 삼아 홀짝 트리를 만들 수 있음
                if (matchingCount == 1) {
                    oddEvenTreeCount++;
                }
                // "불일치" 노드가 1개면 그 노드를 루트로 삼아 역홀짝 트리를 만들 수 있음
                if (nonMatchingCount == 1) {
                    reverseOddEvenTreeCount++;
                }
            }
        }

        return new int[]{oddEvenTreeCount, reverseOddEvenTreeCount};
    }
}


