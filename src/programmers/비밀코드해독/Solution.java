package programmers.비밀코드해독;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    List<List<Integer>> queries;
    int[] answers;
    int n;
    int totalCount = 0;

    public int solution(int n, int[][] q, int[] ans) {
        this.queries = new ArrayList<>();
        for (int[] query: q) {
            List<Integer> qList = new ArrayList<>();
            for (int val: query) {
                qList.add(val);
            }
            this.queries.add(qList);
        }
        this.answers = ans;
        this.n = n;

        // 1. 1부터 n까지의 수 중 5개를 뽑는 조합 생성 시작
        generateCombinations(1, new ArrayList<>());

        return totalCount;
    }

    // 백트래킹을 이용해 5개의 숫자 조합을 생성하느 ㄴ함수
    private void generateCombinations(int startNum, List<Integer> currentCode) {
        // 조합이 5개 숫자로 완성된 경우
        if (currentCode.size() == 5) {
            // 2. 완성된 조합이 모든 조건을 만족하는지 검증
            if (isValid(currentCode)) {
                totalCount++;
            }
            return;
        }

        // 다음숫자를 선택하는 재귀 호출
        for (int i = startNum; i <= n; i++) {
            currentCode.add(i);
            generateCombinations(i + 1, currentCode);
            currentCode.remove(currentCode.size() - 1);
        }
    }

    // 후보 코드가 모든 시도 겨로가와 일치하는지 검증하는 함수
    private boolean isValid(List<Integer> candidateCode) {
        for (int i = 0; i < queries.size(); i++) {
            List<Integer> guess = queries.get(i);
            int expectedMatchCount = answers[i];

            // Set을 이용해 겹치는 원소의 개수를 효율적으로 계산
            Set<Integer> candidateSet = new HashSet<>(candidateCode);
            candidateSet.retainAll(new HashSet<>(guess)); // 교집합 연산
            int actualMatchCount = candidateSet.size();

            if (actualMatchCount != expectedMatchCount) {
                return false; // 하나라도 다르면 즉시 실패
            }
        }
        return true; // 모든 시도 결과를 통과하면 성공
    }
}
