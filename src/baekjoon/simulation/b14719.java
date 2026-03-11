package baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b14719 {
    public static void run(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        int[] blocks = new int[W];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < W; i++) {
            blocks[i] = Integer.parseInt(st.nextToken());
        }

        int totalWater = 0;

        // 양 끝(0, W-1)은 물이 고일 수 없으르모 1부터 W-2까지만 검사
        for (int i = 1; i < W; i++) {
            int leftMax = 0;
            int rightMax = 0;

            // 1. 현재 위치(i) 기준 왼쪽에서 가장 높은 벽 찾기
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, blocks[j]);
            }
            // 2. 현재 위치(i) 기준 오른쪽에서 가장 높은 벽 찾기
            for (int j = i + 1; j < W; j++) {
                rightMax = Math.max(rightMax, blocks[j]);
            }
            // 3. 물이 찰 수 있는 한계선은 양쪽 최고 벽 중 낮은 쪽
            int boundary = Math.min(leftMax, rightMax);
            // 4. 한계선이 현재 블록보다 높을 때만 물이 고임
            if (boundary > blocks[i]) {
                totalWater += (boundary - blocks[i]);
            }
        }
        System.out.println(totalWater);
    }
}
