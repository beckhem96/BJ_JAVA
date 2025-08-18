package programmers.서버증설횟수;

public class Solution {
    public int solution(int[] players, int m, int k) {
        // 내풀이
//        int[] curServer = new int[k+2];
//        int answer = 0;
//
//        for (int player: players) {
//            int n = 0; // 현재 증설된 서버
//            for (int i = 0; i < curServer.length - 1; i++) {
//                curServer[i] = curServer[i + 1];
//                if (i != 0) {
//                    n += curServer[i];
//                }
//            }
//            int share = player / m;
//
//            if (share >= 1) {
//                if (n < share) {
//                    curServer[k] += share - n;
//                    answer += curServer[k];
//                }
//            }
//        }
//        return answer;
        // 제미나이 풀이
        int totalExpansions = 0;
        int runningServers = 0;

        int[] expiringServers = new int[24 + k];

        for (int t = 0; t < 24; t++) {
            runningServers -= expiringServers[t];

            int playersNow = players[t];
            int neededServers = playersNow / m;

            if (runningServers < neededServers) {
                int newExpansions = neededServers - runningServers;

                totalExpansions += newExpansions;
                runningServers += newExpansions;

                if (t + k < expiringServers.length) {
                    expiringServers[t + k] += newExpansions;
                }
            }
        }
        return totalExpansions;
    }
}
