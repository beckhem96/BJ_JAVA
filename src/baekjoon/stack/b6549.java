package baekjoon.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class b6549 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            if (n==0) break;

            long[] heights = new long[n];
            for (int i = 0; i < n; i++) {
                heights[i] = Long.parseLong(st.nextToken());
            }
            System.out.println(getMaxArea(heights));;
        }
    }

    static long getMaxArea(long[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        long maxArea = 0;
        int n = heights.length;

        for (int i = 0; i < n;i++) {
            // 왼쪽에서 오른쪽으로 확장하면서 넓이를 계산
            // 현재 인덱스 i를 기준으로 계산할 거기 때문에
            // stack에 남아있는 것보다 현재 인덱스의 높이가 더 낮으면 오른쪽으로 더 확장 할 수 없으니까
            // 현재 인덱스를 제외하고 넓이를 계산
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) { // 현재 인덱스의 높이가 스택 가장 위에 있는 것 보다 높이가 낮으면,
                int heightsIndex = stack.pop(); // 전 인덱스의 높이를 구하고
                long height = heights[heightsIndex];
                // 스택이 비어 있으면 현재 인덱스가 넓이
                // 스택이 비어 있지 않다면 인덱스를 거꾸로 가면서 넓이를 계산 하는데
                // 그 중 가장 넓은 걸 저장하게 됨, 여기서 stack 가장 위에 있는건 넓이를 구하려는 인덱스-1인 인덱스다
                // 왜냐면 위에서 구하려는 인덱스를 pop 했으니까
                int width = stack.isEmpty()? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
                // 넓이를 돌아가면서 구하다가 현재 인덱스의 높이 보다 높이가 크지 않으면 stack에 남아 있게됨
            }
            stack.push(i);
        }

        while(!stack.isEmpty()) {
            int heightIndex = stack.pop();
            long height = heights[heightIndex];
            int width = stack.isEmpty() ? n : n - stack.peek() - 1; // stack에 남은 것들은 전체 넓이(오른쪽 끝) 기준으로 계산
            maxArea = Math.max(maxArea, height * width);
        }
        return maxArea;
    }
}
