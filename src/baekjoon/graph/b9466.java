package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b9466 {
    static int n;
    static int[] arr; // 학생들 지목 정보
    static boolean[] visited; // 방문 여부
    static boolean[] finished; // 탐색 완료 여부 (사이클 판별용)
    static int count; // 팀을 이룬(사이클에 속한) 학생 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            n = Integer.parseInt(br.readLine());
            arr = new int[n + 1];
            visited = new boolean[n + 1];
            finished = new boolean[n + 1];
            count = 0;

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());

            for (int i = 1; i <= n; i++) {
                if (!visited[i]) dfs(i);
            }
            // 전체 학생 수 - 팀을 이룬 학생 수 = 팀이 없는 학생 수
            sb.append(n - count).append("\n");
        }
        System.out.println(sb);
    }

    static void dfs(int now) {
        visited[now] = true;
        int next = arr[now];

        if (!visited[next]) {
            // 1. 다음 노드를 아직 방문하지 않았다면 계속 탐색
            dfs(next);
        } else {
            // 2. 다음노드를 이미 방문했는데
            if (!finished[next]) {
                // 아직 탐색이 끝나지 않았다면 사이클 발생
                // next 부터 현재(now) 노드까지 사이클을 이루는 노드 개수세기
                count++; // now 노드 포함
                for (int i = next; i != now; i = arr[i]) {
                    count++;
                }
            }
        }
        // 현재 노드 탐색 종료 표시
        finished[now] = true;
    }
}
