package programmers.주사위고르기;

import java.util.*;

class Solution {
    int n; // 총 주사위 개수
    int[][] dice; // 주사위 구성 정보
    List<Integer> sumsA, sumsB; // A와 B의 모든 점수 합을 저장할 리스트
    int maxWins; // 최대 승리 횟수를 기록
    int[] answer; // 최종 정답 조합을 저장할 배열

    public int[] solution(int[][] dice) {
        this.n = dice.length;
        this.dice = dice;
        this.maxWins = -1; // 최대 승리 횟수 초기화
        this.answer = new int[n / 2];

        // 1. A의 주사위 조합 생성(재귀)
        // 0 번 주사위부터 시작, n/2개를 뽑는 조합 생성
        generateCombinations(0, 0, new int[n / 2]);

        return this.answer;
    }

    /**
     * A가 가져갈 주사위 조합을 재귀적으로 생성하는 함수 (백트래킹)
     *
     * @param startIdx     조합을 만들 때 시작할 주사위 인덱스
     * @param count        현재까지 선택한 주사위의 개수
     * @param combinationA 현재 만들어지고 있는 조합
     */
    private void generateCombinations(int startIdx, int count, int[] combinationA) {
        if (count == n / 2) {
            // 2. 조합이 완성되면 승리 경우의 수 계산
            int currentWins = calculateWins(combinationA);
            // 3. 최적의 조합 갱신
            if (currentWins > maxWins) {
                maxWins = currentWins;
                // 현재 조합을 정답으로 업데이트(0-based -> 1- based 인덱스로 변환)
                for (int i = 0; i < combinationA.length; i++) {
                    answer[i] = combinationA[i] + 1;
                }
            }
        }
        // 재귀: 다음 주사위를 선택하기 위해 재귀 호출
        for (int i = startIdx; i < n; i++) {
            combinationA[count] = i;
            generateCombinations(i + 1, count + 1, combinationA);
        }
    }

    // 특정 조합에 대한 승리 경우의 수를 계산
    private int calculateWins(int[] combinationA) {
        // A와 B의 주사위 셋 분리
        List<Integer> diceAIndices = new ArrayList<>();
        List<Integer> diceBIndices = new ArrayList<>();
        Set<Integer> setA = new HashSet<>();
        for (int idx : combinationA) {
            diceAIndices.add(idx);
            setA.add(idx);
        }
        for (int i = 0; i < n; i++) {
            if (!setA.contains(i)) {
                diceBIndices.add(i);
            }
        }

        // A와 B의 모든 점수 합을 각각의 리스트에 채옴
        sumsA = new ArrayList<>();
        generateSums(0, 0, diceAIndices, sumsA);
        sumsB = new ArrayList<>();
        generateSums(0, 0, diceBIndices, sumsB);

        // 이진 탐색을 위해 B의 점수 합 리스트를 오름차순으로 정렬
        Collections.sort(sumsB);

        int totalWins = 0;
        // A의 각 점수 합에 대해, B를 이길 수 있는 경우의 수를 누적
        for (int sumA : sumsA) {
            // 이진 탐색으로 sumA보다 작은 점수가 sumsB에 몇개 있는지 찾음
            totalWins += lowerBound(sumsB, sumA);
        }
        return totalWins;
    }

    /**
     * 특정 주사위 셋으로 가능한 모든 점수 합을 구하는 재귀(DFS) 함수
     *
     * @param dieIndex    현재 굴릴 주사위의 순서(diceIndices 리스트 내에서의 인덱스)
     * @param currentSum  현재까지의 점수 합
     * @param diceIndices 사용할 주사위들의 원본 인덱스 리스트
     * @param resultSums  모든 최종 점수 합이 장될 리스트
     */
    private void generateSums(int dieIndex, int currentSum, List<Integer> diceIndices, List<Integer> resultSums) {
        // Base Case: 모든 주사를 다 굴렸으면 최종 합을 결과에 추가
        if (dieIndex == diceIndices.size()) {
            resultSums.add(currentSum);
            return;
        }

        // Recursive Step: 현재 주사위의 6개 면에 대해 각각 다음 간계로 재귀 호출
        int currentDieOriginalIndex = diceIndices.get(dieIndex);
        for (int faceValue : dice[currentDieOriginalIndex]) {
            generateSums(dieIndex + 1, currentSum + faceValue, diceIndices, resultSums);
        }
    }

    /**
     * 이진 탐색으로 정렬된 리스트에서 특정 값(value)보다 작은 원소의 개수를 찾는 함수
     *
     * @return value가 삽입될 위치의 인덱스, 즉 value보다 작은 원소의 개수
     */
    private int lowerBound(List<Integer> list, int value) {
        int low = 0;
        int high = list.size(); // 검색 범위를 list.size()까지 포함
        while (low < high) {
            int mid = low + (high - low) / 2;
            // list.get(mid)가 value보다 작으면, 탐색 범위를 오른쪽으로 좁힘
            if (list.get(mid) < value) {
                low = mid + 1;
            } else { // 크거나 같으면, 탐색 범위를 왼쪽으로 좁힘
                high = mid;
            }
        }
        return low;
    }
}
