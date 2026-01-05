package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class b1005 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String[] line = br.readLine().split(" ");
        int T = Integer.parseInt(line[0]);
        while (T--> 0) {
            line = br.readLine().split(" ");
            int N = Integer.parseInt(line[0]); // 건물의 개수
            int K = Integer.parseInt(line[1]); // 건설 순서 규칙의 개수

            int[] time = new int[N + 1]; // 각 건물의 건설 시간
            line = br.readLine().split(" ");
            for (int i = 1; i <= N; i++) {
                time[i] = Integer.parseInt(line[i-1]);
            }

            // 그래프 및 진입 차수 초기화
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                graph.add(new ArrayList<>());
            }
            int[] inDegree = new int[N + 1];

            for (int i = 0; i < K; i++) {
                line = br.readLine().split(" ");
                int x = Integer.parseInt(line[0]);
                int y = Integer.parseInt(line[1]);
                graph.get(x).add(y); // x를 지어야 y를 지을 수 있음
                inDegree[y]++; // y의 진입 차수 증가
            }

            int W = Integer.parseInt(br.readLine()); // 승리하기 위해 건설해야하는 건물

            Queue<Integer> queue = new LinkedList<>();
            int[] result = new int[N + 1]; // 각 전물이 완성되는 최소 시간을 저장

            // 진입 차수가 0인 건물 큐에 삽입 및 초기화
            for (int i = 1; i <= N; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                    result[i] = time[i]; // 선행 건물이 없으면 자신의 건설 시간이 곧 완료 시간
                }
            }

            while (!queue.isEmpty()) {
                int current = queue.poll();
                // 목표 건물이 완료되었다면 루프 탈출 (선택 사항, 전체를 다 구해도 됨)
                // 하지만 위상 정렬 특성상 W 이후의 건물들도 처리해야할 수 있으므로
                // 단순히 W를 만났다고 break하면 안될 수도 있음 (W가 큐에서 나왔을 때가 아니라 W의 후행 처리가 필요 없으므로 여기서 종료 가능하긴 함)
                for (int next: graph.get(current)) {
                    // 점화식: next 건물 완성 시간 = Max(현재 저장된 시간, current 완성 시간 + next 건설 시간)
                    // 즉, 여러 선행 건물 중 가장 늦게 끝나는 시간 기준
                    result[next] = Math.max(result[next], result[current] + time[next]);
                    inDegree[next]--;
                    if (inDegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
            sb.append(result[W]).append("\n");
        }
        System.out.println(sb);
    }
}
