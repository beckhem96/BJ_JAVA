package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b2470 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 1. 오름차순 정렬 (투 포인터를 쓰기 위함)
        Arrays.sort(arr);

        // 2. 투 포인터 초기화
        int left = 0;
        int right = N - 1;

        // 정답을 저장할 변수 (최댓값으로 초기화)
        int minDiff = Integer.MAX_VALUE;
        int ans1 = 0;
        int ans2 = 0;

        // 3. 탐색 시작
        while (left < right) {
            int sum = arr[left] + arr[right];
            int absSum = Math.abs(sum);

            // 현재 합의 절댓값이 기존 최소보다 작으면 갱신
            if (absSum < minDiff) {
                minDiff = absSum;
                ans1 = arr[left];
                ans2 = arr[right];
            }

            // 합이 0이면 최적의 해이므로 종료
            if (sum == 0) {
                break;
            }

            // 합이 음수면 -> 더 큰 값을 더해야 0에 가까워짐 -> left 증가
            if (sum < 0) {
                left++;
            }
            // 합이 앙수면 -> 더 작은 값을 더해야 0에 가까워짐 -> right 감소
            else {
                right--;
            }
        }

        // 오름차순으로 출력
        System.out.println(ans1 + " " + ans2);

    }
}
