package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b2292 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        if (N == 1) {
            System.out.println(1);
            return;
        }
        int distance = 1; // 최소 거리 1부터
        long maxRoomInLayer = 1; // 현재 겹(layer)에서 가장 큰 방 번호

        // N이 현재 겹의 최대 방 번호보다 클 때까지 반복
        while (N > maxRoomInLayer) {
            // 최대 방 번호를 다음 겹의 최댓값으로 갱신
            // 현재 겹의 방 개수는 6 * distance
            maxRoomInLayer += 6L * distance;
            // 다음 겹으로 이동하므로 거리 1 증가
            distance++;
        }
        System.out.println(distance);
    }
}
