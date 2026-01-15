package baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class b12015 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // LIS의 길이를 구하기 위한 리스트 (실제 LIS 구성요서와는 다를 수 있음)
        List<Integer> lis = new ArrayList<>();

        // 첫 번째 원소는 무조건 넣고 시작
        lis.add(arr[0]);

        for (int i = 1; i < N; i++) {
            int key = arr[i];

            // 1. 현재 값이 리스트의 마지막 값보다 크면 -> 그냥 뒤에 추가
            if (key > lis.get(lis.size() - 1)) {
                lis.add(key);
            }
            // 2, 작거나 같으면 -> 들어갈 위치(Lower Bound)를 찾아 교체
            else {
                int lo = 0;
                int hi = lis.size() - 1;

                // 이분 탐색 (Lower Bound: key 값 이상이 처음 나오는 위치)
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (lis.get(mid) >= key) {
                        hi = mid;
                    } else {
                        lo = mid + 1;
                    }
                }
                lis.set(hi, key);
            }
        }
        System.out.println(lis.size());
    }
}
