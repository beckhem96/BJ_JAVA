package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1976 {
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 도시의 수
        int M = Integer.parseInt(br.readLine()); // 여행 계획에 속한 도시의 수

        // 부모 배열 초기화 (1번부터 N번까지)
        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) { // 처음에는 나 자신으로 초기화
            parent[i] = i;
        }

        // 인접 행렬 입력 받기 (연결되어 있으면 합치기)
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int isConnected = Integer.parseInt(st.nextToken());
                if (isConnected == 1) {
                    union(i, j); // 두 도시가 연결되어 있다면 하나의 집합으로
                }
            }
        }

        // 여행 계획 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] plan = new int[M];
        for (int i = 0; i < M; i++) {
            plan[i] = Integer.parseInt(st.nextToken());
        }

        // 여행 계획의 모든 도시가 같은 집합인지 확인
        int root = find(plan[0]); // 첫 번쨰 도시의 부모(루트) 찾기
        boolean isPossible = true;

        for (int i = 1; i < M; i++) {
            // 다른 도시들의 부모가 첫 번쨰 도시의 부모와 다르면 불가능
            if(find(plan[i]) != root) {
                isPossible = false;
                break;
            }
        }

        if (isPossible) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    // Find: 최상위 부모 찾기 (경로 압축)
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union: 두 집합 합치기
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA;
        }
    }
}
