package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class b7662 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int k = Integer.parseInt(br.readLine()); // 연산의 개수

            // 값과 입력된 순서(인덱스)를 저장하는 힙
            PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
            PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

            // 해당 인덱스의 노드가 유효한지 체크 (삭제되었으면 FALSE)
            boolean[] visited = new boolean[k];

            for (int i = 0; i < k; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String cmd = st.nextToken();
                int value = Integer.parseInt(st.nextToken());

                if (cmd.equals("I")) {
                    // 삽입: 두 힙에 모두 넣고, visited를 true로 설정
                    minHeap.offer(new int[]{value, i});
                    maxHeap.offer(new int[]{value, i});
                    visited[i] = true;
                } else {
                    // 삭제
                    if (value == 1) { // 최댓값 삭제
                        // 이미 삭제된 값들은 힙에서 제거 (청소)
                        while (!maxHeap.isEmpty() && !visited[maxHeap.peek()[1]]) {
                            maxHeap.poll();
                        }

                        // 유효한 값이 있다면 삭제 수행
                        if (!maxHeap.isEmpty()) {
                            visited[maxHeap.poll()[1]] = false;
                        }
                    } else { // 최솟값 삭제
                        // 이미 삭제된 값들은 힙에서 제거 (청소)
                        while (!minHeap.isEmpty() && !visited[minHeap.peek()[1]]) {
                            minHeap.poll();
                        }

                        // 유효한 값이 있다면 삭제 수행
                        if (!minHeap.isEmpty()) {
                            visited[minHeap.poll()[1]] = false;
                        }
                    }
                }
            }

            // 모든 연산 후, 힙에 남아있는 더미 데이터(이미 삭제된 값) 최종 청소
            while (!maxHeap.isEmpty() && !visited[maxHeap.peek()[1]]) {
                maxHeap.poll();
            }
            while (!minHeap.isEmpty() && !visited[minHeap.peek()[1]]) {
                minHeap.poll();
            }

            if (maxHeap.isEmpty() || minHeap.isEmpty()) {
                sb.append("EMPTY\n");
            } else {
                sb.append(maxHeap.peek()[0] + " " + minHeap.peek()[0] + "\n");
            }
        }
        System.out.println(sb);
    }
}
