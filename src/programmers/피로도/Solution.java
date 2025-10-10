package programmers.피로도;

public class Solution {
    static int maxDungeons = 0;
    static boolean[] visited;
    public static  void main(String[] args) {
        solution(80, new int[][]{{80, 20}, {50, 40}, {30, 10}});
    }
    public static int solution(int k, int[][] dungeons) {
        visited = new boolean[dungeons.length];
        generatePermutation(0, k, dungeons);
        return maxDungeons;
    }

    /*
     * 던전 탐험 순서를 나타내는 순열을 생성하는 재귀(백트래킹) 함수
     * @param count 현재까지 탐헌한 던전 수
     * @param currentK 현재 남은 피로도
     * @param dungeons 던전 정보 배열
     */
    static private void generatePermutation(int count, int currentK, int[][]dungeons) {
        maxDungeons = Math.max(maxDungeons, count);

        for (int i = 0; i < dungeons.length; i++) {
            if (!visited[i] && currentK >= dungeons[i][0]) {
                visited[i] = true;
                generatePermutation(count + 1, currentK - dungeons[i][1], dungeons);
                visited[i] = false;
            }
        }
    }
}
