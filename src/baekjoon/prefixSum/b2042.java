package baekjoon.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b2042 {
    static long[] arr;
    static long[] tree;

    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        arr = new long[N + 1];
        tree = new long[N * 4]; // 트리의 크기는 N의 4배면 모든 노드를 수용 가능

        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        // 세그먼트 트리 생성
        build(1, 1, N);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                // b 번째 수를 c로 변경
                // 기존 값과 새 값의 차이(diff)를 구해서 트리 업데이트
                long diff = c - arr[b];
                arr[b] = c; // 원본 배열 업데이트
                update(1, 1, N, b, diff);
            } else if (a == 2) {
                // b부터 c까지의 구간 합 출력
                sb.append(sum(1, 1, N, b, (int) c)).append("\n");
            }
        }
        System.out.println(sb);
    }
    // 트리 초기화 (node: 현재 노드 번호, start~end: 노드가 담당하는 원본 배열의 구간)
    static long build(int node, int start, int end) {
        if (start == end) { // 리프 노드 (구간의 길이가 1)
            return tree[node] = arr[start];
        }
        int mid = (start + end) / 2;
        // 왼쪽 자식과 오른쪽 자식의 합을 현재 노드에 저장
        return tree[node] = build(node * 2, start, mid) + build(node * 2 + 1, mid + 1, end);
    }

    // 값 변경 (index: 변경할 원본 배열의 인덱스, diff: 변경된 차이값)
    static void update(int node, int start, int end, int index, long diff) {
        // 변경할 인덱스가 현재 노드의 담당 구간을 벗어나면 무시
        if (index < start || index > end) return;

        // 구간에 포함된다면 차이(diff)만큼 더해줌
        tree[node] += diff;

        // 리프 노드가 아니라면 자식들도 업데이트
        if (start != end) {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, index, diff);
            update(node * 2 + 1, mid + 1, end, index, diff);
        }
    }
    // 구간 합 구하기 (left ~ right: 구하고자 하는 구간 합의 범위)
    static long sum(int node, int start, int end, int left, int right) {
        // 구하려는 범위가 노드의 담당 구간을 완전히 벗어난 경우
        if (left > end || right < start) return 0;

        // 구하려는 범위가 노드의 담당 구간을 완전히 포함하는 경우 (더 이상 안 내려가고 바로 리턴)
        if (left <= start && end <= right) return tree[node];

        // 그외의 경우 (걸쳐 있는 경우) -> 자식 노드들로 더 파고 들어감
        int mid = (start + end) / 2;
        return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
    }
}
