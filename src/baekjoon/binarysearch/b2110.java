package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b2110 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        int[] houses = new int[N];
        for (int i = 0; i < N; i++) {
            houses[i] = Integer.parseInt(br.readLine());
        }

        // 이분 탐색을 위해 정렬 필수
        Arrays.sort(houses);

        // 거리의 최소값(low)과 최대값(high) 설정
        int low = 1;
        int high = houses[N - 1] - houses[0]; // 최대 거리는 양끝 집 사이 거리
        int result = 0;

        while (low <= high) {
            int mid = (low + high) / 2; // 이번에 시도해볼 "최소 거리"
            if (canInstall(houses, mid, C)) {
                result = mid; // 가능하다면 일단 정답 후보에 저장
                low = mid + 1; // 더 넓은 거리도 가능한지 시도 (우측 탐색)
            } else {
                high = mid - 1; // 불가능하다면 거리를 좁힘 (좌측 탐색)
            }
        }
        System.out.println(result);
    }

    // 최소 거리가 distance일 때, C개 이상 설치 가능한지 확인하는 함수
    public static boolean canInstall(int[] houses, int distance, int C) {
        int count = 1; // 첫 번째 집에 설차히고 시작
        int lastInstalled = houses[0];

        for (int i = 1; i < houses.length; i++) {
            // 직전에 설치한 집과의 거리가 distance 이상이면 설치
            if (houses[i] - lastInstalled >= distance) {
                count++;
                lastInstalled = houses[i];
            }
        }
        return count >= C;
    }
}
