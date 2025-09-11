package programmers.퍼즐게임챌린지;

public class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        // 이분 탐색 범위 설정 (숙련도의 최소, 최대값)
        int low = 1;
        int high = 100000;
        int answer = high;

        // 이분 탐색 시작
        while (low <= high) {
            int mid = low + (high - low) / 2; // 현재 테스트 해볼 숙련도
            // mid 숙련도로 제한 시간 내에 설공 가능한지 확인
            if (isPossible(mid, diffs, times, limit)) {
                answer = mid;
                high = mid - 1;
            } else {
                // 실패했다면, 더 높은 숙련도가 필요하다.
                low = mid + 1;
            }
        }
        return answer;
    }
    /**
     * 주어딘 숙련도(level)로 제한 시간(limit) 내에 모든 퍼즐을 풀 수 있는지 확인하는 함수
     */
    private boolean isPossible(int level, int[] diffs, int[] times, long limit) {
        long totalTime = 0;
        int n = diffs.length;

        // 첫 번째 퍼즐은 diffs[0]=1, leve>=1 이므로 항상 성공
        totalTime += times[0];

        // 두 번째 퍼즐부터 순회
        for (int i = 1; i < n; i++) {
            int diff = diffs[i];
            long time_cur = times[i];
            long time_prev = times[i-1];

            if (diff <= level) {
                // 숙련도가 난이도 보다 높거나 같으면, 틀리지 않음
                totalTime += time_cur;
            } else {
                // 숙련도가 난이도보다 낮으면, 틀림
                long mistakes = diff - level;
                totalTime += mistakes * (time_cur + time_prev) + time_cur;
            }

            // 계산 도중 이미 limit을 초과하면, 더 볼 필요없이 실패
            if (totalTime > limit) {
                return false;
            }
        }
        // 모든 퍼즐을 다 풀었느넫도 시간이 limit 이라하면 성공
        return true;
    }
}
