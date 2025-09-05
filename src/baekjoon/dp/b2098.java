package baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b2098 {
    static int N;
    static int[][] W; // 비용 행렬
    static int[][] dp; // DP 테이블 (메모이제이션)
    // -> dp[current][visitedMask] = value에서 value는 현재 current 위치에 있고 visitedMask는 이미 방문한 도시들인 상태
    // 이상태에서 아직 방문하지 않은 나머지 도시들을 모두 거쳐 출발점으로 덜하가는데 필요한 최소 비용
    static final int INF = 16 * 1_000_000 + 1; // 충분히 큰 값으로 INF 설정
    static int ALL_VISITED; // 모든 되를 방문했음을 나타내는 마스크

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j =0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // DP 테이블 초기화. - 1은 아직 계산되지 않음
        dp = new int[N][1 << N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 모든 비트가 1인 마스크 (예: N=4 -> 1111(2))
        ALL_VISITED = (1 << N) - 1;

        // 0번 도시에서 시작, 0번 도시만 방문한 상태(mask = 1)
        int result = tsp(0, 1 << 0);
        System.out.println(result);
    }

    /**
     * @param current 현재 도시
     * @param visitedMask 방문한 도시들을 나타내는 비트마스크
     * @return 남은 도시들을 모두 방문하고 출발점으로 돌아가는 최소 비용
     */
    static int tsp(int current, int visitedMask) {
        // --- Base case: 모든 도시를 방분한 경우 ---
        if (visitedMask == ALL_VISITED) {
            // 현재 도시에서 출발 도시(0)로 돌아갈 수 있는지 확인
            if (W[current][0] == 0) {
                return INF;
            }
            return W[current][0]; // 돌아가는 비용 반환
        }

        // --- 메모이제이션: 이미 계산한 경우 ---
        if (dp[current][visitedMask] != -1) {
            return dp[current][visitedMask];
        }

        // 현재 상태의 최소 비용을 INF로 초기화
        dp[current][visitedMask] = INF;

        // --- 재귀 단계: 다음 도시 탐색 ---
        for (int next = 0; next < N; next++) {
            // 1. 다음 도시(next)를 아직 방문하지 않았고,
            // 2. 현재 도시(current)에서 다음 도시로 가는 길이 있다면
            if ((visitedMask & (1 << next)) == 0 && W[current][next] != 0) {
                // 다음 상태: 다음 도시(next)를 방문했다고 마스크에 표시
                int nextMask = visitedMask | (1 << next);
                // (현재->다음 비용) + (다음부터 남은 여정의 최소비용)
                int cost = W[current][next] + tsp(next, nextMask);
                // 현재까지 계산된 최소 비용과 비요하여 갱신
                dp[current][visitedMask] = Math.min(dp[current][visitedMask], cost);
            }
        }
        return dp[current][visitedMask];
    }
}
