package baekjoon.graph.toss;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 보증 개념이 반대로 설명됐지만 방향 그래프에 대한 개념은 제대로 되어 있음
public class toss {
    public boolean[] solution(int n, int[] trustedUsers, int[][] queries, int[] check) {
        // 1. 역방향 그래프 생성
        List<List<Integer>> reverseGraph = new ArrayList<>();
        for (int i = 0; i <=n; i++) {
            reverseGraph.add(new ArrayList<>());
        }
        for (int[] query : queries) {
            int u = query[0];
            int v = query[1];
            // v -> u (v를 보증하는 사람은 u다)
            reverseGraph.get(v).add(u);
        }

        // 2. BFS로 보증된 모든 사람 찾기
        boolean[] isTrusted = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();

        for (int user : trustedUsers) {
            if (!isTrusted[user]) {
                isTrusted[user] = true;
                queue.offer(user);
            }
        }

        while(!queue.isEmpty()) {
            int currentUser = queue.poll();

            if (!isTrusted[currentUser]) {
                isTrusted[currentUser] = true;
                queue.offer(currentUser);
            }
        }
        boolean[] answer = new boolean[check.length];
        for (int i = 0; i < check.length; i++) {
            answer[i] = isTrusted[check[i]];
        }
        return answer;
    }
}
